package DaD.calculator;

import DaD.Debug.DebugLogger;
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
		try {
			// We must calculate all stats that compose the Hero, for this we will first retrieve all stats in one List
			ArrayList<Env> allStats = new ArrayList();

			// FIRST get all his BASICS STATS
			allStats.add(HeroFormulas.getBaseAttack());
			allStats.add(HeroFormulas.getBaseDefense());
			allStats.add(HeroFormulas.getBaseHpMax());
			allStats.add(HeroFormulas.getBaseMpMax());
			allStats.add(HeroFormulas.getBaseExpMax());

			// Then all gender & race modifier
			for (Env raceModifier : hero.getHeroRace().getAllEnv())
				allStats.add(raceModifier);
			for (Env genderModifier : hero.getHeroGender().getAllEnv())
				allStats.add(genderModifier);

			// Then for every level, LEVEL START AT 1 NOT 0
			for (int i = 1; i < hero.getLevel(); i++) {
				allStats.add(HeroFormulas.getAttackGainPerLevel());
				allStats.add(HeroFormulas.getDefenseGainPerLevel());
				allStats.add(HeroFormulas.getHpMaxGainPerLevel());
				allStats.add(HeroFormulas.getMpMaxGainPerLevel());
				allStats.add(HeroFormulas.getExpMaxGainPerLevel());
			}

			// All bonus from his equipped items
			for (ItemInstance item : hero.getInventory().getAllEquippedItems()) {
				allStats.addAll(item.getTemplate().getAllBonus());
			}

			// Once you retrieved all stats we must sort the ArrayList by order
			sort(allStats);

			// Finally calculate all the stats separately
			// Attack
			hero.setAttack(new Env(
					calculateStat(getEnvByStat(allStats, Stats.ATTACK)),
					StatType.SET,
					Stats.ATTACK,
					0x01
			));

			// Defense
			hero.setDefense(new Env(
					calculateStat(getEnvByStat(allStats, Stats.DEFENSE)),
					StatType.SET,
					Stats.DEFENSE,
					0x01
			));

			// Hp_Max
			double oldHpMaxValue = hero.getHpMax().getValue();
			double hpMaxValue = calculateStat(getEnvByStat(allStats, Stats.HP_MAX));
			hero.setHpMax(new Env(
					hpMaxValue,
					StatType.SET,
					Stats.HP_MAX,
					0x01
			));
			// This can be negative if item applied debuf on HP
			double hpMaxValueDiff = oldHpMaxValue - hpMaxValue;
			// Hp
			hero.setHp(new Env(
					hero.getHp().getValue() + hpMaxValueDiff,
					StatType.SET,
					Stats.HP,
					0x01
			));
			/*
			// Mp_Max
			double oldMpMaxValue = hero.getMpMax().getValue();
			double mpMaxValue = calculateStat(getEnvByStat(allStats, Stats.MP_MAX));
			hero.setMpMax(new Env(
					hpMaxValue,
					StatType.SET,
					Stats.MP_MAX,
					0x10
			));
			// This can be negative if item applied debuf on MP
			double mpMaxValueDiff = oldMpMaxValue - mpMaxValue;
			// Mp
			hero.setMp(new Env(
					hero.getMp().getValue() + mpMaxValueDiff,
					StatType.SET,
					Stats.MP,
					0x10
			));
			*/

			// exp_Max
			double expMaxValue = calculateStat(getEnvByStat(allStats, Stats.EXPERIENCE_MAX));
			hero.setExperienceMax(new Env(
					expMaxValue,
					StatType.SET,
					Stats.EXPERIENCE_MAX,
					0x10
			));
		} catch (Exception e){
			DebugLogger.log(e);
		}
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