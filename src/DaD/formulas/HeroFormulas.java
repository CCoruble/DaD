package DaD.formulas;

import DaD.calculator.Calculator;
import DaD.creature.Hero;
import DaD.data.types.*;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.creature.MonsterInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 07/02/2017.
 * Class containing base stats for hero.
 * Used to calculate stats and rewards depending
 * on class, race, gender modifiers.
 * @see Env
 * @see HeroRace
 * @see HeroFormulas
 * @see HeroGender
 */
public class HeroFormulas
{
	private static final Env BASE_ATTACK = new Env(6,StatType.SET,Stats.ATTACK);
	private static final Env ATTACK_GAIN_PER_LEVEL = new Env(2, StatType.ADD, Stats.ATTACK);
	private static final Env BASE_DEFENSE = new Env(3,StatType.SET, Stats.DEFENSE);
	private static final Env DEFENSE_GAIN_PER_LEVEL = new Env(2, StatType.ADD, Stats.DEFENSE);
	private static final Env BASE_HP_MAX = new Env(100,StatType.SET, Stats.HP_MAX);
	private static final Env HP_MAX_GAIN_PER_LEVEL = new Env(10, StatType.ADD, Stats.HP_MAX);
	private static final Env BASE_MP_MAX = new Env(50,StatType.SET, Stats.MP_MAX);
	private static final Env MP_MAX_GAIN_PER_LEVEL = new Env(8, StatType.ADD, Stats.MP_MAX);
	private static final Env BASE_EXP_MAX = new Env(70,StatType.SET, Stats.EXPERIENCE_MAX);
	private static final Env EXP_MAX_GAIN_PER_LEVEL = new Env(1.45, StatType.MULTIPLY, Stats.EXPERIENCE_MAX);
	public static final int BASE_INVENTORY_SIZE = 10;
	public static final double BASE_GOLD = 30;


	public static Env getBaseAttack(){
		return BASE_ATTACK;
	}
	public static Env getAttackGainPerLevel(){
		return ATTACK_GAIN_PER_LEVEL;
	}

	public static Env getBaseDefense() {
		return BASE_DEFENSE;
	}
	public static Env getDefenseGainPerLevel(){
		return DEFENSE_GAIN_PER_LEVEL;
	}

	public static Env getBaseHpMax() {
		return BASE_HP_MAX;
	}
	public static Env getHpMaxGainPerLevel(){
		return HP_MAX_GAIN_PER_LEVEL;
	}

	public static Env getBaseMpMax() {
		return BASE_MP_MAX;
	}
	public static Env getMpMaxGainPerLevel(){
		return MP_MAX_GAIN_PER_LEVEL;
	}

	public static Env getBaseExpMax() {
		return BASE_EXP_MAX;
	}
	public static Env getExpMaxGainPerLevel(){
		return EXP_MAX_GAIN_PER_LEVEL;
	}

	/**
	 * Return the percent of experience to max experience.
	 * @param hero Hero to display percent experience.
	 * @return int
	 */
	public static int calcPercentExperience(Hero hero){
		return (int)(hero.getExperience().getValue()/hero.getExperienceMax().getValue() * 100D);
	}

	/**
	 * Calculate the maxExperience at lvl 1 for
	 * hero depending on his race and gender.
	 * Return it as an {@link Env}.
	 * @param race Race of hero
	 * @param gender Gender of hero
	 * @return Env
	 */
	public static Env calcBaseMaxExperience(HeroRace race, HeroGender gender){
		return new Env(
				race.getExperienceMaxModifier().calcStat(
				gender.getExperienceMaxModifier().calcStat(BASE_EXP_MAX.getValue())
			),StatType.SET,Stats.EXPERIENCE_MAX,0x01);
	}

	/**
	 * Return extra amount of experience hero
	 * will requires to level up compare to his
	 * previous level.
	 * @param hero Hero you want to calculate extra exp required
	 * @return double
	 */
	public static double calcLevelUpMaxExperience(Hero hero){
		return (int)(hero.getHeroRace().getExperienceModifier().calcStat(
				hero.getHeroGender().getExperienceModifier().calcStat(
						EXP_MAX_GAIN_PER_LEVEL.calcStat(hero.getExperienceMax().getValue())
				)
		));
	}

