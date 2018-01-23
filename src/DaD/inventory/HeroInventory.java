package DaD.inventory;

import DaD.item.ItemInstance;

import java.util.ArrayList;

public class HeroInventory extends Inventory{
    public HeroInventory(ArrayList<ItemInstance> itemList, int inventorySize){
        super(itemList,inventorySize);
    }
    public HeroInventory(int inventorySize){
        super(inventorySize);
    }
}
