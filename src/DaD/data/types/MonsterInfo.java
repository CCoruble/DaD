package DaD.data.types;

/**
 * Created by Clovis on 27/09/2017.
 */
public class MonsterInfo
{
	private final int _id;
	private final int _level;
	private final MonsterRarity _rarity;

	public MonsterInfo (int id, int level, MonsterRarity rarity)
	{
		_id = id;
		_level = level;
		_rarity = rarity;
	}

	public int getId()
	{
		return _id;
	}

	public int getLevel()
	{
		return _level;
	}

	public MonsterRarity getRarity()
	{
		return _rarity;
	}

	public void displayInfo(){
		System.out.println("id: " + _id);
		System.out.println("level: " + _level);
		System.out.println("rarity: " + _rarity);
	}
}
