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
	 * Slot where instances of
	 * this items should be equipped.
	 */
	private final ItemEquipSlot _equipSlot;
	/**
	 * Max durability this template can reach.
	 * <p>
	 *     You cannot have an higher durability on
	 *     an itemInstance than the maxDurability
	 *     of the template it's based on.
	 * </p>
	 */
	private final int _maxDurability;
	/**
	 * Weight of item. Actually not used in inventory.
	 */
	private final double _weight;
	/**
	 * Is the item equipable or not
	 */
	private final boolean _equipable;
	/**
	 * Is item sellable or not
	 */
	private final boolean _sellable;
	/**
	 * Description of the item, can be short stories.
	 */
	private final String _description;

	/**
	 * The list containing all Env of the item.
	 * @see Env
	 */
	private final ArrayList<Env> _allBonus = new ArrayList<>();

	/**
	 * Constructor of class.
	 * @param itemsStats MultiValueSet containing all info about the template.
	 */
	public ItemTemplate(MultiValueSet itemsStats){
		_id = itemsStats.getInteger("id");
		_name = itemsStats.getString("name");
		_requiredLevel = itemsStats.getInteger("requiredLevel");
		_price = itemsStats.getInteger("price");
		_rarity = ItemRarity.VALUES[itemsStats.getInteger("rarity")];
		_maxStack = itemsStats.getInteger("maxStack");
		_maxDurability = itemsStats.getInteger("maxDurability");
		_weight = itemsStats.getInteger("weight");

		_equipable = itemsStats.getBool("equipable");
		_sellable = itemsStats.getBool("sellable");

		_description = itemsStats.getString("description");
		_itemType = (ItemType)itemsStats.getEnum("itemType",ItemType.class);
		_equipSlot = (ItemEquipSlot)itemsStats.getEnum("equipSlot",ItemEquipSlot.class);
		ArrayList<Env> allBonus = itemsStats.getArrayList("allBonus");
		_allBonus.addAll(allBonus);
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
	 * Return the list containing all Env of the item.
	 * Each env represent a bonus / malus of the item.
	 * @return ArrayList
	 * @see Env
	 */
	public ArrayList<Env> getAllBonus(){
		return _allBonus;
	}

	/**
	 * Return the list of Env that concern this stat.
	 * @param stat The specific stat you want to focus on.
	 * @return ArrayList
	 */
	public ArrayList<Env> getBonusByStat(Stats stat){
		ArrayList<Env> bonusList = new ArrayList<>();
		for(Env bonus: _allBonus){
			if(bonus.getStat() == stat)
				bonusList.add(bonus);
		}
		return bonusList;
	}

	/**
	 * Return true if item is equipable.
	 * <p>
	 *     ItemTemplate cannot be equipped, but
	 *     the ItemInstance based on this template can be
	 * </p>
	 * @return Boolean
	 * @see ItemInstance
	 */
	public Boolean isEquipable(){
		return _equipable;
	}

	/**
	 * Return {@link #_sellable}.
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
	public void displayTemplate(){
		System.out.println("Id: " + _id);
		System.out.println("_name: " + _name);
		System.out.println("_requiredLevel: " + _requiredLevel);
		System.out.println("_price: " + _price);
		System.out.println("_rarity: " + _rarity);
		System.out.println("_maxStack: " + _maxStack);
		System.out.println("_maxDurability: " + _maxDurability);
		System.out.println("_weight: " + _weight);
		System.out.println("_equipable: " + _equipable);
		System.out.println("_sellable: " + _sellable);
		System.out.println("_description: " + _description);
	}

	/**
	 * Display all bonus, Env, of this template.
	 */
	public void displayBonus(){
		for (Env env: _allBonus) {
			env.displayEnv();
		}
	}

	public ItemEquipSlot getEquipSlot() {
		return _equipSlot;
	}
}