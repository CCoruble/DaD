package DaD.formulas;

import DaD.data.types.MonsterRarity;
import DaD.monster.MonsterTemplate;

/**
 * Created by Clovis on 09/02/2017.
 */
public class MonsterFormulas
{
	private MonsterFormulas(){}

	public static int calcLevel(int difficulty, int roomOrder){
		double level = (Math.random()+0.01); // Goes from 0 to 1
		return Math.max(1,(int)(level * difficulty + roomOrder/2));
	}

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

	public static double calcHp(double hpMax, double percent){
		return ((hpMax * percent) / 100D);
	}

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

	public static double calcGold(MonsterTemplate template){
		return template.getGold();
	}

	public static MonsterRarity calcRarity(int minRarity, int maxRarity){
		// Pre determined rarity for specific monsters, can be lengendary / boss monsters
		if(minRarity == maxRarity)
			return MonsterRarity.VALUES[minRarity];

		// Not pre determined rarity, must be randomly generated
		double rarity = Math.random()*100+1;
		MonsterRarity randomRarity;

		if(rarity > 99) // 1% chance to a an epic monster
			randomRarity =  MonsterRarity.EPIC;
		else if(rarity > 95) // 4% chance to be rare monster
			randomRarity = MonsterRarity.RARE;
		else if(rarity > 85) // 10% chance to be uncommon monster
			randomRarity = MonsterRarity.UNCOMMON;
		else //85% chance to be a common monster
			randomRarity = MonsterRarity.COMMON;

		if(randomRarity.ordinal() < minRarity)
			// The rarity randomly generated is too low, set the rarity to the minimum of the monster
			return MonsterRarity.VALUES[minRarity];
		else if(randomRarity.ordinal() > maxRarity)
			// The rarity randomly generated is too high, set the rarity to the maximum of the monster
			return MonsterRarity.VALUES[maxRarity];
		else
			// The rarity randomly generated is between the monster minimum & maximum rarity
			return randomRarity;
	}
}
