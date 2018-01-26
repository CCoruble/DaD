package DaD.formulas;

import DaD.creature.Creature;

/**
 * Created by Clovis on 09/02/2017.
 * Contain all functions used in a fight.
 * @see DaD.handler.FightHandler
 */
public class FightFormulas
{
	/**
	 * Calculate damage dealt by a creature to another.
	 * @param attacker Creature attacking
	 * @param receiver Creature receiving the attack
	 * @return int
	 */
	public static int calcDamageDealt(Creature attacker, Creature receiver){
		return (int)(attacker.getAttack().getValue() / Math.max(Math.log(Math.sqrt(receiver.getDefense().getValue())),1.0));
	}
}
