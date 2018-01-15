package DaD.city;

import DaD.creature.Hero;
import DaD.item.ItemHolder;
import DaD.item.ItemInstance;
import DaD.item.ItemTemplate;

import java.util.Scanner;

/**
 * Created by Clovis on 09/02/2017.
 */
public class Blacksmith
{
	private static final Blacksmith _instance = new Blacksmith();
	private int _itemTemplateIdList[];
	private static final String[] _options = {"Acheter des objets","Vendre des objets","Quitter le shop"};
	// This is used to determine the price of an item we sell
	private final double _sellRate = 0.8;
	private Blacksmith(){}
	public static Blacksmith getInstance() {
		return _instance;
	}

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

	private void buyItemsMenu(Hero hero){
		System.out.println("Voici la liste des objets en vente:");
		for(int i = 0; i < _itemTemplateIdList.length; i++){
			ItemTemplate template = ItemHolder.getInstance().getItem(_itemTemplateIdList[i]);
			System.out.println((i+1) + ": {" + template.getPrice() +"gold} [Lv. " + template.getRequiredLevel() + " requis] " + template.getName());
		}
		System.out.println("Quelle item voulez-vous acheter ?");
		Scanner scanner = new Scanner(System.in);
		int choice;
		String input;
		try{
			input = scanner.nextLine();
			choice = Integer.parseInt(input);
			// Get the template corresponding to the templateId choosen by player
			buyItem(hero,ItemHolder.getInstance().getItem(_itemTemplateIdList[choice - 1]));

		} catch (Exception e){
			e.printStackTrace();
		}
	}
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
			System.out.println("Impossible d'ajouter l'item a votre inventaire!");
		}
	}

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
