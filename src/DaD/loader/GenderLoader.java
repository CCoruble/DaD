package DaD.loader;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.HeroGender;
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
 * Singleton used to load the Gender information out of
 * the Gender configuration file.
 * @see HeroGender
 * @see DaD.loader
 */
public class GenderLoader {

    /**
     * Private instance of class.
     */
    private static final GenderLoader _instance = new GenderLoader();
    /**
     * Gender configuration file.
     */
    private static final String ALL_HERO_GENDERS = "AllHeroGenders.xml";
    /**
     * Total of gender loaded during the process.
     */
    private int _heroGenderCount;

    /**
     * Accessor for private instance of class.
     * @return GenderLoader
     */
    public static final GenderLoader getInstance() {
        return _instance;
    }

    /**
     * Private constructor of class.
     */
    private GenderLoader(){}

    /**
     * Loading function that brose configuration file
     * to find node containing {@link HeroGender} information.
     */
    public void loadGenders(){
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document = builder.parse(new File(ALL_HERO_GENDERS));

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
                    encodeHeroGender(actualNode);
                    _heroGenderCount++;
                }
            }
        }
        catch (final ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set / adjust the values of {@link HeroGender} with information
     * in the node.
     * @param actualNode Node of the XML file where the dungeon information start.
     * @see HeroGender
     * @see HeroGender#adjustGenderInformation(MultiValueSet)
     */
    private void encodeHeroGender(Node actualNode){
        MultiValueSet genderInformation = new MultiValueSet();
        NodeList childNodeList = actualNode.getChildNodes();
        genderInformation.set(actualNode.getAttributes().getNamedItem("name").getNodeName(),actualNode.getAttributes().getNamedItem("name").getNodeValue());
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
                genderInformation.set(statName,new Env(value,statType,stat,order));
            }
        }
        HeroGender.valueOf(genderInformation.getString("name")).adjustGenderInformation(genderInformation);
    }
}
