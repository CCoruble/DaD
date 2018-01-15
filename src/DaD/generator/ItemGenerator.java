package DaD.generator;

import DaD.item.ItemDropInfo;
import DaD.item.ItemInstance;

public class ItemGenerator {

    private static final ItemGenerator _instance = new ItemGenerator();

    private ItemGenerator(){}

    public static final ItemGenerator getInstance() {
            return _instance;
    }

    public ItemInstance createItem(ItemDropInfo itemDropInfo){
        return new ItemInstance(itemDropInfo.getTemplate());
    }
}
