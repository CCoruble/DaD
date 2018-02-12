package DaD.inventory;

import DaD.calculator.Calculator;
import DaD.creature.Hero;
import DaD.data.types.ItemEquipSlot;
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
     * @param hero The hero who equip the item
     * @param itemInstance ItemInstance to equip
     * @see Calculator#calculateAllStats(Hero)
     */
    public void equip(Hero hero, ItemInstance itemInstance){
        ItemInstance oldEquippedItem = hero.getInventory().getEquippedItem(itemInstance.getEquipSlot());
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
     * @param itemInstance Item to equip
     */
    public void unequip(Hero hero, ItemInstance itemInstance){
        // Set the item as Unequipped
        setUnequipped(itemInstance);
        // then call calculator so it calculate all the hero stat again
        Calculator.getInstance().calculateAllStats(hero);
        System.out.println(itemInstance.getTemplate().getName() + " n'est plus équipé!");
    }

    /**
     * Change the EquipSlot of an
     * {@link ItemInstance} to the appropriate
     * value, depending on its {@link DaD.data.types.ItemType}.
     * <p>
     *     Each {@link ItemInstance} has an {@link DaD.data.types.ItemType}.
     *     Depending on this {@link DaD.data.types.ItemType} the
     *     {@link ItemInstance} will be equipped on a specific {@link DaD.data.types.ItemEquipSlot}.
     *     Example: An {@link ItemInstance} of type {@link DaD.data.types.ItemType#SWORD}
     *     will be equipped on equip slot
     *     {@link DaD.data.types.ItemEquipSlot#PAPERDOLL_RHAND right hand}.
     * </p>
     * @param itemInstance Item to set as equipped
     */
    public void setEquipped(ItemInstance itemInstance){
        switch(itemInstance.getTemplate().getItemType()){
            // RHAND
            case RHAND_DAGGER:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_RHAND);
                break;
            case SWORD:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_RHAND);
                break;
            case SPEAR:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_RHAND);
                break;
            case BOW:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_RHAND);
                break;

            // LHAND
            case LHAND_DAGGER:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_LHAND);
                break;
            case SHIELD:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_LHAND);
                break;

            // RLHAND
            case AXE:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_RHAND);
                break;
            case LONGSWORD:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_RHAND);
                break;

            // HEAD
            case HELMET:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_HEAD);
                break;
            case HAT:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_HEAD);
                break;

            // GLOVES
            case GLOVES:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_GLOVES);
                break;

            // CHEST
            case CHESTPLATE:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_CHEST);
                break;

            // LEGS
            case LEGGINGS:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_LEGS);
                break;

            // BOOTS
            case BOOTS:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_BOOTS);
                break;

            // BACK
            case CAPE:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_BACK);
                break;

            // RBRACELET
            case RHAND_RING:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_RBRACELET);
                break;
            // LBRACELET
            case LHAND_RING:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_LBRACELET);
                break;

            // BELT
            case BELT:
                itemInstance.setEquipSlot(ItemEquipSlot.PAPERDOLL_BELT);
                break;
        }
    }
    /**
     * Change {@link DaD.data.types.ItemEquipSlot} of
     * an item to {@link DaD.data.types.ItemEquipSlot#NONE}.
     * This mean the item is unequipped.
     * @param itemInstance Item to set as unequipped
     */
    public void setUnequipped(ItemInstance itemInstance){
        itemInstance.setEquipSlot(ItemEquipSlot.NONE);
    }
}
