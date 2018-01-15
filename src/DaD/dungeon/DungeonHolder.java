package DaD.dungeon;

import DaD.commons.MultiValueSet;

import java.util.HashMap;
import java.util.Map;

public class DungeonHolder {

    private static final DungeonHolder _instance = new DungeonHolder();
    private final Map<Integer,DungeonTemplate> _templateList =  new HashMap<>();

    public static final DungeonHolder getInstance()
    {
        return _instance;
    }

    private DungeonHolder() {}

    public DungeonTemplate getTemplate(int id){
        return _templateList.get(id);
    }

    public void createTemplate(MultiValueSet dungeonStat) throws Exception{
        int dungeonId = dungeonStat.getInteger("id");
        // If there is already an existing dungeon with this ID, we throw an exception
        if(_templateList.get(dungeonId) != null)
            throw new Exception("DungeonInstance ID [" + dungeonId + "] already exist.");
        _templateList.put(dungeonId,new DungeonTemplate(dungeonStat));
    }

    public void displayTemplateList(){
        for (int i = 0; i < _templateList.size(); i++){
            if(_templateList.get(i) != null)
                _templateList.get(i).displayTemplate();
        }
    }

    public Map<Integer,DungeonTemplate> getTemplateList(){
        return _templateList;
    }
}
