package DaD.item;

import DaD.Holder.ItemHolder;
import DaD.Template.EquipmentTemplate;
import DaD.Template.ItemTemplate;

public class EquipmentInstance extends ItemInstance{
    /**
     * True if item is equipped.
     */
    private boolean _equipped;
    /**
     * Actual durability of equipement
     */
    private int _durability;
    /**
     * Constructor of class.
     * @param itemTemplate The template of the item instance.
     */
    public EquipmentInstance(ItemTemplate itemTemplate){
        super(itemTemplate);
        _equipped = false;
    }

    /**
     * Constructor of class.
     * @param itemTemplateId The id of the template.
     * @param stack Number of item stacked.
     */
    public EquipmentInstance(int itemTemplateId, int stack){
        super(itemTemplateId,stack);
        _equipped = false;
    }

    public EquipmentInstance(int itemTemplateId, boolean equipped, int stack){
        super(itemTemplateId,stack);
        this._equipped = equipped;
    }

    public EquipmentInstance(int itemTemplateId, boolean equipped, int stack, int durability){
        super(itemTemplateId,stack);
        this._equipped = equipped;
        this._durability = durability;
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
     * @param equipped New value for equipped
     */
    public void setEquipped(boolean equipped){
        _equipped = equipped;
    }

    @Override
    public EquipmentTemplate getTemplate(){
        return (EquipmentTemplate)super.getTemplate();
    }

    public int getDurability() {
        return _durability;
    }

    /**
     * Return true if item is not equipped,
     * false otherwise.
     * @return boolean
     */
    @Override
    public boolean isThrowable(){
        return !_equipped;
    }
}
