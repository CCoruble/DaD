package DaD.inventory;

import DaD.data.types.ItemEquipSlot;
import DaD.item.ItemInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 13/02/2017.
 */
public class Inventory
{
	private int _inventorySize;
	private ArrayList<ItemInstance> _itemList;
	private double _gold;

	public Inventory(ArrayList<ItemInstance> itemList,int inventorySize){
		_inventorySize = inventorySize;
		_itemList = itemList;
	}
	public Inventory(int inventorySize){
		_inventorySize = inventorySize;
		_itemList = new ArrayList<>();
	}

	//_inventorySize methods
	public int getInventorySize(){
		return _inventorySize;
	}
	public void setInventorySize(int inventorySize){
		_inventorySize = inventorySize;
	}
	public void increaseInventorySize(int bonusSize){
		_inventorySize += bonusSize;
	}
	public void decreaseInventorySize(int malusSize){
		_inventorySize -= malusSize;
	}
	public int getInventorySizeLeft(){
		return _inventorySize - _itemList.size();
	}
	public boolean isInventoryFull(){
		// If the size of the list containing the items is higher or the same as the inventory size
		return _itemList.size() >= _inventorySize;
	}
	public int getNumberOfItems(){
		return _itemList.size();
	}

	//_itemList methods
	public ArrayList<ItemInstance> getItemList(){
		return _itemList;
	}
	public void setItemList(ArrayList<ItemInstance> itemList){
		_itemList = itemList;
	}

	// Gold
	public double getGold(){
		return _gold;
	}
	public void setGold(double gold){
		_gold = gold;
	}

	// items
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
	public void deleteItem(ItemInstance itemInstance){
		_itemList.remove(itemInstance);
	}
	public ItemInstance getEquippedItem(ItemEquipSlot equipSlot){
		for (ItemInstance item: _itemList)
		{
			if(item.getTemplate().getEquiSlot() == equipSlot && item.isEquipped()){
				return item;
			}
		}
		return null;
	}
	public ArrayList<ItemInstance> getAllEquippedItems(){
		ArrayList<ItemInstance> allEquippedItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			if(item.isEquipped())
				allEquippedItems.add(item);
		}
		return allEquippedItems;
	}
	public ArrayList<ItemInstance> getAllNonEquippedITems() {
		ArrayList<ItemInstance> allEquippedItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			// If item is not equipped
			if(!item.isEquipped())
				allEquippedItems.add(item);
		}
		return allEquippedItems;
	}
	public ArrayList<ItemInstance> getAllEquipableItems(){
		ArrayList<ItemInstance> allEquipableItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			if(item.getTemplate().isEquipable())
				allEquipableItems.add(item);
		}
		return allEquipableItems;
	}
	public ArrayList<ItemInstance> getAllEquipableUnequippedItems(){
		ArrayList<ItemInstance> allEquipableItems = new ArrayList<>();
		for (ItemInstance item: _itemList) {
			// If the item can be equipped and not equipped yet !
			if(item.getTemplate().isEquipable() && !item.isEquipped())
				allEquipableItems.add(item);
		}
		return allEquipableItems;
	}
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


	// display
	public void displayInventory() {
		// Display all equipped items first
		for (ItemInstance itemInstance : _itemList) {
			if (itemInstance.isEquipped())
				System.out.println("{EQUIPE} " + itemInstance.getTemplate().getName());
		}
		// Then display all unequipped item
		for (ItemInstance itemInstance : _itemList) {
			if (!itemInstance.isEquipped())
				System.out.println("[x" + itemInstance.getStack() + "]" + itemInstance.getTemplate().getName());
		}
	}
	public void displayAllItems(){
		for(ItemInstance itemInstance:_itemList)
			System.out.println(itemInstance.getTemplate().getName());
	}
}