package DaD.Holder;

import DaD.Commons.Collections.MultiValueSet;
import DaD.Template.ItemTemplate;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.HashMap;

/**
 * Created by Clovis on 29/05/2017.
 * Singleton used to hold all ItemTemplate into
 * one unique place.
 * @see ItemTemplate
 */
public class ItemHolder
{
	/**
	 * Private instance of class.
	 */
	private static final ItemHolder _instance = new ItemHolder();

	/**
	 * HashMap containing the full ItemTemplate list.
	 * <p>
	 *     The key for this HashMap is an Integer and
	 *     the value is ItemTemplate.
	 *     The key is equal to the ID of the template,
	 *     the ID of the template can be found in the
	 *     item configuration file.
	 * </p>
	 * @see DaD.loader.ItemLoader
	 * @see ItemTemplate
	 */
	private final TIntObjectMap<ItemTemplate> _itemsList =  new TIntObjectHashMap<>();

	/**
	 * Accessor for private instance of class.
	 * @return ItemHolder
	 */
	public static final ItemHolder getInstance(){
		return _instance;
	}

	/**
	 * Private constructor of class.
	 */
	private ItemHolder() {}

	/**
	 * Create a template from a MultiValueSet containing
	 * all necessary information.
	 * <p>
	 *     Here we receive information about a template,
	 *     we call the constructor and add the template
	 *     create to the HashMap.
	 * </p>
	 * @param itemStat MultiValueSet containing the information
	 * @throws Exception If a template already use ID given in MultiValueSet
	 * @see ItemTemplate#ItemTemplate(MultiValueSet)
	 */
	public void createTemplate(MultiValueSet itemStat) throws Exception{
		int itemId = itemStat.getInteger("id");
		// If there is already an existing item with this ID, we throw an exception
		if(_itemsList.get(itemId) != null)
			throw new Exception("Item ID [" + itemId + "] already exist");
		_itemsList.put(itemId,new ItemTemplate(itemStat));
	}

	/**
	 * Return the item who has the given value as ID.
	 * <p>
	 *     Here ID given in MultiValueSet and key in the HashMap are the same!
	 * </p>
	 * @param order The ID of the item.
	 * @return ItemTemplate
	 */
	public ItemTemplate getItem(int order){
		return _itemsList.get(order);
	}

	/**
	 * Return the full HashMap containing all templates.
	 * <p>
	 *     The HashMap use an Integer as a key and
	 *     an ItemTemplate as value.
	 * </p>
	 * @return HashMap
	 */
	public TIntObjectMap<ItemTemplate> getTemplateList(){
		return _itemsList;
	}

	/**
	 * Display all item in the HashMap by calling the
	 * {@link ItemTemplate#displayTemplate()} function for each template.
	 */
	public void displayAllItems(){
		for(int i = 0; i < _itemsList.size(); i++){
			if(_itemsList.get(i) != null)
				_itemsList.get(i).displayTemplate();
		}
	}
}
