package DaD.inventory;

import DaD.calculator.Calculator;
import DaD.creature.Hero;
import DaD.data.types.GearType;
import DaD.data.types.ItemEquipSlot;
import DaD.item.EquipmentInstance;
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
     * @param hero The hero who equip the item
     * @param itemInstance ItemInstance to equip
     * @see Calculator#calculateAllStats(Hero)
     */
    public void equip(Hero hero, EquipmentInstance itemInstance){
        EquipmentInstance oldEquippedItem = hero.getInventory().getEquippedItem(itemInstance.getTemplate().getEquipSlot());
        // If there is already an equipped item on the equipSlot we unequip it
        if(oldEquippedItem != null){
            setUnequipped(oldEquippedItem);
        }
        // Set the new item as equipped
        setEquipped(itemInstance);
        // then call calculator to calculate all the new stats
        Calculator.getInstance().calculateAllStats(hero);
        System.out.println(itemInstance.getTemplate().getName() + " est maintenant équipé !");
    }

    /**
     * Set item as unequip and calculate Hero stats.
     * @param hero The hero that will unequip the item
     * @param equipmentInstance Item to equip
     */
    public void unequip(Hero hero, EquipmentInstance equipmentInstance){
        // Set the item as Unequipped
        setUnequipped(equipmentInstance);
        // then call calculator so it calculate all the hero stat again
        Calculator.getInstance().calculateAllStats(hero);
        System.out.println(equipmentInstance.getTemplate().getName() + " n'est plus équipé!");
    }

    /**
     * Call {@link EquipmentInstance#setEquipped(boolean) setEquipped(true)}
     * function.
     * @param equipmentInstance equipment to set as equipped
     */
    public void setEquipped(EquipmentInstance equipmentInstance){
        equipmentInstance.setEquipped(true);
    }
    /**
     * Call {@link EquipmentInstance#setEquipped(boolean) setEquipped(false)}
     * function.
     * @param equipmentInstance equipment to set as equipped
     */
    public void setUnequipped(EquipmentInstance equipmentInstance){
        equipmentInstance.setEquipped(false);
    }
}
