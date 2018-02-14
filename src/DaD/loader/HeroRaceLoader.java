package DaD.loader;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.HeroRace;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
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

/**
 * Singleton used to load the races information out of
 * the race configuration file.
 * @see HeroRace
 * @see DaD.loader
 */
public class HeroRaceLoader {

    /**
     * Private instance of class.
     */
    private static final HeroRaceLoader _instance = new HeroRaceLoader();
    /**
     * Race configuration file.
     */
    private static final String ALL_HERO_RACES = "AllHeroRaces.xml"; // path from the folder root
    /**
     * Total of race loaded during process.
     */
    private int _heroRaceCount;

    /**
     * Private constructor of class.
     */
    private HeroRaceLoader(){}

    /**
     * Accessor for private instance of class.
     * @return HeroRaceLoader
     */
    public static final HeroRaceLoader getInstance() {
        return _instance;
    }

    /**
     * Accessor for total of race loaded.
     * @return int
     */
    public int getHeroRaceCount(){
        return _heroRaceCount;
    }

    /**
     * Loading function that brose configuration file
     * to find node containing {@link HeroRace} information.
     */
    public void loadHeroRaces(){
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(new File(ALL_HERO_RACES));

            //Récupère et affiche l'élèment racine
            final Element racine = document.getDocumentElement();

            //On récupère tous les noeuds enfants
            final NodeList racineNoeuds = racine.getChildNodes();

            //On récupère le nombre de noeuds enfants
            final int nbRacineNoeuds = racineNoeuds.getLength();

            //Pour chaque noeufs enfants
            for (int i = 0; i<nbRacineNoeuds; i++) {
                if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE)
                {
                    final Node actualNode = racineNoeuds.item(i);
                    encodeHeroRace(actualNode);
                    _heroRaceCount++;
                }
            }
        }
        catch (final ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set / adjust the values of {@link HeroRace} with information
     * in the node.
     * @param actualNode Node of the XML file where the dungeon information start.
     * @see HeroRace
     * @see HeroRace#adjustRaceInformation(MultiValueSet)
     */
    private void encodeHeroRace(Node actualNode){
        MultiValueSet raceInformation = new MultiValueSet();
        NodeList childNodeList = actualNode.getChildNodes();
        raceInformation.set(actualNode.getAttributes().getNamedItem("name").getNodeName(),actualNode.getAttributes().getNamedItem("name").getNodeValue());
        for(int j = 0; j < childNodeList.getLength(); j++){
            Node childNode = childNodeList.item(j);
            if (childNode.getNodeType() == 3) // Because of xml format, we need to skip the blank and new line
                continue;

            if(childNode.getAttributes().getNamedItem("name") != null){
                String statName = childNode.getAttributes().getNamedItem("name").getNodeValue();
                StatType statType = StatType.valueOf(childNode.getNodeName());
                Stats stat = Stats.valueOf(childNode.getAttributes().getNamedItem("stat").getNodeValue());
                double value = Double.parseDouble(childNode.getAttributes().getNamedItem("value").getNodeValue());
                int order = Integer.parseInt(childNode.getAttributes().getNamedItem("order").getNodeValue());
                raceInformation.set(statName,new Env(value,statType,stat,order));
            }
        }
        HeroRace.valueOf(raceInformation.getString("name")).adjustRaceInformation(raceInformation);
    }
}