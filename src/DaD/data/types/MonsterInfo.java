package DaD.data.types;

import DaD.Template.MonsterTemplate;
import DaD.creature.MonsterInstance;

/**
 * Created by Clovis on 27/09/2017.
 * Represent the necessary information
 * to create a MonsterInstance.
 */
public class MonsterInfo
{
	/**
	 * Id of the{@link MonsterTemplate}.
	 */
	private final int _id;
	/**
	 * Level of the future {@link MonsterInstance}.
	 */
	private final int _level;
	/**
	 * Rarity of the future {@link MonsterInstance}.
	 */
	private final MonsterRarity _rarity;

	/**
	 * Constructor of class.
	 * @param id Id of template
	 * @param level Level of monster
	 * @param rarity Rarity of monster.
	 */
	public MonsterInfo (int id, int level, MonsterRarity rarity) {
		_id = id;
		_level = level;
		_rarity = rarity;
	}

	/**
	 * Return template Id.
	 * @return int
	 */
	public int getId()
	{
		return _id;
	}

	/**
	 * Return level of future monster.
	 * @return int
	 */
	public int getLevel()
	{
		return _level;
	}

	/**
	 * Return rarity of future monster.
	 * @return MonsterRarity
	 */
	public MonsterRarity getRarity()
	{
		return _rarity;
	}

	/**
	 * Display each attributes of this instance.
	 */
	public void displayInfo(){
		System.out.println("id: " + _id);
		System.out.println("level: " + _level);
		System.out.println("rarity: " + _rarity);
	}
}
