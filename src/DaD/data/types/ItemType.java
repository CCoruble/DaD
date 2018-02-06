package DaD.data.types;

/**
 * Class representing the type of
 * weapons or armor.
 */
public enum ItemType {

    // RHAND
    RHAND_DAGGER,
    SWORD,
    SPEAR,
    BOW,
    // LHAND
    LHAND_DAGGER,
    SHIELD,
    // RLHAND
    AXE,
    LONGSWORD,
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
    // RBRACELET OR LBRACELET
    RHAND_RING,
    LHAND_RING,
    // BELT
    BELT;

    /**
     * Array containing all enum values.
     */
    public static final ItemType[] VALUES = values();
}
