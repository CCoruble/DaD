package DaD.Template;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.Stats.Env;

import java.util.List;

public class MiscTemplate extends ItemTemplate{
    private int _maxUsage;
    private boolean _reloadable;
    private List<Env> _effectList;

    public MiscTemplate(MultiValueSet itemsStats){
        super(itemsStats);
        _maxUsage = itemsStats.getInteger("maxUsage");
        _reloadable = itemsStats.getBool("reloadable");
        _effectList = itemsStats.getArrayList("effectList");
    }

    public int getMaxUsage() {
        return _maxUsage;
    }

    public List<Env> getEffectList() {
        return _effectList;
    }

    @Override
    public String toString(){
        String s = super.toString() + "\n" ;
        s += "_maxUsage: " + _maxUsage + "\n";
        s += "_reloadable: " + _reloadable + "\n";
        s += "Effect list:" + "\n";
        for(Env env: _effectList){
            s += env.toString() + "\n";
        }
        return s;
    }
}
