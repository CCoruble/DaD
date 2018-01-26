package DaD.generator;

import DaD.item.ItemDropInfo;
import DaD.item.ItemInstance;
/**
 * Used to create {@link ItemInstance} from
 * given {@link ItemDropInfo}
 */
public class ItemGenerator {

    /**
     * Private instance of class.
     */
    private static final ItemGenerator _instance = new ItemGenerator();

    /**
     * Private constructor of class.
     */
    private ItemGenerator(){}

    /**
     * Accessor for instance of class.
     * @return
     */
    public static final ItemGenerator getInstance() {
            return _instance;
    }

    /**
     * Call {@link ItemInstance#ItemInstance(int)} and
     * return the ItemInstance created.
     * @param itemDropInfo Information about the item.
     * @return ItemInstance
     */
    public ItemInstance createItem(ItemDropInfo itemDropInfo){
        return new ItemInstance(itemDropInfo.getTemplate());
    }
}
