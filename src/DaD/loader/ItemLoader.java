package DaD.loader;

import DaD.Commons.Collections.MultiValueSet;
import DaD.Template.ItemTemplate;
import DaD.data.types.ItemType;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.Stats;
import DaD.Holder.ItemHolder;
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
import java.util.List;

/**
 * Created by Clovis on 29/05/2017.
 * Singleton used to load the races information out of
 * the race configuration file.
 * @see ItemTemplate
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
	 * @see List
	 */
	private final List<Env> _bonusList = new ArrayList<>();
	/**
	 * List containing all requirements to equip
	 * an equipment
	 */
	private final List<Env> _requirementList = new ArrayList<>();
	/**
	 * List containing all effects of a misc
	 */
	private final List<Env> _effectList = new ArrayList<>();
	/**
	 * Total of gear, weapons and armor, loaded.
	 */
	private int _equipmentCount = 0;
	/**
	 * Total of goods, usable and non usable items, loaded.
	 */
	private int _miscCount = 0;

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
	 * to find node containing {@link ItemTemplate} information.
	 * <p>
	 *     Depending on the type of items we
	 *     will call {@link #encodeMisc(Node) encode Goods} or
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
					final Node actualNode = racineNoeuds.item(i); // this represent the whole <item> </item>

					ItemType itemType = ItemType.valueOf(actualNode.getAttributes().getNamedItem("type").getNodeValue().toUpperCase());

					switch(itemType){
						case EQUIPMENT:
							_itemStat.set("itemType",ItemType.EQUIPMENT);
							encodeGear(actualNode);
							_equipmentCount++;
							break;
						case MISC:
							_itemStat.set("itemType",ItemType.MISC);
							encodeMisc(actualNode);
							_miscCount++;
							break;
					}
					// Create the itemTemplate, stock it into the itemTemplate holder then clear the MultiValueSet
					ItemHolder.getInstance().createTemplate(_itemStat);
					_itemStat.clear();
					_bonusList.clear();
					_requirementList.clear();
					_effectList.clear();
				}
			}
		}
		catch (final ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function called by loadItems to create an equipment from the retrieved
	 * information in the configuration file.
	 * @param actualNode Node of the XML file where the weapon information start.
	 * @see ItemTemplate
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
					_bonusList.add(encodeEnv(subNode));
				}
			}
			if(childNode.getNodeName().equals("requirement")){
				NodeList subNodeList = childNode.getChildNodes();
				for(int k = 0; k < subNodeList.getLength(); k++){
					Node subNode = subNodeList.item(k);
					if (subNode.getNodeType() == 3)
						continue;
					// Add the bonus to the list of all bonus
					_requirementList.add(encodeEnv(subNode));
				}
			}
		}
		_itemStat.set("bonusList", _bonusList);
		_itemStat.set("requirementList", _requirementList);
	}
	/**
	 * Function called by loadItems to create a misc from the retrieved
	 * information in the configuration file.
	 * @param actualNode Node of the XML file where the armor information start.
	 * @see DaD.Template.MiscTemplate
	 */
	private void encodeMisc(Node actualNode){
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
					_effectList.add(encodeEnv(subNode));
				}
			}
		}
		_itemStat.set("effectList", _effectList);
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

	public int getEquipmentCount() {
		return _equipmentCount;
	}

	public int getMiscCount(){
		return _miscCount;
	}
}
