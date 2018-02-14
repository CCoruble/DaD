package DaD.item;

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
	 * True if item is equipped.
	 */
	private  boolean _equipped;

	/**
	 * Constructor of class.
	 * @param itemTemplate The template of the item instance.
	 */
	public ItemInstance(ItemTemplate itemTemplate){
		_itemTemplate = itemTemplate;
		_stack = 1;
		_equipped = false;
	}

	/**
	 * Constructor of class.
	 * @param itemTemplateId The id of the template.
	 * @param stack Number of item stacked.
	 */
	public ItemInstance(int itemTemplateId, int stack){
		_stack = stack;
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
		_equipped = false;
	}

	public ItemInstance(int itemTemplateId, boolean equipped, int stack){
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
		_equipped = equipped;
		_stack = stack;
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
	public void removeStack(int stack){
		_stack -= stack;
	}

	/**
	 * Return value of {@link #_equipped}.
	 * @return Boolean
	 */
	public Boolean isEquipped(){
		return _equipped;
	}
	/**
	 * Set {@link #_equipped} value.
	 * @param equipped
	 */
	public void setEquipped(boolean equipped){
		_equipped = equipped;
	}

	@Override
	public String toString() {
		return "[x" + getStack() + "] " + getTemplate().getName();
	}
}