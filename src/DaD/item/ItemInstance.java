package DaD.item;

import DaD.data.types.ItemEquipSlot;

/**
 * Created by Clovis on 06/03/2017.
 * This is this instance of an item, this is what
 * the player will have in his inventory and what
 * is dropped by monsters. Each instance is created
 * from a {@link ItemTemplate template}.
 * @see ItemTemplate
 */
public class ItemInstance
{
	/**
	 * The number of item this instance has.
	 * <p>
	 *     This is really useful when having potion or 10 times the same item.
	 *     Rather than having 3 times the same item, we will it only once but
	 *     with a stack value of 3.
	 *     BE CAREFUL EQUIPPED ITEM CANNOT BE STACKED!
	 *     IT WILL BE 2 DIFFERENT INSTANCES!
	 * </p>
	 */
	private int _stack;
	/**
	 * Template of the item instance.
	 * @see ItemTemplate
	 */
	private final ItemTemplate _itemTemplate;
	/**
	 * Equip slot where the item should be equipped.
	 * @see ItemEquipSlot
	 */
	private ItemEquipSlot _equipSlot;

	/**
	 * Constructor of class.
	 * @param itemTemplateId The id of the template.
	 */
	public ItemInstance(int itemTemplateId){
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
		_stack = 1;
		_equipSlot = ItemEquipSlot.NONE; // Item is not equipped
	}

	/**
	 * Constructor of class.
	 * @param itemTemplate The template of the item instance.
	 */
	public ItemInstance(ItemTemplate itemTemplate){
		_itemTemplate = itemTemplate;
		_stack = 1;
		_equipSlot = ItemEquipSlot.NONE; // Item is not equipped
	}

	/**
	 * Constructor of class.
	 * @param itemTemplateId The id of the template.
	 * @param stack Number of item stacked.
	 */
	public ItemInstance(int itemTemplateId, int stack){
		_stack = stack;
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
		_equipSlot = ItemEquipSlot.NONE;
	}

	/**
	 * Return the item template.
	 * @return ItemTemplate
	 */
	public ItemTemplate getTemplate(){
		return _itemTemplate;
	}

	/**
	 * Return the value of stack.
	 * @return int
	 */
	public int getStack(){
		return _stack;
	}

	/**
	 * Add X stacks to the item instance.
	 * <p>
	 *     BE CAREFUL! Here we do not verify if
	 *     we overpass the maximum stack of the item
	 *     template! This must be verified before calling
	 *     this function.
	 * </p>
	 * @param stack Number of stack to add
	 */
	public void addStack(int stack){
		_stack += stack;
	}

	/**
	 * Remove X stacks to the item instance.
	 * <p>
	 *     BE CAREFUL! Here we not verify if number of
	 *     stacks goes below 0! This must be verified
	 *     outside of this function.
	 * </p>
	 * @param stack Number of stack to remove.
	 */
	public void retrieveStack(int stack){
		_stack -= stack;
	}

	/**
	 * Return true if {@link #_equipSlot} is
	 * different from {@link ItemEquipSlot#NONE}.
	 * @return Boolean
	 */
	public Boolean isEquipped(){
		return (_equipSlot != ItemEquipSlot.NONE);
	}

	/**
	 * Return attribute {@link #_equipSlot},
	 * this represent where the item is equipped.
	 * Value is "NONE" if item is not equipped.
	 * @return
	 */
	public ItemEquipSlot getEquipSlot() {
		return _equipSlot;
	}
	/**
	 * Set the value of {@link #_equipSlot}.
	 * @param equipSlot New value for attribute
	 */
	public void setEquipSlot(ItemEquipSlot equipSlot){
		_equipSlot = equipSlot;
	}
}