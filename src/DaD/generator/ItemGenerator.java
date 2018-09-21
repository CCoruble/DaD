package DaD.generator;

import DaD.Commons.Utils.RandomGenerator;
import DaD.item.EquipmentInstance;
import DaD.item.ItemDropInfo;
import DaD.item.ItemInstance;
import DaD.Template.ItemTemplate;
import DaD.item.MiscInstance;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

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
     * From a list of ItemDropInfo create all
     * instances that were actually dropped.
     * @param itemDropInfoList
     * @return List containing all instances
     */
    public List<ItemInstance> generateItemDropList(List<ItemDropInfo> itemDropInfoList){
        List<ItemInstance> itemList = new ArrayList<>();
        for(ItemDropInfo itemDrop: itemDropInfoList) {
            int stack = 0;
            // Count how many of this item are dropped
            for (int i = 0; i < itemDrop.getMaxDrop(); i++)
                if (RandomGenerator.RNG(itemDrop.getDropRate()))
                    stack++;

            // Do not create instance with 0 stack
            if(stack > 0)
                itemList.addAll(createItem(itemDrop.getTemplate(), stack));

        }
        return itemList;
    }

    /**
     * Create X items from given template.
     * Number of items created depends on given stack
     * and template max Stack.
     *
     * <p>
     *     If stack > maxStack, we will
     *     create at least 2 itemInstance
     * </p>
     * @param itemTemplate
     * @param stack
     * @return List containing all itemInstance
     */
    public List<ItemInstance> createItem(ItemTemplate itemTemplate, int stack){
        // we MUST NOT create an item with 0 negative stacks
        if(stack <= 0)
            return null;

        List<ItemInstance> itemList = new ArrayList<>();
        // while we cannot store all stack into one itemInstance
        while (stack > itemTemplate.getMaxStack()) {
            // Create a new instance

            ItemInstance item = createItem(itemTemplate);
            item.setStack(itemTemplate.getMaxStack());
            itemList.add(item);
            // Retrieve number of stack contained in this instance
            stack -= itemTemplate.getMaxStack();
        }
        // Once here, all stacks left can be contained in one instance
        ItemInstance item = createItem(itemTemplate);
        item.setStack(stack);
        itemList.add(item);
        return itemList;
    }

    /**
     * Create an item from its template, can
     * be either an equipment or misc
     * @param itemTemplate
     * @return ItemInstance
     */
    public ItemInstance createItem(ItemTemplate itemTemplate){
        switch(itemTemplate.getItemType()){
            case EQUIPMENT:
                return new EquipmentInstance(itemTemplate);
            case MISC:
                return new MiscInstance(itemTemplate);
            default:
                throw new NotImplementedException();
        }
    }
}
