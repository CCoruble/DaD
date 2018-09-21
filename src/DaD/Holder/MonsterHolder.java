package DaD.Holder;

import DaD.Commons.Collections.MultiValueSet;
import DaD.creature.MonsterInstance;
import DaD.Template.MonsterTemplate;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.HashMap;

/**
 * Created by Clovis on 13/06/2017.
 * Singleton use to store all {@link MonsterTemplate templates} and
 * link them a unique ID.
 *
 * @see MonsterTemplate
 * @see HashMap
 */
public class MonsterHolder {
	/**
	 * Private instance of class.
	 */
	private static final MonsterHolder _instance = new MonsterHolder();

	/**
	 * MAp containing all {@link MonsterTemplate Monstertemplate} of the game.
	 *
	 * <p>
	 *     This will be used when loading {@link MonsterTemplate templates}.
	 *     We will store them all in this Map, later when generating instance
	 *     of monster we will retrieve the {@link MonsterTemplate template} from this HashMap with an ID.
	 * </p>
	 *
	 * @see MonsterTemplate
	 * @see MonsterInstance
	 */
	private final TIntObjectMap<MonsterTemplate> _templateList = new TIntObjectHashMap<>();

	/**
	 * Private constructor of class.
	 */
	private MonsterHolder() {}

	/**
	 * Accessor for private instance of class.
	 * @return MonsterHolder
	 */
	public static final MonsterHolder getInstance()
	{
		return _instance;
	}

	/**
	 * Return the template corresponding to the given id.
	 *
	 * <p>
	 *     The ArrayList containing all {@link MonsterTemplate template} is fulfilled
	 *     by order. Monster with ID 1 is inserted first, then
	 *     order 2 ... Order is defined in XML file containing all
	 *     {@link MonsterTemplate templates}.
	 * </p>
	 * @param id ID of the template of the monster.
	 * @return MonsterTemplate
	 * @see DaD.loader.MonsterLoader
	 * @see MonsterTemplate
	 */
	public MonsterTemplate getTemplate(int id){
		return _templateList.get(id);
	}

	/**
	 * From a given MultiValueSet containing all information
	 * about the {@link MonsterTemplate template}, we create one and add it to the list.
	 *
	 * <p>
	 *     Name of function might not been the best one.
	 *     Here we check if a {@link MonsterTemplate template} with id in the MultiValueSet
	 *     already exists, if yes we throw exception.
	 *     Otherwise we call the {@link MonsterTemplate#MonsterTemplate(MultiValueSet) MonsterTemplate constructor} and add
	 *     the object returned to the list.
	 * </p>
	 * @param monsterStat A MultiValueSet containing all the MonsterTemplate information. (INCLUDING HIS ID !)
	 * @throws Exception If a {@link MonsterTemplate template} with the same ID already exists in the list.
	 * @see MultiValueSet
	 * @see MonsterTemplate#MonsterTemplate(MultiValueSet)
	 */
	public void createTemplate(MultiValueSet monsterStat) throws Exception{
		int monsterId = monsterStat.getInteger("id");
		// If there is already an existing monster with this ID, we throw an exception
		if(_templateList.get(monsterId) != null){
			throw new Exception("Monster ID \"" + monsterId + "\" already exist");
		}
		_templateList.put(monsterId,new MonsterTemplate(monsterStat));
	}

	/**
	 * Simply browse the list of {@link MonsterTemplate templates} and call
	 * the toString function.
	 */
	public void displayTemplateList(){
		for (int i = 0; i < _templateList.size(); i++) {
			if(_templateList.get(i) != null)
				_templateList.get(i).displayTemplate();
		}
	}

	/**
	 * Return the full list of template.
	 * <p>
	 *     The map returned contains MonsterTemplate as
	 *     value and Integer as Key.
	 * </p>
	 * @return Map
	 */
	public TIntObjectMap<MonsterTemplate> getTemplateList(){
		return _templateList;
	}
}
