package DaD.item;

import DaD.Template.EquipmentTemplate;
import DaD.Template.ItemTemplate;
import DaD.Template.MiscTemplate;
import DaD.calculator.Calculator;
import DaD.creature.Creature;
import DaD.creature.Hero;
import DaD.data.types.Stats.Env;

import java.util.List;

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
        if(_usageLeft > 0 && getTemplate().getMaxUsage() > 0 && getTemplate().getEffectList().size() > 0)
            return true;
        return false;
    }

    /**
     * If item {@link #isUsable()} call
     * {@link Calculator#calculateAllStats(Hero, List)}
     * with effect list. Otherwise print "nothing happens".
     * @param user
     */
    @Override
    public void use(Creature user){
        if(!isUsable())
            System.out.println("Rien ne se passe !");
        else{
            System.out.println(user.getName() + " utilise " + getTemplate().getName() + " !");
            // Calculate all new stats once effect has been used
            Calculator.getInstance().calculateAllStats((Hero)user,getTemplate().getEffectList());
            // Decrease usageLeft
            _usageLeft--;
        }
    }

    @Override
    public MiscTemplate getTemplate(){
        return (MiscTemplate)super.getTemplate();
    }

    public MiscInstance(ItemTemplate itemTemplate){
        super(itemTemplate);
    }
}
