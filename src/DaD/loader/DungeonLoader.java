package DaD.loader;

import DaD.commons.MultiValueSet;
import DaD.dungeon.DungeonHolder;
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
 * Singleton used to load the dungeon information out of
 * the dungeon file.
 * @see DungeonHolder
 * @see DaD.dungeon.DungeonTemplate
 */
public class DungeonLoader {
    /**
     * Private instance of class.
     */
    private static final DungeonLoader _instance = new DungeonLoader();
    /**
     * Dungeon configuration file.
     */
    private static final String ALL_DUNGEONS = "AllDungeons.xml"; // path from the folder root
    /**
     * MultiValueSet used to store information about extracted dungeon.
     */
    private final MultiValueSet _dungeonInformation = new MultiValueSet();
    /**
     * Number of dungeon extracted from file
     */
    private int _dungeonCount;

    /**
     * Accessor for private instance of class.
     * @return DungeonLoader
     */
    public static final DungeonLoader getInstance()
    {
        return _instance;
    }

    /**
     * Private constructor of class.
     */
    private DungeonLoader() {}

    /**
     * Function that will browse the configuration file.
     * <p>
     *     For each node corresponding to a dungeon information, we
     *     will call the function {@link #encodeDungeons(Node) encodeDungeons}
     *     to retrieve information from the node.
     *     When information are finally retrieved, we create a {@link DaD.dungeon.DungeonTemplate dungeonTemplate}
     * </p>
     * @throws Exception If we reach end of file too early of read a malformated information.
     */
    public void loadDungeons() throws Exception{
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(new File(ALL_DUNGEONS));

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
                    final Node actualNode = racineNoeuds.item(i); // this represent the whole <dungeon> </dungeon> beacon
                   encodeDungeons(actualNode);
                    DungeonHolder.getInstance().createTemplate(_dungeonInformation);
                    _dungeonInformation.clear();
                    _dungeonCount++;
                }
            }
        }
        catch (final ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function called by loadDungeon to create a dungeon from the retrieved
     * information in the configuration file.
     * <p>
     *     The node used here represent the parent node for all the
     *     dungeon information. For each sub-node corresponding to a
     *     {@link DaD.dungeon.DungeonRoomTemplate dungeonRoom} information,
     *     we call the function {@link #extractRoom(Node) extractRoom}.
     *     When information are retrieved, we create a {@link DaD.dungeon.DungeonRoomTemplate}
     *     and add it to the list of rooms of this dungeon.
     * </p>
     * @param actualNode Node of the XML file where the dungeon information start.
     * @see DaD.dungeon.DungeonTemplate
     * @see DaD.generator.DungeonGenerator
     */
    private void encodeDungeons(Node actualNode){
        NodeList childNodeList = actualNode.getChildNodes();
        _dungeonInformation.set(actualNode.getAttributes().getNamedItem("id").getNodeName(),actualNode.getAttributes().getNamedItem("id").getNodeValue());
        _dungeonInformation.set(actualNode.getAttributes().getNamedItem("name").getNodeName(),actualNode.getAttributes().getNamedItem("name").getNodeValue());
        for(int j = 0; j < childNodeList.getLength(); j++){
            Node childNode = childNodeList.item(j);
            if (childNode.getNodeType() == 3) // Because of xml format, we need to skip the blank and new line
                continue;
            if(childNode.getAttributes().getNamedItem("name") != null){
                _dungeonInformation.set(childNode.getAttributes().getNamedItem("name").getNodeValue(),childNode.getAttributes().getNamedItem("value").getNodeValue());
            }
            if (childNode.getNodeName().equals("rewardList")) {
                NodeList rewardList = childNode.getChildNodes();
                for (int k = 0; k < rewardList.getLength(); k++) {
                    Node reward = rewardList.item(k);
                    if (reward.getNodeType() == 3) // Because of xml format, we need to skip the blank and new line
                        continue;
                    _dungeonInformation.set(reward.getAttributes().getNamedItem("name").getNodeValue(), reward.getAttributes().getNamedItem("value").getNodeValue());
                }
            }
            if (childNode.getNodeName().equals("roomList")) {
                ArrayList<MultiValueSet> allRooms = new ArrayList<>();
                NodeList roomList = childNode.getChildNodes();
                for(int k = 0; k < roomList.getLength(); k++){
                    Node roomNode = roomList.item(k);
                    if (roomNode.getNodeType() == 3)
                        continue;
                    if(roomNode.getNodeName().equals("room")){
                        allRooms.add(extractRoom(roomNode));
                    }
                }
                _dungeonInformation.set("rooms",allRooms);
            }
        }
    }

    /**
     * Function called by encodeDungeons to create a Room from the retrieved
     * information in the configuration file.
     * <p>
     *     The node used here is where all the room information are.
     *     For each sub-node corresponding to a {@link DaD.data.types.MonsterInfo},
     *     we call the function {@link #extractMonster(Node) extractMonster}.
     * </p>
     * @param roomNode Node of the XML file where the room information start.
     * @return MultiValueSet
     * @see DaD.dungeon.DungeonRoomTemplate
     */
    private MultiValueSet extractRoom(Node roomNode){
        // Here node is <room></room>
        MultiValueSet room = new MultiValueSet();
        room.set("id",roomNode.getAttributes().getNamedItem("id").getNodeValue());
        NodeList monsterListNode = roomNode.getChildNodes();
        ArrayList<MultiValueSet> monsters = new ArrayList<>();
        for(int l = 0; l < monsterListNode.getLength(); l++){
            Node monsterNode = monsterListNode.item(l);
            if (monsterNode.getNodeType() == 3)
                continue;
            monsters.add(extractMonster(monsterNode));
        }
        room.set("monsters", monsters);
        return room;
    }

    /**
     * Function that retrieves all {@link DaD.data.types.MonsterInfo} and return them in a MultiValueSet.
     * @param node Node of the XML where the monster information start.
     * @return MultiValueSet
     */
    private MultiValueSet extractMonster(Node node){
        // Here node is <monster> </monster> node
        MultiValueSet monster = new MultiValueSet();
        monster.set("id", node.getAttributes().getNamedItem("id").getNodeValue());
        NodeList nodeList = node.getChildNodes();
        for(int i = 0; i < nodeList.getLength(); i++){
            Node subNode = nodeList.item(i);
            if(subNode.getNodeType() == 3)
                continue;
            monster.set(subNode.getAttributes().getNamedItem("name").getNodeValue(),subNode.getAttributes().getNamedItem("value").getNodeValue());
        }
        return monster;
    }
}