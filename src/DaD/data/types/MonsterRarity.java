package DaD.data.types;

/**
 * Created by Clovis on 08/02/2017.
 * Represent the different rarity of
 * a monster.
 */
public enum MonsterRarity
{
	COMMON,
	UNCOMMON,
	RARE,
	EPIC,
	LEGENDARY;

	public static final MonsterRarity[] VALUES = values();
}
