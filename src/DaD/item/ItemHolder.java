package DaD.item;

import DaD.commons.MultiValueSet;

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
	private final HashMap<Integer,ItemTemplate> _itemsList =  new HashMap<>();

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

	public void createTemplate(MultiValueSet itemStat) throws Exception{
		int itemId = itemStat.getInteger("id");
		// If there is already an existing item with this ID, we throw an exception
		if(_itemsList.get(itemId) != null)
			throw new Exception("Item ID [" + itemId + "] already exist");
		_itemsList.put(itemId,new ItemTemplate(itemStat));
	}
	public ItemTemplate getItem(int order){
		return _itemsList.get(order);
	}

	public HashMap<Integer,ItemTemplate> getTemplateList(){
		return _itemsList;
	}

	public void displayAllItems(){
		for(int i = 0; i < _itemsList.size(); i++){
			if(_itemsList.get(i) != null)
				_itemsList.get(i).displayTemplate();
		}
	}
}
