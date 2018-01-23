package DaD.loader;

import DaD.commons.MultiValueSet;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.item.ItemDropInfo;
import DaD.monster.MonsterHolder;
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

/**
 * Created by Clovis on 09/06/2017.
 * Singleton used to load the monster template information out of
 * the monster configuration file.
 * @see DaD.monster.MonsterTemplate
 * @see DaD.loader
 */
public class MonsterLoader
{
	/**
	 * Private instance of class.
	 */
	private static final MonsterLoader _instance = new MonsterLoader();
	/**
	 * Monster template configuraton file.
	 */
	private static final String ALL_MONSTERS = "AllMonsters.xml"; // path from the folder root
	/**
	 * MultiValueSet containing information about the
	 * monster template.
	 * @see MultiValueSet
	 */
	private final MultiValueSet _monsterInformation = new MultiValueSet();
	/**
	 * Total of monster loaded.
	 */
	private int _monsterCount;

	/**
	 * Accessor for private instance of class.
	 * @return MonsterLoader
	 */
	public static final MonsterLoader getInstance()
	{
		return _instance;
	}

	/**
	 * Private Constructor of class.
	 */
	private MonsterLoader() {}

	/**
	 * Accessor for total of monster loaded.
	 * @return int
	 */
	public int getMonsterCount(){
		return _monsterCount;
	}

	/**
	 * 	/**
	 * Loading function that browse configuration file
	 * to find node containing {@link DaD.monster.MonsterTemplate} information.
	 * @throws Exception If a monster with an ID that already exists is loaded.
	 */
	public void loadMonsters() throws Exception{
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();
			final Document document = builder.parse(new File(ALL_MONSTERS));

			//Récupère et affiche l'élèment racine
			final Element racine = document.getDocumentElement();

			//On récupère tous les noeuds enfants
			final NodeList racineNoeuds = racine.getChildNodes();

			//On récupère le nombre de noeuds enfants
			final int nbRacineNoeuds = racineNoeuds.getLength();

			// For each node children
			for (int i = 0; i<nbRacineNoeuds; i++) {
				if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE)
				{ // We only reads user created information (<monster> for example)
					final Node actualNode = racineNoeuds.item(i); // this represent the whole <monster> </monster> beacon
					encodeMonsters(actualNode);
					MonsterHolder.getInstance().createTemplate(_monsterInformation);
					_monsterCount++;
					_monsterInformation.clear();
				}
			}
		}
		catch (final ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Function called by {@link #loadMonsters()} to create a monster template
	 * from the retrieved information in the configuration file.
	 * @param actualNode Node of the XML file where the monster template information start.
	 * @see DaD.monster.MonsterTemplate
	 */
	private void encodeMonsters(Node actualNode) {
		ArrayList<ItemDropInfo> itemDropInfoList = new ArrayList();
		NodeList childNodeList = actualNode.getChildNodes();
		_monsterInformation.set(actualNode.getAttributes().getNamedItem("id").getNodeName(),actualNode.getAttributes().getNamedItem("id").getNodeValue());
		_monsterInformation.set(actualNode.getAttributes().getNamedItem("name").getNodeName(),actualNode.getAttributes().getNamedItem("name").getNodeValue());
		_monsterInformation.set(actualNode.getAttributes().getNamedItem("race").getNodeName(),actualNode.getAttributes().getNamedItem("race").getNodeValue());
		for(int j = 0; j < childNodeList.getLength(); j++){
			Node childNode = childNodeList.item(j);
			if (childNode.getNodeType() == 3) // Because of xml format, we need to skip the blank and new line
				continue;

			if(childNode.getAttributes().getNamedItem("name") != null){
				_monsterInformation.set(childNode.getAttributes().getNamedItem("name").getNodeValue(),childNode.getAttributes().getNamedItem("value").getNodeValue());
			}

			if (childNode.getNodeName().equals("for")) {
				NodeList subNodeList = childNode.getChildNodes();
				for(int k = 0; k < subNodeList.getLength(); k++){
					Node statNode = subNodeList.item(k);
					if (statNode.getNodeType() == 3)
						continue;
					String statName = statNode.getAttributes().getNamedItem("name").getNodeValue();
					StatType statType = StatType.valueOf(statNode.getNodeName());
					Stats stat = Stats.valueOf(statNode.getAttributes().getNamedItem("stat").getNodeValue().toUpperCase());
					double value = Double.parseDouble(statNode.getAttributes().getNamedItem("value").getNodeValue());
					int order = Integer.parseInt(statNode.getAttributes().getNamedItem("order").getNodeValue());
					_monsterInformation.set(statName,new Env(value,statType,stat,order));
				}
			}
			if (childNode.getNodeName().equals("reward")) {
				NodeList subNodeList = childNode.getChildNodes();
				for(int k = 0; k < subNodeList.getLength(); k++){
					Node statNode = subNodeList.item(k);
					if (statNode.getNodeType() == 3)
						continue;

					int itemId = Integer.parseInt(statNode.getAttributes().getNamedItem("itemId").getNodeValue());
					int maxDrop = Integer.parseInt(statNode.getAttributes().getNamedItem("maxDrop").getNodeValue());
					double dropRate = Double.parseDouble(statNode.getAttributes().getNamedItem("dropRate").getNodeValue());
					itemDropInfoList.add(new ItemDropInfo(itemId,maxDrop,dropRate));
				}
			}
		}
		_monsterInformation.set("itemDropInfoList",itemDropInfoList);
		_monsterCount++;
	}
}

