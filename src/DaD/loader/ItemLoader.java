package DaD.loader;

import DaD.commons.MultiValueSet;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.Stats;
import DaD.item.ItemHolder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Clovis on 29/05/2017.
 * Singleton used to load the races information out of
 * the race configuration file.
 * @see DaD.item.ItemTemplate
 * @see DaD.loader
 */
public class ItemLoader
{
	/**
	 * Private instance of class.
	 */
	private static final ItemLoader _instance = new ItemLoader();
	/**
	 * Item configuration file.
	 */
	private static final String ALL_ITEMS = "AllItems.xml"; // path from the folder root
	/**
	 * MultiValueSet containing information about the item template.
	 * @see MultiValueSet
	 */
	private final MultiValueSet _itemStat = new MultiValueSet();
	/**
	 * ArrayList containing all Env / stats of the item template.
	 * @see Env
	 * @see ArrayList
	 */
	private final ArrayList<Env> _allBonus = new ArrayList<>();
	/**
	 * Total of gear, weapons and armor, loaded.
	 */
	private int _gearCount = 0;
	/**
	 * Total of goods, usable and non usable items, loaded.
	 */
	private int _goodsCount = 0;

	/**
	 * Accessor for private instance of class.
	 * @return ItemLoader
	 */
	public static final ItemLoader getInstance()
	{
		return _instance;
	}

	/**
	 * Private constructor of class.
	 */
	private ItemLoader(){}

	/**
	 * Loading function that browse configuration file
	 * to find node containing {@link DaD.item.ItemTemplate} information.
	 * <p>
	 *     Depending on the type of items we
	 *     will call {@link #encodeGoods(Node) encode Goods} or
	 *     {@link #encodeGear(Node) encode Gear}.
	 * </p>
	 * @throws Exception If an item with an ID that already exists is loaded.
	 */
	public void loadItems() throws Exception{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File(ALL_ITEMS));

			//Récupère et affiche l'élèment racine
			final Element racine = document.getDocumentElement();

			//On récupère tous les noeuds enfants
			final NodeList racineNoeuds = racine.getChildNodes();

			//On récupère le nombre de noeuds enfants
			final int nbRacineNoeuds = racineNoeuds.getLength();

			//Pour chaque noeufs enfants
			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE)
				{ // We only reads user created information (<armor> for example)
					final Node actualNode = racineNoeuds.item(i); // this represent the whole <weapon> </weapon> or <armor> </armor> beacon
					String itemType = actualNode.getNodeName();

					// Depending on the type of item, we will parse information  differently
					switch(itemType){
						case "gear":
							encodeGear(actualNode);
							_gearCount++;
							break;
					}
					// Create the itemTemplate, stock it into the itemTemplate holder then clear the MultiValueSet
					ItemHolder.getInstance().createTemplate(_itemStat);
					_itemStat.clear();
					_allBonus.clear();
				}
			}
		}
		catch (final ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display all stats of an item template from the given HashMap.
	 * @param itemStat HashMap containing all stats of the item.
	 */
	private void displayItemsStats(HashMap<String,Object> itemStat){
		System.out.println("Début de l'affichage _itemStats");
		String keys[] = {"id","name","price","type","weight","requiredLevel","slot","StatType","order","stat","value"};
		for (String key:itemStat.keySet()) {
			System.out.println(key + ": " + itemStat.get(key));
		}
	}

	/**
	 * Function called by loadItems to create a weapon from the retrieved
	 * information in the configuration file.
	 * @param actualNode Node of the XML file where the weapon information start.
	 * @see DaD.item.ItemTemplate
	 */
	private void encodeGear(Node actualNode){
		NodeList childNodeList = actualNode.getChildNodes();
		_itemStat.set(actualNode.getAttributes().getNamedItem("id").getNodeName(),actualNode.getAttributes().getNamedItem("id").getNodeValue());
		_itemStat.set(actualNode.getAttributes().getNamedItem("name").getNodeName(),actualNode.getAttributes().getNamedItem("name").getNodeValue());
		for(int j = 0; j < childNodeList.getLength(); j++){
			Node childNode = childNodeList.item(j);
			if (childNode.getNodeType() == 3) // Because of xml format, we need to skip the blank and new line
				continue;

			if(childNode.getAttributes().getNamedItem("name") != null){
				_itemStat.set(childNode.getAttributes().getNamedItem("name").getNodeValue(),childNode.getAttributes().getNamedItem("value").getNodeValue());
			}

			if (childNode.getNodeName().equals("for")) {
				NodeList subNodeList = childNode.getChildNodes();
				for(int k = 0; k < subNodeList.getLength(); k++){
					Node subNode = subNodeList.item(k);
					if (subNode.getNodeType() == 3)
						continue;
					// Add the bonus to the list of all bonus
					_allBonus.add(encodeEnv(subNode));
				}
			}
		}
		_itemStat.set("allBonus",_allBonus);
	}
	/**
	 * Function called by loadItems to create a armor from the retrieved
	 * information in the configuration file.
	 * @param actualNode Node of the XML file where the armor information start.
	 * @see DaD.item.ItemTemplate
	 */
	private void encodeGoods(Node actualNode){
		NodeList childNodeList = actualNode.getChildNodes();
		_itemStat.set(actualNode.getAttributes().getNamedItem("id").getNodeName(),actualNode.getAttributes().getNamedItem("id").getNodeValue());
		_itemStat.set(actualNode.getAttributes().getNamedItem("name").getNodeName(),actualNode.getAttributes().getNamedItem("name").getNodeValue());
		for(int j = 0; j < childNodeList.getLength(); j++){
			Node childNode = childNodeList.item(j);
			if (childNode.getNodeType() == 3) // Because of xml format, we need to skip the blank and new line
				continue;

			if(childNode.getAttributes().getNamedItem("name") != null){
				_itemStat.set(childNode.getAttributes().getNamedItem("name").getNodeValue(),childNode.getAttributes().getNamedItem("value").getNodeValue());
			}

			if (childNode.getNodeName().equals("for")) {
				NodeList subNodeList = childNode.getChildNodes();
				for(int k = 0; k < subNodeList.getLength(); k++){
					Node subNode = subNodeList.item(k);
					if (subNode.getNodeType() == 3)
						continue;
					// Add the bonus to the list of all bonus
					_allBonus.add(encodeEnv(subNode));
				}
			}
		}
		_itemStat.set("allBonus",_allBonus);
	}

	/**
	 * From a given node we will extract information and create an Env.
	 * This Env is linked to an armor or a weapon.
	 * @param node Node of the XML file where the armor information start.
	 * @return Env
	 */
	private Env encodeEnv(Node node){
		return new Env(
				// Value
				Double.parseDouble(node.getAttributes().getNamedItem("value").getNodeValue()),
				// StatsType (ADD,MULTIPLY,...)
				DaD.data.types.Stats.StatType.valueOf(node.getNodeName().toUpperCase()),
				// Stat (attack,def,...)
				Stats.valueOf(node.getAttributes().getNamedItem("stat").getNodeValue().toUpperCase()),
				// Order (0x10,0x20,...)
				Integer.parseInt(node.getAttributes().getNamedItem("order").getNodeValue())
		);
	}
}
