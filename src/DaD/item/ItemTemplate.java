package DaD.item;

import DaD.commons.MultiValueSet;
import DaD.data.types.ItemEquipSlot;
import DaD.data.types.ItemRarity;
import DaD.data.types.ItemType;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.Stats;

import java.util.ArrayList;

/**
 * Created by Clovis on 16/05/2017.
 */
public class ItemTemplate
{
	// ItemInstance properties
	private final int _id;
	private final String _name;
	private final int _requiredLevel;
	private final double _price;
	private final ItemRarity _rarity;
	private final int _maxStack; // -1 => infinite, 1 => can't be stacked, X => can be stacked X times
	private final ItemType _type;
	private final int _maxDurability;
	private final double _weight;
	private final boolean _equipable;
	private final String _description;
	private final ItemEquipSlot _equipSlot;

	// Bonus
	private final ArrayList<Env> _allBonus = new ArrayList<>();


	public ItemTemplate(MultiValueSet itemsStats){
		_id = itemsStats.getInteger("id");
		_name = itemsStats.getString("name");
		_requiredLevel = itemsStats.getInteger("requiredLevel");
		_price = itemsStats.getInteger("price");
		_rarity = ItemRarity.VALUES[itemsStats.getInteger("rarity")];
		_maxStack = itemsStats.getInteger("maxStack");
		_maxDurability = itemsStats.getInteger("maxDurability");
		_weight = itemsStats.getInteger("weight");

		// In the xml file "equipable" is equal to 0 or 1, depending on it we set the boolean value to true or false
		_equipable = itemsStats.getBool("equipable");

		_description = itemsStats.getString("description");
		_equipSlot = (ItemEquipSlot) itemsStats.getEnum("equipSlot",ItemEquipSlot.class);
		_type = (ItemType)itemsStats.getEnum("type",ItemType.class);

		ArrayList<Env> allBonus = itemsStats.getArrayList("allBonus");
		_allBonus.addAll(allBonus);
	}

	public int getId(){
		return _id;
	}

	public String getName(){
		return _name;
	}

	public ArrayList<Env> getAllBonus(){
		return _allBonus;
	}

	public ArrayList<Env> getBonusByStat(Stats stat){
		ArrayList<Env> bonusList = new ArrayList<>();
		for(Env bonus: _allBonus){
			if(bonus.getStat() == stat)
				bonusList.add(bonus);
		}
		return bonusList;
	}

	public Boolean isEquipable(){
		return _equipable;
	}

	public double getPrice(){
		return _price;
	}

	public int getMaxStack(){
		return _maxStack;
	}

	public ItemEquipSlot getEquiSlot(){
		return _equipSlot;
	}

	public int getRequiredLevel(){
		return _requiredLevel;
	}

	public void displayTemplate(){
		System.out.println("Id: " + _id);
		System.out.println("_name: " + _name);
		System.out.println("_requiredLevel: " + _requiredLevel);
		System.out.println("_rarity: " + _rarity);
		System.out.println("_maxStack: " + _maxStack);
		System.out.println("_maxDurability: " + _maxDurability);
		System.out.println("_weight: " + _weight);
		System.out.println("_equipable: " + _equipable);
		System.out.println("_description: " + _description);
	}
}