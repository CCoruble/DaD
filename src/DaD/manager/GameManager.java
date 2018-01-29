package DaD.manager;

import DaD.city.Bank;
import DaD.city.Blacksmith;
import DaD.city.Church;
import DaD.city.Inn;
import DaD.commons.Spacer;
import DaD.creature.Hero;
import DaD.dungeon.DungeonInstance;
import DaD.generator.HeroGenerator;
import DaD.handler.DungeonHandler;

import java.util.Scanner;

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
	private static final String[] _mainMenuOptions = {"Créer une nouvelle partie","Continuer une partie","Quitter le jeu"};

	/**
	 * Options for player once a game was loaded or created.
	 *
	 * @see GameManager#inGameMenu()
	 */
	private static final String[] _inGameMenuOptions = {"Entrer en donjon","Aller voir le forgeron","Dormir à  l'auberge",
			"Aller à l'église","Aller à la banque","Afficher tes caractéristiques","Acceder a ton inventaire","Quitter la partie"};

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
		while (true)
		{
			Scanner scanner = new Scanner(System.in);
			String input;
			int choice;

			//Display information to player
			Spacer.displayMainMenuSpacer();
			System.out.println("Bienvenue dans le jeu \"Dungeons and Dragons\" !");
			for(int i = 0; i < _mainMenuOptions.length; i++){
				System.out.println((i+1) + " : " + _mainMenuOptions[i]);
			}
			Spacer.displayMainMenuSpacer();
			try {
				input = scanner.next();
				choice = Integer.parseInt(input);

				switch (choice) { // Action depending en the player's choice
					case 1: // Create a new game with a new hero
						HeroGenerator.getInstance().createNewHero(); // create a new hero with the hero generator
						inGameMenu();
						break;
					case 2: // Continue an existing game
						if (SaveManager.getInstance().load()) // load() return true if the load succeed, false if the load fail
							inGameMenu(); // If it's true, the load succeed, we load the main menu with the hero
						break;
					case 3: // Quit game
						System.exit(0);
						break;
					default:
						throw new Exception();
				}
			} catch (Exception e){
				System.out.println("Ce n'est pas un choix valide!");
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
		Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
		String input;
		int choice;
		boolean inGame = true;

		while(inGame)
		{
			Spacer.displayInGameMenuSpacer();
			System.out.println("Bonjour " + Hero.getInstance().getName() + "!");
			System.out.println("Que veux-tu faire ?");
			for(int i = 0; i < _inGameMenuOptions.length; i++){
				System.out.println((i+1) + " : " + _inGameMenuOptions[i]);
			}
			Spacer.displayInGameMenuSpacer();

			try {
				input = scanner.next();
				choice = Integer.parseInt(input);
				switch (choice) {
					case 1:
						// Go into the dungeonMenu, choose difficulty then create the DungeonInstance
						DungeonInstance dungeon = DungeonHandler.getInstance().dungeonSetting();
						DungeonHandler.getInstance().StartDungeon(Hero.getInstance(), dungeon);
						break;
					case 2:
						Blacksmith.getInstance().blacksmithMenu(Hero.getInstance());
						break;
					case 3:
						// Go to the inn menu
						Inn.getInstance().innMenu();
						break;
					case 4:
						// go to church
						Church.getInstance().churchMenu();
						break;
					case 5:
						Bank.getInstance().bankMenu();
						break;
					case 6:
						System.out.println(Hero.getInstance().displayFullCharacteristic());
						break;
					case 7:
						InventoryManager.getInstance().inventoryMenu(Hero.getInstance());
						break;
					case 8:
						inGame = false;
						break;
					default:
						// This will go to catch and display the message
						throw new Exception();
				}
			} catch (Exception e){
				System.out.println("Ce n'est pas un choix valide !");
			}
		}
	}
}
