package DaD.calculator;

import DaD.creature.Hero;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.formulas.HeroFormulas;
import DaD.item.ItemInstance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Clovis on 30/09/2017.
 */
public class Calculator implements Comparator<Env>
{
	private static final Calculator _instance = new Calculator();

	private Calculator(){}

	public static Calculator getInstance()
	{
		return _instance;
	}

	public void calculateAllStats(Hero hero){
		// We must calculate all stats that compose the Hero, for this we will first retrieve all stats in one List
		// First get all bonus from his equipped items
		ArrayList<Env> allStats = new ArrayList();
		for (ItemInstance item : hero.getInventory().getAllEquippedItems()) {
			allStats.addAll(item.getTemplate().getAllBonus());
		}

		// Then get all his basic bonus Hp,Attack,Defense,...
		allStats.add(HeroFormulas.calcBaseAttack(hero.getHeroRace(),hero.getHeroGender()));
		allStats.add(HeroFormulas.calcBaseDefense(hero.getHeroRace(),hero.getHeroGender()));
		allStats.add(HeroFormulas.calcBaseHpMax(hero.getHeroRace(),hero.getHeroGender()));
		allStats.add(HeroFormulas.calcBaseMpMax(hero.getHeroRace(),hero.getHeroGender()));

		// Once you retrieved all stats we must sort the ArrayList
		sort(allStats);

		// Finally calculate all the stats separately
		// Attack
		hero.setAttack(new Env(
				calculateStat(getEnvByStat(allStats,Stats.ATTACK)),
				StatType.SET,
				Stats.ATTACK,
				0x01
		));

		// Defense
		hero.setDefense(new Env(
				calculateStat(getEnvByStat(allStats,Stats.DEFENSE)),
				StatType.SET,
				Stats.DEFENSE,
				0x01
		));

		// DO NOT FORGET about actual hp_percent, if the hero was 70% we should not give back 100% of his HP
		// Hp_Max
		double hpMaxValue = calculateStat(getEnvByStat(allStats,Stats.HP_MAX));
		hero.setHpMax(new Env(
				hpMaxValue,
				StatType.SET,
				Stats.HP_MAX,
				0x01
		));

		// Hp
		hero.setHp(new Env(
				hpMaxValue * (Hero.getInstance().getPercentHp() / 100),
				StatType.SET,
				Stats.HP,
				0x01
		));

		// DO NOT FORGET about actual Mp_percent, if the hero was 70% we should not give back 100% of his Mp
		/* Mp_Max
		double mpMaxValue = calculateStat(getEnvByStat(allStats,Stats.MP_MAX));
		hero.setMpMax(new Env(
				hpMaxValue,
				StatType.SET,
				Stats.MP_MAX,
				0x10
		));*/

		/* Mp
		hero.setMp(new Env(
				mpMaxValue * Hero.getInstance().getPercentMp(),
				StatType.SET,
				Stats.MP,
				0x10
		));*/
	}

	public double calculateStat(ArrayList<Env> envList){
		double stat = 0;
		Collections.sort(envList, this);
		for (Env env:envList)
		{
			stat = env.calcStat(stat);
		}
		return stat;
	}

	public ArrayList<Env> sort(ArrayList<Env> envList){
		Collections.sort(envList, this);
		return envList;
	}

	@Override
	public int compare(Env env1, Env env2) {
		int env1Order = env1.getOrder();
		int env2Order = env2.getOrder();

		if (env1Order > env2Order) {
			return 1;
		} else if (env1Order < env2Order) {
			return -1;
		} else {
			return 0;
		}
	}

	private ArrayList<Env> getEnvByStat(ArrayList<Env> envList, Stats stat){
		ArrayList<Env> sortedList = new ArrayList<>();
		for(Env env:envList){
			if(env.getStat() == stat)
				sortedList.add(env);
		}
		return sortedList;
	}
}