package DaD.item;

import DaD.calculator.Calculator;
import DaD.creature.Hero;

/**
 * Created by Clovis on 06/03/2017.
 */
public class ItemInstance
{
	private int _stack;
	private Boolean _equipped;
	private final ItemTemplate _itemTemplate;

	public ItemInstance(int itemTemplateId){
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
		_stack = 1;
		_equipped = false;
	}
	public ItemInstance(ItemTemplate itemTemplate){
		_itemTemplate = itemTemplate;
		_stack = 1;
		_equipped = false;
	}
	public ItemInstance(int itemTemplateId, boolean equipped, int stack){
		_stack = stack;
		_equipped = equipped;
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
	}

	public ItemTemplate getTemplate(){
		return _itemTemplate;
	}

	public int getStack(){
		return _stack;
	}
	public void addStack(int stack){
		_stack += stack;
	}
	public void retrieveStack(int stack){
		_stack -= stack;
	}

	public void equip(Hero hero){
		ItemInstance oldEquippedItem = hero.getInventory().getEquippedItem(_itemTemplate.getEquiSlot());
		// If there is already an equiped item on the equipSlot we unequip it
		if(oldEquippedItem != null){
			unequipWithoutCalculate(oldEquippedItem);
		}
		// Set the new item as equipped
		_equipped = true;
		// then call calculator to calculate all the new stats
		Calculator.getInstance().calculateAllStats(hero);
	}

	public void unequip(Hero hero){
		// Set the item as Unequipped
		_equipped = false;
		// then call calculator so it calculate all the hero stat again
		Calculator.getInstance().calculateAllStats(hero);
	}

	private void unequipWithoutCalculate(ItemInstance item){
		_equipped = false;
	}

	public Boolean isEquipped(){
		return _equipped;
	}
}