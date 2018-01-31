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
 * Singleton used to calculate all stats hero.
 * @see Env
 */
public class Calculator implements Comparator<Env>
{
	/**
	 * Private instance of class.
	 */
	private static final Calculator _instance = new Calculator();

	/**
	 * Private constructor.
	 */
	private Calculator(){}

	/**
	 * Accessor for private instance.
	 * @return Calculator.
	 */
	public static Calculator getInstance()
	{
		return _instance;
	}

	/**
	 * Will calculate all statistics of
	 * a hero.
	 * @param hero Hero you wants to calculate all statistics.
	 */
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

	/**
	 * From a given list of {@link Env} concerning the
	 * same {@link Stats} will return a double representing
	 * the value of a {@link Stats} for hero. See example.
	 * <p>
	 *     Here we have an ArrayList of {@link Env} concerning
	 *     the same {@link Stats}. What we do is:
	 *     - Initialize a double at 0, let's call it Result.
	 *     - For each {@link Env} in the ArrayList,
	 *     Result is equal to {@link Env#calcStat(double) env.calcStat(Result)}.
	 *     - Return Result
	 *
	 *     We do return a double and NOT an {@link Env}!
	 * </p>
	 * @param envList ArrayList containing all Env to apply
	 * @return double
	 */
	public double calculateStat(ArrayList<Env> envList){
		double stat = 0;
		Collections.sort(envList, this);
		for (Env env:envList)
		{
			stat = env.calcStat(stat);
		}
		return stat;
	}

	/**
	 * Sort all {@link Env} in an ArrayList by {@link Env#_order},
	 * from 0 to infinite.
	 * @param envList Not sorted ArrayList.
	 * @return ArrayList
	 */
	public ArrayList<Env> sort(ArrayList<Env> envList){
		Collections.sort(envList, this);
		return envList;
	}

	/**
	 * Required function to sort all env.
	 * @param env1 First Env to compare
	 * @param env2 Second Env to compare
	 * @return int
	 * @see Env
	 */
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

	/**
	 * Return ArrayList containing all
	 * Env that refer to the given stat.
	 * @param envList List containing all env, even those not concerning the given stat
	 * @param stat Stat you want all Env concerning it to be retrieved
	 * @return ArrayList
	 * @see Env
	 */
	private ArrayList<Env> getEnvByStat(ArrayList<Env> envList, Stats stat){
		ArrayList<Env> sortedList = new ArrayList<>();
		for(Env env:envList){
			if(env.getStat() == stat)
				sortedList.add(env);
		}
		return sortedList;
	}
}