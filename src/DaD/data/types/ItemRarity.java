package DaD.data.types;

/**
 * Created by Clovis on 19/10/2017.
 * Represent the rarity of an item.
 * Not really used for the moment.
 */
public enum ItemRarity
{
	COMMON,
	UNCOMMON,
	RARE,
	EPIC,
	LEGENDARY;

	/**
	 * Array containing all enum values.
	 */
	public static final ItemRarity[] VALUES = values();
}
