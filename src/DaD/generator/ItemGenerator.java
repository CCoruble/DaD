package DaD.generator;

import DaD.commons.RandomGenerator;
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
     * @return ItemGenerator
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
        ItemInstance instance = null;
        for (int i = 0; i < itemDropInfo.getMaxDrop(); i++) {
            // Each item has a chance to be dropped, we test if the item is successfully dropped
            boolean success = RandomGenerator.RNG(itemDropInfo.getDropRate());
            // If the item fail to be dropped we just continue
            if (!success) {
                continue;
            } else if(instance == null){
                // First time we success
                instance = ItemGenerator.getInstance().createItem(itemDropInfo);
            } else {
                // We already created this item, it mean it is dropped X times so we add one stack
                instance.addStack(1);
            }
        }
        return instance;
    }
}
