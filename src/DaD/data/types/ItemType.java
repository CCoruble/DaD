package DaD.data.types;

/**
 * Class representing the type of
 * weapons or armor.
 */
public enum ItemType {

    // RHAND
    SWORD,
    DAGGER,
    AXE,
    SPEAR,
    LONGSWORD,
    BOW,
    // LHAND
    SHIELD,
    // HEAD
    HELMET,
    HAT,
    // GLOVES
    GLOVES,
    // CHEST
    CHESTPLATE,
    // LEGS
    LEGGINGS,
    // BOOTS
    BOOTS,
    // BACK
    CAPE,
    // L & A BRACELET (hands)
    RING,
    // BELT
    BELT;

    /**
     * Array containing all enum values.
     */
    public static final ItemType[] VALUES = values();
}
