package DaD.inventory;

import DaD.data.types.ItemEquipSlot;
import DaD.item.ItemInstance;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;

/**
 * Created by Clovis on 13/02/2017.
 * Abstract Inventory class.
 * Extended by {@link HeroInventory}
 * @see ArrayList
 * @see ItemInstance
 */
public abstract class Inventory
{
	/**
	 * Max amount of items that can be stored
	 * in this inventory.
	 */
	private int _inventorySize;
	/**
	 * ArrayList containing all ItemInstance.
	 */
	private ArrayList<ItemInstance> _itemList;

	/**
	 * Constructor of class.
	 * @param itemList ArrayList containing all ItemInstance
	 * @param inventorySize Max amount of different items that can be stored
	 */
	public Inventory(ArrayList<ItemInstance> itemList,int inventorySize){
		_inventorySize = inventorySize;
		_itemList = itemList;
	}

	/**
	 * Constructor of class.
	 * @param inventorySize Max amount of different items that can be stored
	 */
	public Inventory(int inventorySize){
		_inventorySize = inventorySize;
		_itemList = new ArrayList<>();
	}

	//_inventorySize methods
	/**
	 * Return inventory size
	 * @return int
	 */
	public int getInventorySize(){
		return _inventorySize;
	}
	/**
	 * Set the value of inventory size
	 * @param inventorySize Max amount of different items that can be stored
	 */
	public void setInventorySize(int inventorySize){
		_inventorySize = inventorySize;
	}
	/**
	 * Increase inventory size by a certain number.
	 * @param bonusSize Inventory space you want to add
	 */
	public void increaseInventorySize(int bonusSize){
		_inventorySize += bonusSize;
	}
	/**
	 * Decrease inventory size by a certain number.
	 * @param malusSize Inventory space you want to remove
	 */
	public void decreaseInventorySize(int malusSize){
		_inventorySize -= malusSize;
	}
	/**
	 * Return amount of different items that can
	 * be stored.
	 * @return int
	 */
	public int getInventorySizeLeft(){
		return _inventorySize - getUnequippedItemsCount();
	}
	/**
	 * Return true if inventory is full.
	 * @return boolean
	 */
	public boolean isInventoryFull(){
		return getUnequippedItemsCount() >= _inventorySize;
	}
	/**
	 * Return the number of different items that are stored
	 * in this inventory.
	 * @return int
	 */
	public int getNumberOfItems(){
		return _itemList.size();
	}

	/**
	 * Return number of unequipped item inventory,
	 * this represent number of slot taken in
	 * inventory.
	 * @return int
	 */
	public int getUnequippedItemsCount(){
		int count = 0;
		// Count how many non-equipped items are in his inventory
		for(ItemInstance itemInstance:_itemList){
			// If item is unequipped
			if(!itemInstance.isEquipped())
				count++;
		}
		return count;
	}

	/**
	 * Return the ArrayList containing all
	 * {@link ItemInstance}.
	 * @return ArrayList
	 */
	public ArrayList<ItemInstance> getItemList(){
		return _itemList;
	}

	// items
	/**
	 * Add an ItemInstance to the list of items.
	 * <p>
	 *     Here we check various condition before
	 *     adding item.
	 *     Function will return true if item
	 *     was added to inventory.
	 * </p>
	 * @param item The ItemInstance to add to inventory.
	 * @return boolean
	 */
	public boolean addItem(ItemInstance item){
		// Is item stackable ?
		if(item.getTemplate().getMaxStack() > 1) {
			// For each NON EQUIPED items => We CANNOT stack equipped items, one will be equipped, the other will not
			for (ItemInstance inventoryItem : getAllNonEquippedITems()) {
				// If there is already the same item in our inventory
				if (item.getTemplate().getId() == inventoryItem.getTemplate().getId()
						// And we did not reach Max Stack for it (Ex: We have a 3 Heal potions and max stack is 5 we can add it)
						&& item.getStack() < item.getTemplate().getMaxStack()) {
					// Increase the stack of item by 1
					inventoryItem.addStack(1);
					return true;
				}
			}
		}
		// We only add an item if we have place in inventory for it
		if(_itemList.size() < _inventorySize) {
			_itemList.add(item);
			return true;
		}
		// By reaching this point it means we can not add this item to the inventory
		return false;
	}
	/**
	 * Delete an item from the list of ItemInstance
	 * @param itemInstance Item to be removed from the list
	 */
	public void deleteItem(ItemInstance itemInstance){
		_itemList.remove(itemInstance);
	}
	/**
	 * Return the ItemInstance equipped on this
	 * EquipSlot, return null if there is no equipped item
	 * on this EquipSlot.
	 * @param equipSlot The equipSlot you want the equipped item to be returned
	 * @return ItemInstance
	 */
	public ItemInstance getEquippedItem(ItemEquipSlot equipSlot){
		for (ItemInstance item: _itemList)
		{
			if(item.getEquipSlot() == equipSlot && item.isEquipped()){
				return item;
			}
		}
		return null;
	}
	/**
	 * Return all equipped ItemInstance in
	 * an ArrayList.
	 * @return ArrayList
	 */
	public ArrayList<ItemInstance> getAllEquippedItems(){
		ArrayList<ItemInstance> allEquippedItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			if(item.isEquipped())
				allEquippedItems.add(item);
		}
		return allEquippedItems;
	}
	/**
	 * Return all ItemInstance that are not equipped
	 * in an arrayList.
	 * @return ArrayList
	 */
	public ArrayList<ItemInstance> getAllNonEquippedITems() {
		ArrayList<ItemInstance> allEquippedItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			// If item is not equipped
			if(!item.isEquipped())
				allEquippedItems.add(item);
		}
		return allEquippedItems;
	}
	/**
	 * Return all ItemInstance that are not equipped and
	 * can be equipped in an ArrayList.
	 * @return ArrayList
	 */
	public ArrayList<ItemInstance> getAllEquipableUnequippedItems(){
		ArrayList<ItemInstance> allEquipableItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			// If the item can be equipped and not equipped yet !
			if(item.getTemplate().isEquipable() && !item.isEquipped())
				allEquipableItems.add(item);
		}
		return allEquipableItems;
	}
	/**
	 * Return all ItemInstance that can be sold in
	 * an ArrayList.
	 * @return ArrayList
	 */
	public ArrayList<ItemInstance> getAllSellableItems(){
		ArrayList<ItemInstance> sellableItems = new ArrayList<>();
		for(ItemInstance itemInstance:_itemList) {
			// if the item is not equipped and sellable (later items can be not sellable)
			if (!itemInstance.isEquipped()) {
				sellableItems.add(itemInstance);
			}
		}
		return sellableItems;
	}
	/**
	 * First display each items inventory name, stack and
	 * if it is equipped or not. Then display golds amount.
	 */
	public void displayInventory() {
		if(_itemList.size() == 0)
			System.out.println("Votre inventaire est vide!");
		// Display all items
		for (ItemInstance itemInstance : _itemList) {
			if (itemInstance.isEquipped()) {
				System.out.println("{EQUIPE} " + itemInstance.getTemplate().getName());
			} else {
				System.out.println("[x" + itemInstance.getStack() + "]" + itemInstance.getTemplate().getName());
			}
		}
	}
}