	/**
	 * From given monsterInstance calculate experience
	 * earned by the hero.
	 * <p>
	 *     Calculation take in count level difference, race
	 *     and gender.
	 * </p>
	 * @param hero Hero to calculate experience earned
	 * @param monsterInstance MonsterInstance killed by hero
	 * @return double
	 */
	public static double calcExperienceGained(Hero hero, MonsterInstance monsterInstance){
		ArrayList<Env> envList = new ArrayList<>();

		// Calculate the EXP gave by monster
		Env monsterExp = new Env(MonsterFormulas.calcExperience(monsterInstance),StatType.SET,Stats.EXPERIENCE,10);
		envList.add(monsterExp);

		// Add hero race & gender modifiers
		envList.add(hero.getHeroRace().getExperienceModifier());
		envList.add(hero.getHeroGender().getExperienceModifier());

		// Then calculate the final exp earned by hero
		double experience = Calculator.getInstance().calculateStat(envList);

		// Get the difference between hero & monster level
		int levelDifference = Math.abs(hero.getLevel() - monsterInstance.getLevel());
		// Depending on the lvl difference we decrease the exp gained
		if (levelDifference <= 2) {
			// No exp diminution
			return experience;
		} else if (levelDifference <= 4) {
			// Decrease exp by 30%
			return experience * 0.70;
		} else if (levelDifference <= 6) {
			// Decrease exp by 60%
			return experience * 0.40;
		} else {
			// decrease exp by 90%
			return experience * 0.10;
		}
	}

	/**
	 * Calculate the attack at lvl 1 for
	 * hero depending on his race and gender.
	 * Return it as an {@link Env}.
	 * @param race Race of hero
	 * @param gender Gender of hero
	 * @return Env
	 */
	public static Env calcBaseAttack(HeroRace race, HeroGender gender){
		return new Env(
				race.getAttackModifier().calcStat(
				gender.getAttackModifier().calcStat(BASE_ATTACK.getValue())
			),StatType.SET,Stats.ATTACK,0x01);
	}
	/**
	 * Return extra attack hero earned as after
	 * leveling up.
	 * @param hero Hero you want to calculate extra attack
	 * @return double
	 */
	public static int calcLevelUpAttack(Hero hero){
		return (int)(hero.getHeroRace().getAttackModifier().calcStat(
				hero.getHeroGender().getAttackModifier().calcStat(
						ATTACK_GAIN_PER_LEVEL.calcStat(hero.getAttack().getValue())
				)
		));
	}

	/**
	 * Calculate the defense at lvl 1 for
	 * hero depending on his race and gender.
	 * Return it as an {@link Env}.
	 * @param race Race of hero
	 * @param gender Gender of hero
	 * @return Env
	 */
	public static Env calcBaseDefense(HeroRace race, HeroGender gender){
		return new Env(
				race.getDefenseModifier().calcStat(
				gender.getDefenseModifier().calcStat(BASE_DEFENSE.getValue())
			),StatType.SET,Stats.DEFENSE,0x01);
	}
	/**
	 * Return extra defense hero earned as after
	 * leveling up.
	 * @param hero Hero you want to calculate extra defense
	 * @return double
	 */
	public static int calcLevelUpDefense(Hero hero){
		return (int)(hero.getHeroRace().getDefenseModifier().calcStat(
				hero.getHeroGender().getDefenseModifier().calcStat(
						DEFENSE_GAIN_PER_LEVEL.calcStat(hero.getDefense().getValue())
				)
		));
	}

	/**
	 * Calculate the hpMax at lvl 1 for
	 * hero depending on his race and gender.
	 * Return it as an {@link Env}.
	 * @param race Race of hero
	 * @param gender Gender of hero
	 * @return Env
	 */
	public static Env calcBaseHpMax(HeroRace race, HeroGender gender){
		return new Env(
				race.getHpMaxModifier().calcStat(
				gender.getHpMaxModifier().calcStat(BASE_HP_MAX.getValue())
			),StatType.SET, Stats.HP_MAX,0x01);
	}
	/**
	 * Return extra hpMax hero earned as after
	 * leveling up.
	 * @param hero Hero you want to calculate extra hpMax
	 * @return double
	 */
	public static int calcLevelUpHpMax(Hero hero){
		return (int)(hero.getHeroRace().getHpMaxModifier().calcStat(
				hero.getHeroGender().getHpMaxModifier().calcStat(
						HP_MAX_GAIN_PER_LEVEL.calcStat(hero.getHpMax().getValue())
				)
		));
	}

	/**
	 * Calculate the mpMax at lvl 1 for
	 * hero depending on his race and gender.
	 * Return it as an {@link Env}.
	 * THIS STAT IS NOT USED FOR NOW!
	 * @param race Race of hero
	 * @param gender Gender of hero
	 * @return Env
	 */
	public static Env calcBaseMpMax(HeroRace race, HeroGender gender){
		return new Env(
			(race.getMpMaxModifier().calcStat(
				gender.getMpMaxModifier().calcStat(BASE_MP_MAX.getValue())
			)),StatType.SET, Stats.MP_MAX,0x01);
	}
	/**
	 * Return extra mpMax hero earned as after
	 * leveling up.
	 * THIS STAT IS NOT USED FOR NOW!
	 * @param hero Hero you want to calculate extra mpMax
	 * @return double
	 */
	public static int calcLevelUpMpMax(Hero hero){
		return (int)(hero.getHeroRace().getMpMaxModifier().calcStat(
				hero.getHeroGender().getMpMaxModifier().calcStat(
						MP_MAX_GAIN_PER_LEVEL.calcStat(hero.getMpMax().getValue())
				)
		));
	}
}
