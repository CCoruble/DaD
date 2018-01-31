package DaD.data.types;

/**
 * Created by Clovis on 09/10/2017.
 * Represent the slot where the item
 * is equipped. "NONE" representing an
 * item that is NOT equipped.
 */
public enum ItemEquipSlot
{
	NONE,
	PAPERDOLL_HEAD,
	PAPERDOLL_RHAND,
	PAPERDOLL_LHAND,
	PAPERDOLL_GLOVES,
	PAPERDOLL_CHEST,
	PAPERDOLL_LEGS,
	PAPERDOLL_BOOTS,
	PAPERDOLL_BACK,
	PAPERDOLL_RBRACELET,
	PAPERDOLL_LBRACELET,
	PAPERDOLL_BELT;

	/**
	 * Array containing all enum values
	 */
	public static final ItemEquipSlot[] VALUES = values();
}
