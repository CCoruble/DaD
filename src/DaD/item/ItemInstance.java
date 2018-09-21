package DaD.item;

import DaD.Holder.ItemHolder;
import DaD.Template.ItemTemplate;

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
	 * Constructor of class.
	 * @param itemTemplate The template of the item instance.
	 */
	public ItemInstance(ItemTemplate itemTemplate){
		_itemTemplate = itemTemplate;
		_stack = 1;
	}

	/**
	 * Constructor of class.
	 * @param itemTemplateId The id of the template.
	 * @param stack Number of item stacked.
	 */
	public ItemInstance(int itemTemplateId, int stack){
		_stack = stack;
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
	}

	/**
	 * Return the item template.
	 * @return ItemTemplate
	 */
	public ItemTemplate getTemplate(){
		return _itemTemplate;
	}

	/**
	 * Return true if number of stack
	 * is higher or equals to maxStack.
	 * Stack should NEVER be higher than maxStack.
	 * @return boolean
	 */
	public boolean isAtMaxStack(){
		return _stack >= getTemplate().getMaxStack();
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
	private void addStack0(int stack){
		_stack += stack;
	}

	/**
	 * Add as much stack as possible and
	 * return stacks that couldn't be added
	 * to item
	 * @param stack stack to add
	 * @return int number of stack that couldn't be added
	 */
	public int addStack(int stack){
		int amountToAdd = Math.min(stack, getTemplate().getMaxStack() - getStack());
		addStack0(amountToAdd);
		return stack - amountToAdd;
	}

	/**
	 * Set value of stack.
	 * @param stack New value for stack
	 */
	public void setStack(int stack){
		_stack = stack;
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
	public void removeStack(int stack){
		_stack -= stack;
	}

	@Override
	public String toString() {
		return "[x" + getStack() + "] " + getTemplate().getName();
	}

	/**
	 * Return true if item can
	 * thrown
	 * @return boolean
	 */
	public boolean isThrowable(){
		return true;
	}
}