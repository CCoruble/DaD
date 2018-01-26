package DaD.formulas;

import DaD.data.types.MonsterRarity;
import DaD.monster.MonsterTemplate;

/**
 * Created by Clovis on 09/02/2017.
 * Class used to calculate difference
 * stats and reward for a monster.
 */
public class MonsterFormulas
{
	/**
	 * Calculate experience given by killing monster.
	 * @param template Template of the monster
	 * @param level Level of the monster
	 * @param rarity Rarity fo the monster
	 * @return double
	 */
	public static double calcExperience(MonsterTemplate template,int level,MonsterRarity rarity){
		//  Calculate the experience given by killing a monster depending on his level and rarity
		double exp = template.getExperience().getValue();
		for(int i = 0; i < level-1; i++)
		exp = template.getExperiencePerLevel().calcStat(exp,level-1);

		if(rarity.ordinal() == 0 || template.getMinimumRarity() == template.getMaximumRarity()) // This is the basic rarity or a specific rarity so there is no modification to the total experience
			return exp;
		else{ // This is a non-basic rarity (rare / epic / legendary ...), we apply the modifier to the total experience
			exp = template.getExperienceRarityModifier().calcStat(exp,rarity.ordinal() - template.getMinimumRarity());
			return exp;
		}
	}

	/**
	 * Calculate HpMax of a monster.
	 * @param template Template of the monster
	 * @param level Level of the monster
	 * @param rarity Rarity fo the monster
	 * @return double
	 */
	public static double calcHpMax(MonsterTemplate template, int level, MonsterRarity rarity){
		double hp = template.getMaxHp().getValue();
		hp = template.getMaxHpPerLevel().calcStat(hp,level - 1);

		if(rarity.ordinal() == 0 || template.getMinimumRarity() == template.getMaximumRarity()) // This is the basic rarity or a specific rarity so there is no modification to the hpMax
			return hp;
		else{ // This is a non-basic rarity (rare / epic / legendary ...), we apply the modifier to the total experience
			hp = template.getMaxHpRarityModifier().calcStat(hp,rarity.ordinal() - template.getMinimumRarity());
			return hp;
		}
	}

	/**
	 * Calculate hp of a monster, hp can be
	 * different from HpMax.
	 * @param hpMax Maximum Hp of the monster
	 * @param percent Percent of his HpMax he will get as Hp
	 * @return double
	 * @see MonsterTemplate#_hpPercent
	 */
	public static double calcHp(double hpMax, double percent){
		return ((hpMax * percent) / 100D);
	}

	/**
	 * Calculate attack of a monster.
	 * @param template Template of the monster
	 * @param level Level of the monster
	 * @param rarity Rarity fo the monster
	 * @return double
	 */
	public static double calcAttack(MonsterTemplate template, int level, MonsterRarity rarity){
		double attack = template.getAttack().getValue();
		attack = template.getAttackPerLevel().calcStat(attack,level - 1);

		if(rarity.ordinal() == 0 || template.getMinimumRarity() == template.getMaximumRarity()) // This is the basic rarity or a specific rarity so there is no modification to the total attack
			return attack;
		else{ // This is a non-basic rarity (rare / epic / legendary ...), we apply the modifier to the total experience
			attack = template.getAttackRarityModifier().calcStat(attack,rarity.ordinal() - template.getMinimumRarity());
			return attack;
		}
	}

	/**
	 * Calculate defense of a monster.
	 * @param template Template of the monster
	 * @param level Level of the monster
	 * @param rarity Rarity fo the monster
	 * @return double
	 */
	public static double calcDefense(MonsterTemplate template, int level, MonsterRarity rarity){
		double defense = template.getDefense().getValue();
		defense = template.getDefensePerLevel().calcStat(defense,level - 1);

		if(rarity.ordinal() == 0 || template.getMinimumRarity() == template.getMaximumRarity()) // This is the basic rarity or a specific rarity so there is no modification to the total defense
			return defense;
		else{ // This is a non-basic rarity (rare / epic / legendary ...), we apply the modifier to the total experience
			defense = template.getDefenseRarityModifier().calcStat(defense,rarity.ordinal() - template.getMinimumRarity());
			return defense;
		}
	}

	/**
	 * Calculate gold given by killing a monster.
	 * @param template Template of the monster
	 * @return double
	 */
	public static double calcGold(MonsterTemplate template){
		return template.getGold();
	}
}
