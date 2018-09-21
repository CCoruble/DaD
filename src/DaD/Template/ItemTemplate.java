package DaD.Template;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.ItemEquipSlot;
import DaD.data.types.ItemRarity;
import DaD.data.types.ItemType;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.Stats;
import DaD.item.ItemInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 16/05/2017.
 * Template from which ItemInstance are created.
 * Template are NEVER USED in game. Instead
 * player will ALWAYS USE {@link ItemInstance}.
 */
public class ItemTemplate
{
	/**
	 * ID of the template. Mostly used when
	 * creating a new ItemInstance.
	 */
	private final int _id;
	/**
	 * Name of the template. ItemInstance
	 * base on this template will have the same name.
	 */
	private final String _name;
	/**
	 * Required level to equip ItemInstance base on
	 * this template.
	 */
	private final int _requiredLevel;
	/**
	 * Base price when buying an ItemInstance
	 * based on this template.
	 */
	private final double _price;
	/**
	 * Rarity of the template. Doesn't affect stats only
	 * here to rank items.
	 * @see ItemRarity
	 */
	private final ItemRarity _rarity;
	/**
	 * Max amount of time an itemInstance
	 * based on this template can be stacked.
	 */
	private final int _maxStack; // -1 => infinite, 1 => can't be stacked, X => can be stacked X times
	/**
	 * Type of item
	 * @see DaD.data.types.ItemType
	 */
	private final ItemType _itemType;
	/**
	 * Weight of item. Actually not used in inventory.
	 */
	private final double _weight;
	/**
	 * Is item sellable or not
	 */
	private final boolean _sellable;
	/**
	 * Description of the item, can be short stories.
	 */
	private final String _description;

	/**
	 * Constructor of class.
	 * @param itemStats MultiValueSet containing all info about the template.
	 */
	public ItemTemplate(MultiValueSet itemStats){
		_id = itemStats.getInteger("id");
		_name = itemStats.getString("name");
		_requiredLevel = itemStats.getInteger("requiredLevel");
		_price = itemStats.getInteger("price");
		_rarity = ItemRarity.VALUES[itemStats.getInteger("rarity")];
		_maxStack = itemStats.getInteger("maxStack");
		_weight = itemStats.getInteger("weight");

		_sellable = itemStats.getBool("sellable");

		_description = itemStats.getString("description");
		_itemType = (ItemType)itemStats.getEnum("itemType",ItemType.class);

	}

	/**
	 * Return the id of the template.
	 * @return int
	 */
	public int getId(){
		return _id;
	}

	/**
	 * Return the name of the template.
	 * @return String
	 */
	public String getName(){
		return _name;
	}

	/**
	 * Return {@link #_sellable}.
	 * @return boolean
	 */
	public boolean isSellable(){
		return _sellable;
	}

	/**
	 * Return the base price of this item.
	 * <p>
	 *     ItemTemplate cannot be sell or buy, but
	 *     ItemInstance based on this template can be.
	 * </p>
	 * @return double
	 * @see ItemInstance
	 */
	public double getPrice(){
		return _price;
	}

	/**
	 * Return the maximum stack of this item.
	 * <p>
	 *     Item Template cannot be stacked, they are not usable
	 *     ingame! ItemInstance base on this template may be
	 *     stackable and stacked.
	 * </p>
	 * @return int
	 */
	public int getMaxStack(){
		// In case of infinite max stack, gold ca be stacked infinite time
		if(_maxStack == -1)
			return Integer.MAX_VALUE;
		return _maxStack;
	}

	/**
	 * Return the level required to equip this item.
	 * <p>
	 *     ItemTemplate cannot be equipped.
	 *     ItemInstance base on this template can
	 *     be equipped, if you meet the requirements.
	 * </p>
	 * @return int
	 */
	public int getRequiredLevel(){
		return _requiredLevel;
	}

	/**
	 * Display all attributes of the template.
	 */
	@Override
	public String toString(){
		String s = "Id: " + _id + "\n";
		s += "_name: " + _name + "\n";
		s += "_requiredLevel: " + _requiredLevel + "\n";
		s += "_price: " + _price + "\n";
		s += "_rarity: " + _rarity + "\n";
		s += "_maxStack: " + _maxStack + "\n";
		s += "_weight: " + _weight + "\n";
		s += "_sellable: " + _sellable + "\n";
		s += "_description: " + _description + "\n";
		s += "_itemType: " + _itemType + "\n";
		return s;
	}

	public ItemType getItemType() {
		return _itemType;
	}
}