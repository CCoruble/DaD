package DaD.Template;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.ItemEquipSlot;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.Stats;
import DaD.item.ItemInstance;

import java.util.ArrayList;
import java.util.List;

public class EquipmentTemplate extends ItemTemplate{
    /**
     * Is the item equipable or not
     */
    private final boolean _equipable;
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
     * The list containing all Env of the item.
     * @see Env
     */
    private final ArrayList<Env> _bonusList = new ArrayList<>();
    /**
     * All stats requirement to equip this item
     */
    private final ArrayList<Env> _requirementList = new ArrayList<>();

    /**
     * Public constructor
     * @param itemStats multivalueSet containing all required information
     */
    public EquipmentTemplate(MultiValueSet itemStats){
        super(itemStats);
        _equipable = itemStats.getBool("equipable");
        _equipSlot = (ItemEquipSlot)itemStats.getEnum("equipSlot",ItemEquipSlot.class);
        List<Env> allBonus = itemStats.getArrayList("bonusList");
        _bonusList.addAll(allBonus);
        _maxDurability = itemStats.getInteger("maxDurability");
        List<Env> allRequirements = itemStats.getArrayList("requirementList");
        _requirementList.addAll(allRequirements);
    }

    /**
     * Return true if item is equipable.
     * <p>
     *     ItemTemplate cannot be equipped, but
     *     the ItemInstance based on this template can
     * </p>
     * @return Boolean
     * @see ItemInstance
     */
    public Boolean isEquipable(){
        return _equipable;
    }

    /**
     * Return the list containing all Env of the item.
     * Each env represent a bonus / malus of the item.
     * @return ArrayList
     * @see Env
     */
    public ArrayList<Env> getBonusList(){
        return _bonusList;
    }

    /**
     * Return the list of Env that concern this stat.
     * @param stat The specific stat you want to focus on.
     * @return ArrayList
     */
    public ArrayList<Env> getBonusByStat(Stats stat){
        ArrayList<Env> bonusList = new ArrayList<>();
        for(Env bonus: _bonusList){
            if(bonus.getStat() == stat)
                bonusList.add(bonus);
        }
        return bonusList;
    }

    /**
     * Return equip slot for this equipment
     * @return ItemEquipSlot
     */
    public ItemEquipSlot getEquipSlot() {
        return _equipSlot;
    }

    public List<Env> getRequirementList(){
        return _requirementList;
    }

    /**
     * Display all bonus, Env, of this template.
     */
    public void displayBonus(){
        for (Env env: _bonusList) {
            env.displayEnv();
        }
    }

    @Override
    public String toString(){
        String s = super.toString() + "\n";
        s += "_equipable: " + _equipable + "\n";
        s += "_equipSlot" + _equipSlot + "\n";
        s += "_maxDurability: " + _maxDurability + "\n";
        return s;
    }
}
