package DaD.formulas;

import DaD.creature.Creature;

/**
 * Created by Clovis on 09/02/2017.
 */
public class FightFormulas
{
	private FightFormulas(){}

	public static int calcDamageDealt(Creature attacker, Creature receiver){
		return (int)(attacker.getAttack().getValue() / Math.max(Math.log(Math.sqrt(receiver.getDefense().getValue())),1.0));
	}
}
