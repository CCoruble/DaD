package DaD.creature;

import DaD.commons.MultiValueSet;

/**
 * Created by Clovis on 08/02/2017.
 * Class for every non player character.
 */
public abstract class Npc extends Creature
{
	/**
	 * Constructor of class.
	 * @param stats MultiValueSet containing all information
	 */
	protected Npc(MultiValueSet stats) {
		super(stats);
	}
}
