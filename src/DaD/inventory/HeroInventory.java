package DaD.inventory;

import DaD.calculator.Calculator;
import DaD.creature.Hero;
import DaD.generator.ItemGenerator;
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

    /**
     * Make the hero Equip this item instance, check full description for
     * important details!
     * <p>
     *     The function first check if there is an item equipped
     *     at the place where we'll equip the new item. If so then we
     *     unequip it first and then equip the new one. Then we re-calculate
     *     all the stats for the hero.
     * </p>
     * @param hero The hero who equip the item.
     * @see Calculator#calculateAllStats(Hero)
     */
    public void equip(Hero hero, ItemInstance instance){
        ItemInstance oldEquippedItem = hero.getInventory().getEquippedItem(instance.getTemplate().getEquiSlot());
        // If there is already an equiped item on the equipSlot we unequip it
        if(oldEquippedItem != null){
            oldEquippedItem.setEquipped(false);
        }
        // Set the new item as equipped
        instance.setEquipped(true);
        // then call calculator to calculate all the new stats
        Calculator.getInstance().calculateAllStats(hero);
    }

    /**
     * Make the hero unequip this item by changing his {@link ItemInstance#_equipped}
     * value and then re-calculate his stats
     * @param hero The hero that will unequip the item.
     */
    public void unequip(Hero hero, ItemInstance itemInstance){
        // Set the item as Unequipped
        itemInstance.setEquipped(false);
        // then call calculator so it calculate all the hero stat again
        Calculator.getInstance().calculateAllStats(hero);
    }
}
