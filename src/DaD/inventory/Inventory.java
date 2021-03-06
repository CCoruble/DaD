package DaD.inventory;

import DaD.Commons.Utils.InputFunction;
import DaD.Template.EquipmentTemplate;
import DaD.data.types.ItemEquipSlot;
import DaD.generator.ItemGenerator;
import DaD.item.EquipmentInstance;
import DaD.item.ItemInstance;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
			if(!(itemInstance instanceof EquipmentInstance) || !((EquipmentInstance)itemInstance).isEquipped())
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

	/**
	 * Add an ItemInstance to the list of items.
	 * Return true if itemInstance and all of his stacks
	 * were added to inventory, false otherwise.
	 * @param itemInstance The ItemInstance to add to inventory.
	 * @return boolean
	 */
	public boolean addItem(ItemInstance itemInstance){
		// Non stackable item
		if(itemInstance.getTemplate().getMaxStack() == 1){
			// Inventory is not full
			if(!isInventoryFull()) {
				_itemList.add(itemInstance);
				return true;
			} else {
				// Ask user to free one inventory slot
				if(askToFreeSlot())
					return addItem(itemInstance);
				// if we user did not free one inventory slot
				return false;
			}
		}
		// Stackable item
		else {
			addStackToExistingItems(itemInstance);
			if(itemInstance.getStack() > 0) // If there is stack left to add to inventory
				addNewItem(itemInstance);
		}

		// When reaching this point, either all stack of item has been added to inventory either inventory is full
		if(itemInstance.getStack() > 0){
			System.out.println("Impossible d'ajouter " + itemInstance.getStack() + " * " + itemInstance.getTemplate().getName() + " à l'inventaire");
			if(askToFreeSlot()){
				return addItem(itemInstance);
			} else {
				return false;
			}
		} else {
			System.out.println("Item ajouté à votre inventaire");
			return true;
		}
	}

	/**
	 * Add as many stack as possible
	 * to items in inventory with same template
	 * ID of given itemInstance. Return
	 * number of stack that couldn't be added.
	 * @param itemInstance ItemInstance you want stacks to be added to existing item
	 * @return int
	 */
	public int addStackToExistingItems(ItemInstance itemInstance){
		// This represent number of stack we can add before reaching maxStack
		int maxAmountToAdd;
		// This represent number of stack that was really added to existing item
		int addedAmount;
		for(ItemInstance uniqueItem: getItemsById(itemInstance.getTemplate().getId())){
			// Calculate how many stack can we add until reach maxStack of item
			maxAmountToAdd = uniqueItem.getTemplate().getMaxStack() - uniqueItem.getStack();
			// Calculate how many stack we will add to item
			addedAmount = Math.min(maxAmountToAdd,itemInstance.getStack());
			uniqueItem.addStack(addedAmount);
			itemInstance.removeStack(addedAmount);
		}
		return itemInstance.getStack();
	}

	/**
	 * Add as many non-stackable items
	 * as possible and return the number
	 * of items that couldn't be
	 * added to inventory.
	 * @param itemInstance item to add
	 * @return int number of stack left
	 */
	public int addNonStackableItem(ItemInstance itemInstance){
		itemInstance.getStack();
		for(int i = 0; i < itemInstance.getStack(); i++){
			if(isInventoryFull())
				return itemInstance.getStack();
			_itemList.add(ItemGenerator.getInstance().createItem(itemInstance.getTemplate(),1).get(0));
			itemInstance.removeStack(1);
		}
		return itemInstance.getStack();
	}

	/**
	 * Add a new item to inventory,
	 * it means this item will
	 * take one free slot in inventory and
	 * it cannot be added to stacks of
	 * an existing item in inventory.
	 * Return stack that couldn't been added
	 * to inventory.
	 * @param itemInstance ItemInstance to add to inventory
	 * @return int number of stack left to add
	 */
	public int addNewItem(ItemInstance itemInstance){
		// While inventory is not full AND there is stack to add to inventory
		while(!isInventoryFull() && itemInstance.getStack() > 0){
			// This represent the number of stack that will set for the new item
			int addedAmount = Math.min(itemInstance.getStack(),itemInstance.getTemplate().getMaxStack());
			_itemList.add(new ItemInstance(itemInstance.getTemplate().getId(),addedAmount));
			itemInstance.removeStack(addedAmount);
		}
		return itemInstance.getStack();
	}

	/**
	 * Ask player to free one
	 * of his inventory slot by
	 * throwing an item.
	 * Return true if player
	 * threw an item.
	 * @return boolean
	 */
	public boolean askToFreeSlot(){
		System.out.println("Voici la liste des items que vous pouvez jeter:");
		ArrayList<ItemInstance> throwableItemList = getThrowableItems();
		int count = 0;
		for (ItemInstance itemInstance: throwableItemList) {
			System.out.println((count + 1) + ": " + itemInstance.getTemplate().getName() + " [x" + itemInstance.getStack() + "]");
			count++;
		}
		System.out.println("Autre: Quitter");
		int choice = InputFunction.getIntInput();

		// Ask player if he sure about his decision
		if(InputFunction.askConfirmation()){
			// Player is sure about his desicion
			if(choice > 0 && choice <= throwableItemList.size()){
				// Player did choose a correct item, remove it
				_itemList.remove(throwableItemList.get(choice - 1));
				return true;
			}
			return false;
		}else {
			return askToFreeSlot();
		}
	}

	/**
	 * Delete an item from the list of ItemInstance
	 * @param itemInstance Item to be removed from the list
	 */
	public void deleteItem(ItemInstance itemInstance){
		_itemList.remove(itemInstance);
	}
	/**
	 * Return the EquipmentInstance equipped on this
	 * EquipSlot, return null if there is no equipped item
	 * on this EquipSlot.
	 * @param equipSlot The equipSlot you want the equipped item to be returned
	 * @return EquipmentInstance
	 */
	public EquipmentInstance getEquippedItem(ItemEquipSlot equipSlot){
		for (ItemInstance item: _itemList)
		{
			if(item instanceof EquipmentInstance
					&& ((EquipmentInstance)item).getTemplate().getEquipSlot() == equipSlot
					&& ((EquipmentInstance)item).isEquipped()){
				return (EquipmentInstance)item;
			}
		}
		return null;
	}
	/**
	 * Return all equipped ItemInstance in
	 * an ArrayList.
	 * @return ArrayList
	 */
	public ArrayList<EquipmentInstance> getAllEquippedItems(){
		ArrayList<EquipmentInstance> allEquippedItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			if(item instanceof EquipmentInstance && ((EquipmentInstance)item).isEquipped())
				allEquippedItems.add((EquipmentInstance)item);
		}
		return allEquippedItems;
	}
	/**
	 * Return all ItemInstance that are not equipped
	 * in an arrayList.
	 * @return ArrayList
	 */
	public ArrayList<ItemInstance> getAllNonEquippedItems() {
		ArrayList<ItemInstance> allEquippedItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			// If item is not equipped
			if(item instanceof EquipmentInstance && !((EquipmentInstance)item).isEquipped())
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
			if(item instanceof EquipmentInstance
				&& ((EquipmentInstance)item).getTemplate().isEquipable()
				&& !((EquipmentInstance)item).isEquipped())
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
			// if the item is not equipped and sellable
			if (itemInstance instanceof EquipmentInstance
					&& !((EquipmentInstance)itemInstance).isEquipped()
					&& itemInstance.getTemplate().isSellable()) {
				sellableItems.add(itemInstance);
			}
		}
		return sellableItems;
	}

	public ArrayList<ItemInstance> getThrowableItems() {
		ArrayList<ItemInstance> itemList = new ArrayList<>();
		for(ItemInstance itemInstance:_itemList)
			// If item is throwable and not equipped
			if(itemInstance.isThrowable())
				itemList.add(itemInstance);

		return itemList;
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
			if (itemInstance instanceof EquipmentInstance && ((EquipmentInstance)itemInstance).isEquipped()) {
				System.out.println("{EQUIPE} " + itemInstance.getTemplate().getName());
			} else {
				System.out.println("[x" + itemInstance.getStack() + "]" + itemInstance.getTemplate().getName());
			}
		}
	}
	/**
	 * Return the first item
	 * matching the given ID.
	 * Return null if no item
	 * match given ID.
	 * @param id ID to item to return
	 * @return ItemInstance
	 */
	public ItemInstance getItemById(int id){
		for(ItemInstance itemInstance:_itemList){
			if (itemInstance.getTemplate().getId() == id)
				return itemInstance;
		}
		return null;
	}
	/**
	 * Return all items matching
	 * given ID.
	 * @param id Id to match
	 * @return ArrayList
	 */
	public ArrayList<ItemInstance> getItemsById(int id) {
		ArrayList<ItemInstance> arrayList = new ArrayList();
		for(ItemInstance itemInstance:_itemList){
			if (itemInstance.getTemplate().getId() == id)
				arrayList.add(itemInstance);
		}
		return arrayList;
	}

	/**
	 * Remove given amount of stack
	 * to a given itemInstance.
	 * @param itemInstance Item to remove stacks from
	 * @param stack Amount of stack to remove
	 */
	public void removeItemStack(ItemInstance itemInstance, int stack){
		itemInstance.removeStack(stack);
		if(itemInstance.getStack() <= 0) {
			// There is no more stack of it, remove it from inventory
			_itemList.remove(itemInstance);
		}
	}
}