package DaD.inventory;

import DaD.item.ItemInstance;

import java.util.ArrayList;

/**
 * Class used by hero to store items and golds.
 * This class extends {@link Inventory}.
 * @see ItemInstance
 */
public class HeroInventory extends Inventory{
    /**
     * Constructor of class.
     * @param itemList ArrayList containing all ItemInstance.
     * @param inventorySize Size of the inventory.
     */
    public HeroInventory(ArrayList<ItemInstance> itemList, int inventorySize){
        super(itemList,inventorySize);
    }

    /**
     * Constructor of class.
     * @param inventorySize Size of the inventory.
     */
    public HeroInventory(int inventorySize){
        super(inventorySize);
    }
}
