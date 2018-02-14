package DaD.Holder;

import DaD.Commons.Collections.MultiValueSet;
import DaD.creature.Hero;
import DaD.Template.DungeonTemplate;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton used to store all
 * {@link DungeonTemplate} int the same place.
 * @see Map
 */
public class DungeonHolder {

    /**
     * Private instance of class.
     */
    private static final DungeonHolder _instance = new DungeonHolder();
    /**
     * Map containing all DungeonTemplate.
     */
    private final TIntObjectMap<DungeonTemplate> _templateList = new TIntObjectHashMap<>();

    /**
     * Accessor for private instance of class.
     * @return DungeonHolder
     */
    public static final DungeonHolder getInstance()
    {
        return _instance;
    }

    /**
     * Private constructor of class.
     */
    private DungeonHolder() {}

    /**
     * Return a {@link DungeonTemplate} matching the
     * given id.
     * @param id ID of the template
     * @return DungeonTemplate
     */
    public DungeonTemplate getTemplate(int id){
        return _templateList.get(id);
    }

    /**
     * Retrieve ID from MultiValueSet, if not other
     * template with this ID exists, call {@link DungeonTemplate#DungeonTemplate(MultiValueSet) DungeonTemplate constructor}
     * and add the returned object to the list.
     * @param dungeonStat MultiValueSet containing all information about the template
     * @throws Exception If template with this id already exists
     */
    public void createTemplate(MultiValueSet dungeonStat) throws Exception{
        int dungeonId = dungeonStat.getInteger("id");
        // If there is already an existing dungeon with this ID, we throw an exception
        if(_templateList.get(dungeonId) != null)
            throw new Exception("DungeonInstance ID [" + dungeonId + "] already exist.");
        _templateList.put(dungeonId,new DungeonTemplate(dungeonStat));
    }

    /**
     * Iterate on the Map {@link #_templateList} and
     * call {@link DungeonTemplate#displayTemplate()}
     * for each template.
     */
    public void displayTemplateList(){
        for (int i = 0; i < _templateList.size(); i++){
            if(_templateList.get(i) != null)
                _templateList.get(i).displayTemplate();
        }
    }

    /**
     * Return the Map containing all {@link DungeonTemplate}.
     * @return Map
     */
    public TIntObjectMap<DungeonTemplate> getTemplateList(){
        return _templateList;
    }

    /**
     * Return an ArrayList containing all dungeons the
     * player has unlocked. Obviously required level
     * and other conditions are checked before.
     * @param hero the hero that want to see his available dungeon
     * @return ArrayList
     */
    public ArrayList<DungeonTemplate> getAvailableDungeon(Hero hero){
        ArrayList<DungeonTemplate> availableDungeons = new ArrayList<>();
        for(int key : getTemplateList().keys()) {
            if(hero.getLevel() >= _templateList.get(key).getRequiredLevel())
                availableDungeons.add(_templateList.get(key));
        }
        return availableDungeons;
    }

}
