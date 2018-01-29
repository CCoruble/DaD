package DaD.loader;

import DaD.commons.Spacer;
import DaD.dungeon.DungeonHolder;
import DaD.item.ItemHolder;
import DaD.manager.GameManager;
import DaD.monster.MonsterHolder;
/**
 * Singleton used to launch all
 * loader in a specific order.
 * @see DaD.loader
 */
public class GeneralLoader {

    /**
     * Private instance of class.
     */
    private static final GeneralLoader _instance = new GeneralLoader();

    /**
     * Accessor for private instance of class.
     * @return GeneralLoader
     */
    public static final GeneralLoader getInstance()
    {
        return _instance;
    }

    /**
     * Private constructor of class.
     */
    private GeneralLoader() {}

    /**
     * Call all loader in a specific order.
     */
    public void loadAll(){
        try
        {
            // First load everything that does not require anything else pre-loaded
            ItemLoader.getInstance().loadItems();
            HeroRaceLoader.getInstance().loadHeroRaces();
            GenderLoader.getInstance().loadGenders();

            // Then load Monster => can be linked to Items
            MonsterLoader.getInstance().loadMonsters();
            // Then load Dungeons => linked to Monsters & Items
            DungeonLoader.getInstance().loadDungeons();

            if(GameManager.getInstance().isDebug){
                Spacer.displayDebugSpacer();
                ItemHolder.getInstance().displayAllItems();
                MonsterHolder.getInstance().displayTemplateList();
                DungeonHolder.getInstance().displayTemplateList();
                Spacer.displayDebugSpacer();
            }
        } catch (Exception e){
            System.out.println("Erreur lors du chargement des différents élèments du jeu !");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
