package DaD.formulas;

import DaD.calculator.Calculator;
import DaD.creature.Hero;
import DaD.data.types.*;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.monster.MonsterInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 07/02/2017.
 */
public class HeroFormulas
{
	private HeroFormulas(){}

	private static final Env BASE_ATTACK = new Env(6,StatType.SET);
	private static final Env ATTACK_GAIN_PER_LEVEL = new Env(2, StatType.ADD);
	private static final Env BASE_DEFENSE = new Env(3,StatType.SET);
	private static final Env DEFENSE_GAIN_PER_LEVEL = new Env(2, StatType.ADD);
	private static final Env BASE_HP_MAX = new Env(100,StatType.SET);
	private static final Env HP_MAX_GAIN_PER_LEVEL = new Env(12, StatType.ADD);
	private static final Env BASE_MP_MAX = new Env(50,StatType.SET);
	private static final Env MP_GAIN_PER_LEVEL = new Env(10, StatType.ADD);
	private static final Env BASE_EXPERIENCE = new Env(70,StatType.SET);
	private static final Env EXP_MAX_GAIN_PER_LEVEL = new Env(1.45, StatType.MULTIPLY);
	public static final int BASE_INVENTORY_SIZE = 10;
	public static final double BASE_GOLD = 100;

	// Experience
	public static int calcPercentExperience(Hero hero){
		return (int)(hero.getExperience().getValue()/hero.getExperienceMax().getValue() * 100D);
	}
	public static Env calcBaseMaxExperience(HeroRace race, HeroGender gender){
		return new Env(
				race.getExperienceMaxModifier().calcStat(
				gender.getExperienceMaxModifier().calcStat(BASE_EXPERIENCE.getValue())
			),StatType.SET,Stats.EXPERIENCE_MAX,0x01);
	}

	public static double calcLevelUpMaxExperience(Hero hero){
		return (int)(hero.getHeroRace().getExperienceModifier().calcStat(
				hero.getHeroGender().getExperienceModifier().calcStat(
						EXP_MAX_GAIN_PER_LEVEL.calcStat(hero.getExperienceMax().getValue())
				)
		));
	}

	public static double calcExperienceGained(Hero hero, MonsterInstance monsterInstance){
		// Get the difference between hero & monster level
		int levelDifference = Math.abs(hero.getLevel() - monsterInstance.getLevel());

		// Calculate the potential exp gained by the hero
		// Monster modifier
		ArrayList<Env> envList = new ArrayList<>();

		// All hero modifier & env
		envList.add(hero.getExperience());
		envList.add(hero.getHeroRace().getExperienceModifier());
		envList.add(hero.getHeroGender().getExperienceModifier());

		// For the moment we just add one Env to the EnvList for each level, LEVEL START AT 1 NOT AT 0 !!!!
		for(int i = 1; i < monsterInstance.getLevel()-1; i++)
			envList.add(monsterInstance.getTemplate().getExperiencePerLevel());

		for(int i = 0; i < monsterInstance.getRarity().ordinal(); i++)
			envList.add(monsterInstance.getTemplate().getExperienceRarityModifier());

		double experience = Calculator.getInstance().calculateStat(envList);

		// Depending on the lvl difference we decrease the exp gained
		if (levelDifference <= 2) {
			// No exp diminution
			return experience;
		} else if (levelDifference <= 4) {
			// Decrease exp by 15%
			return experience * 0.85;
		} else if (levelDifference <= 6) {
			// Decrease exp by 35%
			return experience * 0.65;
		} else {
			// decrease exp by 50%
			return experience * 0.5;
		}
	}

	//Attack
	public static Env calcBaseAttack(HeroRace race, HeroGender gender){
		return new Env(
				race.getAttackModifier().calcStat(
				gender.getAttackModifier().calcStat(BASE_ATTACK.getValue())
			),StatType.SET,Stats.ATTACK,0x01);
	}
	public static int calcLevelUpAttack(Hero hero){
		return (int)(hero.getHeroRace().getAttackModifier().calcStat(
				hero.getHeroGender().getAttackModifier().calcStat(
						ATTACK_GAIN_PER_LEVEL.calcStat(hero.getAttack().getValue())
				)
		));
	}

	//Defense
	public static Env calcBaseDefense(HeroRace race, HeroGender gender){
		return new Env(
				race.getDefenseModifier().calcStat(
				gender.getDefenseModifier().calcStat(BASE_DEFENSE.getValue())
			),StatType.SET,Stats.DEFENSE,0x01);
	}
	public static int calcLevelUpDefense(Hero hero){
		return (int)(hero.getHeroRace().getDefenseModifier().calcStat(
				hero.getHeroGender().getDefenseModifier().calcStat(
						DEFENSE_GAIN_PER_LEVEL.calcStat(hero.getDefense().getValue())
				)
		));
	}

	//Hp Max
	public static Env calcBaseHpMax(HeroRace race, HeroGender gender){
		return new Env(
				race.getHpMaxModifier().calcStat(
				gender.getHpMaxModifier().calcStat(BASE_HP_MAX.getValue())
			),StatType.SET, Stats.HP_MAX,0x01);
	}
	public static int calcLevelUpHpMax(Hero hero){
		return (int)(hero.getHeroRace().getHpMaxModifier().calcStat(
				hero.getHeroGender().getHpMaxModifier().calcStat(
						HP_MAX_GAIN_PER_LEVEL.calcStat(hero.getHpMax().getValue())
				)
		));
	}

	//Mana max
	public static Env calcBaseMpMax(HeroRace race, HeroGender gender){
		return new Env(
			(race.getMpMaxModifier().calcStat(
				gender.getMpMaxModifier().calcStat(BASE_MP_MAX.getValue())
			)),StatType.SET, Stats.MP_MAX,0x01);
	}
	public static int calcLevelUpMpMax(Hero hero){
		return (int)(hero.getHeroRace().getMpMaxModifier().calcStat(
				hero.getHeroGender().getMpMaxModifier().calcStat(
						MP_GAIN_PER_LEVEL.calcStat(hero.getMpMax())
				)
		));
	}
}
