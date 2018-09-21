package DaD.item;

import DaD.Template.EquipmentTemplate;
import DaD.Template.ItemTemplate;
import DaD.Template.MiscTemplate;

public class MiscInstance extends ItemInstance implements IUsable{
    private int _usageLeft;

    public  MiscInstance(int templateId, int usageLeft, int stack){
        super(templateId,stack);
        this._usageLeft = usageLeft;
    }

    public int getUsageLeft() {
        return _usageLeft;
    }

    public void setUsageLeft(int usageLeft) {
        this._usageLeft = usageLeft;
    }

    @Override
    public boolean isUsable() {
        return _usageLeft > 0 || (getTemplate()).getMaxUsage() == 0;
    }

    @Override
    public void use(){
        if((getTemplate()).getEffectList().size() == 0)
            System.out.println("Rien ne se passe !");
    }

    @Override
    public MiscTemplate getTemplate(){
        return (MiscTemplate)super.getTemplate();
    }

    public MiscInstance(ItemTemplate itemTemplate){
        super(itemTemplate);
    }
}
