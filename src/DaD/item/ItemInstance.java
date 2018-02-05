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
	 * True if the item is equipped.
	 */
	private Boolean _equipped;
	/**
	 * Template of the item instance.
	 * @see ItemTemplate
	 */
	private final ItemTemplate _itemTemplate;

	/**
	 * Constructor of class.
	 * @param itemTemplateId The id of the template.
	 */
	public ItemInstance(int itemTemplateId){
		_itemTemplate = ItemHolder.getInstance().getItem(itemTemplateId);
		_stack = 1;
		_equipped = false;
	}

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
	 * @param equipped Is the item equiped or not.
	 * @param stack Number of item stacked.
	 */
	public ItemInstance(int itemTemplateId, boolean equipped, int stack){
		_stack = stack;
		_equipped = equipped;
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
	 * Return true if item Instance is equipped.
	 * @return Boolean
	 */
	public Boolean isEquipped(){
		return _equipped;
	}
	/**
	 * Set the value of {@link #_equipped}.
	 * @param equipped New value for attribute
	 */
	public void setEquipped(boolean equipped){
		_equipped = equipped;
	}
}