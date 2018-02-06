package DaD.city;

import DaD.creature.Hero;
import DaD.item.ItemHolder;
import DaD.item.ItemInstance;
import DaD.item.ItemTemplate;

import java.util.Scanner;

/**
 * Created by Clovis on 09/02/2017.
 * Singleton used to handle item
 * buying and selling process.
 */
public class Blacksmith
{
	/**
	 * Private instance of class.
	 */
	private static final Blacksmith _instance = new Blacksmith();
	/**
	 * Array containing template id
	 * corresponding to items that the
	 * blacksmith sells.
	 */
	private int _itemTemplateIdList[];
	/**
	 * All possible choices for the hero
	 */
	private static final String[] _options = {"Acheter des objets","Vendre des objets","Quitter le shop"};
	/**
	 * Sell rate, when selling an item
	 * we apply this sell rate to reduce the price.
	 */
	private final double _sellRate = 0.7;

	/**
	 * Constructor of class.
	 */
	private Blacksmith(){}

	/**
	 *Accessor for private instance of class.
	 * @return Blacksmith
	 */
	public static Blacksmith getInstance() {
		return _instance;
	}

	/**
	 * Display the main menu where hero
	 * will be prompted choices.
	 * @param hero Hero entering the black smith menu
	 */
	public void blacksmithMenu(Hero hero){
		// First call this method to charge / change / update the inventory of the blacksmith
		initializeBlacksmithInventory(hero);

		boolean stayInShop = true;
		Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
		System.out.println("Que veux-tu faire ?");

		while(stayInShop) {
			//Display all options to the player
			for (int i = 0; i < _options.length; i++) {
				System.out.println((i + 1) + " : " + _options[i]);
			}
			try {
				int choice; // Variable that represent the player's choice
				choice = scanner.nextInt(); // Get the player's choice
				switch (choice) {
					case 1:
						buyItemsMenu(hero);
						break;
					case 2:
						sellItemsMenu(hero);
						break;
					case 3:
						stayInShop = false;
						break;
				}
			}catch (Exception e){
				System.out.println("Ce n'est pas un choix valide.");
			}
		}
	}

	/**
	 * Depending on hero level, the blacksmith
	 * will have different items to sell.
	 * @param hero Hero entering the black smith menu
	 */
	private void initializeBlacksmithInventory(Hero hero){
		// Later rework this to add full set of items to the list depending on the hero level.
		if(hero.getLevel() < 10){
			_itemTemplateIdList = new int[4];
			_itemTemplateIdList[0] = 9;
			_itemTemplateIdList[1] = 10;
			_itemTemplateIdList[2] = 11;
			_itemTemplateIdList[3] = 12;
		} else if(hero.getLevel() < 20) {
			// Add higher level items here
		} else {
			// Add rare / legendary items here
		}
	}

	/**
	 * Main menu where hero can choose which
	 * item he wants to buy.
	 * @param hero Hero who wants to buy items
	 */
	private void buyItemsMenu(Hero hero){
		System.out.println("Voici la liste des objets en vente:");
		for(int i = 0; i < _itemTemplateIdList.length; i++){
			ItemTemplate template = ItemHolder.getInstance().getItem(_itemTemplateIdList[i]);
			System.out.println((i+1) + ": (" + (int)template.getPrice() +"g) "  + template.getName() + " [Lv." + template.getRequiredLevel() + "]");
			template.displayBonus();
		}
		System.out.println("Autre: Quitter");
		System.out.println("Quelle item voulez-vous acheter ?");
		Scanner scanner = new Scanner(System.in);
		int choice;
		String input;
		try{
			input = scanner.nextLine();
			choice = Integer.parseInt(input);
			// Get the template corresponding to the templateId chosen by player
			buyItem(hero,ItemHolder.getInstance().getItem(_itemTemplateIdList[choice - 1]));

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Test if hero can buy item and prompt
	 * message depending on it.
	 * @param hero Hero buying item
	 * @param itemTemplate Template of the item bought by hero
	 */
	private void buyItem(Hero hero, ItemTemplate itemTemplate){
		if(hero.getGold() < itemTemplate.getPrice()) {
			System.out.println("Vous n'avez pas assez d'argent !");
			return;
		}
		// Create a new ItemInstance from the template
		ItemInstance itemInstance = new ItemInstance(itemTemplate);
		if(hero.getInventory().addItem(itemInstance)){
			hero.decreaseGold(itemTemplate.getPrice());
			System.out.println("L'objet est maintenant dans votre inventaire!");
		} else {
			System.out.println("Impossible d'ajouter l'item à votre inventaire!");
		}
	}

	/**
	 * Main menu where can choose which
	 * item he wants to sell.
	 * @param hero Hero who wants to sell items.
	 */
	private void sellItemsMenu(Hero hero){
		System.out.println("Voici la liste de vos objets (qui peuvent se vendre):");
		int count = 1;
		for(ItemInstance itemInstance : hero.getInventory().getAllSellableItems()){
			System.out.println(count + ": {" + (int)(itemInstance.getTemplate().getPrice() * _sellRate) + "} " + itemInstance.getTemplate().getName());
			count++;
		}
		System.out.println("Quelle item voulez-vous vendre?");
		Scanner scanner = new Scanner(System.in);
		int choice;
		try{
			choice = scanner.nextInt();
			// Get the templateId of the selected item
			sellItem(hero,hero.getInventory().getAllSellableItems().get(choice - 1));

		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Ask hero quantity he wants to sell and
	 * sell them.
	 * @param hero Hero selling his items
	 * @param itemInstance Item hero wants to sell
	 */
	private void sellItem(Hero hero, ItemInstance itemInstance){
		// If this a non stackable item
		if(itemInstance.getTemplate().getMaxStack() == 1){
			// First retrieve the price of the item, delete it and then add gold to the hero
			double goldGained = itemInstance.getTemplate().getPrice() * _sellRate;
			hero.getInventory().deleteItem(itemInstance);
			hero.addGold(goldGained);
			System.out.println("Item vendu avec succès ! (+" + (int)goldGained + " gold)");
		} else {
			System.out.println("Vous possedez " + itemInstance.getStack() + " examplaires, combien voulez-vous en vendre ?");
			Scanner scanner = new Scanner(System.in);
			int stackSelled;
			String input;
			try{
				input = scanner.nextLine();
				stackSelled = Integer.parseInt(input);
				double goldGained = itemInstance.getTemplate().getPrice() * stackSelled * _sellRate;
				itemInstance.retrieveStack(stackSelled);
				// If we selled all stacks we just delete the item from inventory
				if(itemInstance.getStack() < 1){
					hero.getInventory().deleteItem(itemInstance);
				}
				hero.addGold(goldGained);
				System.out.println("Item vendu avec succès ! (+" + (int)goldGained + " gold)");
			} catch (Exception e){
				e.printStackTrace();
				System.out.println("Ce n'est pas un choix valide!");
			}
		}
	}
}
