package DaD.manager;

import DaD.city.Bank;
import DaD.city.Blacksmith;
import DaD.city.Church;
import DaD.city.Inn;
import DaD.Commons.Utils.InputFunction;
import DaD.Commons.Utils.Spacer;
import DaD.creature.Hero;
import DaD.dungeon.DungeonInstance;
import DaD.generator.HeroGenerator;
import DaD.handler.DungeonHandler;

/**
 * Created by Clovis on 07/02/2017.
 * Singleton used to display both loading / creating game menu
 * and in game menu when creation / loading is finished.
 */
public class GameManager
{
	/**
	 * Private Instance of class.
	 */
	private static final GameManager _instance = new GameManager();

	/**
	 * Used to know if we launch app on debug
	 * mode or not.
	 */
	public final boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().
			getInputArguments().toString().contains("jdwp");

	/**
	 * Option for loading / creating new game menu.
	 *
	 * @see GameManager#mainMenu()
	 */
	private static final String[] _mainMenuOptions = {"Créer une nouvelle partie","Continuer une partie"};

	/**
	 * Options for player once a game was loaded or created.
	 *
	 * @see GameManager#inGameMenu()
	 */
	private static final String[] _inGameMenuOptions = {"Entrer en donjon","Aller voir le forgeron","Dormir à  l'auberge",
			"Aller à l'église","Aller à la banque","Afficher tes caractéristiques","Acceder a ton inventaire"};

	/**
	 * Accessor for private Instance of class.
	 *
	 * @return GameManager
	 */
	public static final GameManager getInstance()
	{
		return _instance;
	}

	/**
	 * Private Constructor of class.
	 */
	private GameManager() {}

	/**
	 * Main menu of the game, where you will choose between
	 * starting a new game or loading an old one.
	 *
	 * @see SaveManager
	 */
	public void mainMenu(){
		boolean stay = true;
		while(stay) {
			// Display information to player
			Spacer.displayMainMenuSpacer();
			System.out.println("Bienvenue dans le jeu \"Dungeons and Dragons\" !");
			for (int i = 0; i < _mainMenuOptions.length; i++) {
				System.out.println((i + 1) + " : " + _mainMenuOptions[i]);
			}
			System.out.println("Autre: Quitter");
			Spacer.displayMainMenuSpacer();

			switch (InputFunction.getIntInput()) { // Action depending en the player's choice
				case 1: // Create a new game with a new hero
					if (HeroGenerator.getInstance().createNewHero()) // Return true if creating hero was successful
						inGameMenu();
					break;
				case 2: // Continue an existing game
					if (SaveManager.getInstance().load()) // load() return true if the load succeed, false other way
						inGameMenu(); // If it's true, the load succeed, we load the main menu with the hero
					break;
				default: // Quit game
					stay = false;
					break;
			}
		}
	}

	/**
	 * Main menu once loaded or created a new char.
	 * <p>
	 * From this menu will access to all parts of the game,
	 * like entering in a dungeon, save your game, buy items ...
	 */
	private void inGameMenu(){ // This is the main menu once the game has been initiated
		boolean stay = true;
		while(stay) {
			Spacer.displayInGameMenuSpacer();
			System.out.println("Bonjour " + Hero.getInstance().getName() + "!");
			System.out.println("Que veux-tu faire ?");
			for (int i = 0; i < _inGameMenuOptions.length; i++) {
				System.out.println((i + 1) + " : " + _inGameMenuOptions[i]);
			}
			System.out.println("Autre: Quitter");
			Spacer.displayInGameMenuSpacer();

			switch (InputFunction.getIntInput()) {
				case 1:
					// Go into the enterDungeon, choose difficulty then create the DungeonInstance
					DungeonInstance dungeon = DungeonHandler.getInstance().dungeonSetting(Hero.getInstance());
					if (dungeon != null) { // In case the hero left before choosing a dungeon
						DungeonHandler.getInstance().enterDungeon(Hero.getInstance(), dungeon);
					}
					break;
				case 2:
					Blacksmith.getInstance().blacksmithMenu(Hero.getInstance());
					break;
				case 3:
					// Go to the inn menu
					Inn.getInstance().innMenu(Hero.getInstance());
					break;
				case 4:
					// go to church
					Church.getInstance().churchMenu(Hero.getInstance());
					break;
				case 5:
					Bank.getInstance().bankMenu(Hero.getInstance());
					break;
				case 6:
					Hero.getInstance().displayFullCharacteristic();
					break;
				case 7:
					InventoryManager.getInstance().inventoryMenu(Hero.getInstance());
					break;
				default:
					// Ask confirmation before leaving
					if (InputFunction.askConfirmation()) {
						// Clean hero so stats are not used after
						Hero.getInstance().clearHero();
						stay = false;
					}
					break;
			}
		}
	}
}
