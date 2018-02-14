package DaD.city;

import DaD.Commons.Utils.InputFunction;
import DaD.creature.Hero;
import DaD.Holder.ItemHolder;
import DaD.item.ItemInstance;
import DaD.Template.ItemTemplate;

import java.util.ArrayList;

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
	private ArrayList<Integer> _itemTemplateIdList = new ArrayList<>();
	/**
	 * All possible choices for the hero
	 */
	private static final String[] _options = {"Acheter des objets","Vendre des objets"};
	/**
	 * Sell rate, when selling an item
	 * we apply this sell rate to reduce the price.
	 */
	private final double _sellRate = 0.6;

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

		System.out.println("Que veux-tu faire ?");

		//Display all options to the player
		for (int i = 0; i < _options.length; i++) {
			System.out.println((i + 1) + " : " + _options[i]);
		}
		System.out.println("Autre: Quitter");
		switch (InputFunction.getIntInput()) {
			case 1:
				buyItemsMenu(hero);
				break;
			case 2:
				sellItemsMenu(hero);
				break;
			default:
				// Nothing, player just want to leave
		}
	}

	/**
	 * Depending on hero level, the blacksmith
	 * will have different items to sell.
	 * @param hero Hero entering the black smith menu
	 */
	private void initializeBlacksmithInventory(Hero hero){
		// Clear old id in it
		_itemTemplateIdList.clear();
		// Add item depending on hero level
		if(hero.getLevel() < 10){
			_itemTemplateIdList.add(10);
			_itemTemplateIdList.add(11);
			_itemTemplateIdList.add(12);
			_itemTemplateIdList.add(13);
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
		for(int i = 0; i < _itemTemplateIdList.size(); i++){
			ItemTemplate template = ItemHolder.getInstance().getItem(_itemTemplateIdList.get(i));
			System.out.println((i+1) + ": (" + (int)template.getPrice() +"g) "  + template.getName() + " [Lv." + template.getRequiredLevel() + "]");
			template.displayBonus();
		}
		System.out.println("Autre: Quitter");
		System.out.println("Quelle item voulez-vous acheter ?");
		int choice = InputFunction.getIntInput();

		// Player made a valid choice
		if(choice > 0 || choice <= _itemTemplateIdList.size())
			buyItem(hero,ItemHolder.getInstance().getItem(_itemTemplateIdList.get(choice - 1)));
	}

	/**
	 * Test if hero can buy item and prompt
	 * message depending on it.
	 * @param hero Hero buying item
	 * @param itemTemplate Template of the item bought by hero
	 */
	private void buyItem(Hero hero, ItemTemplate itemTemplate){
		// If either hero doesn't have item gold or not enough stack of it
		if(!hero.canAfford(itemTemplate.getPrice())) {
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
		System.out.println("Autre: Quitter");
		System.out.println("Quelle item voulez-vous vendre?");
		int choice = InputFunction.getIntInput();

		// Player made a non valid choice, he want to leave
		if(choice <= 0 || choice > hero.getInventory().getAllSellableItems().size())
			return;

		// Player did a valid choice, get the templateId of the selected item
		sellItem(hero,hero.getInventory().getAllSellableItems().get(choice - 1));
	}

	/**
	 * Ask hero how many stack
	 * of item he want to sell and
	 * then sell them.
	 * @param hero Hero selling his items
	 * @param itemInstance Item hero wants to sell
	 */
	private void sellItem(Hero hero, ItemInstance itemInstance){
		double goldGained;
		int stackSold;

		// Check how many stack does the player want to sell
		if(itemInstance.getTemplate().getMaxStack() == 1 || itemInstance.getStack() == 1){
			// If this a non stackable item or there is only 1 stack of it
			stackSold = 1;
		} else {
			// there is more than 1 stack
			System.out.println("Vous possedez " + itemInstance.getStack() + " examplaires, combien voulez-vous en vendre ?");
			System.out.println("Montant invalide: Quitter");
			stackSold = InputFunction.getIntInput();
		}

		// Calculate potential gold gained
		goldGained = itemInstance.getTemplate().getPrice() * stackSold * _sellRate;

		// Check if hero can get gold, he may not if his inventory is full
		if(hero.canAddGold()) {
			// Hero can get gold, remove stack from item and add gold
			hero.getInventory().removeItemStack(itemInstance,stackSold);
			hero.addGold(goldGained);
			System.out.println("Item vendu avec succès ! (+" + (int) goldGained + " gold)");
		} else {
			// Hero cannot get gold, do NOT sell items
			System.out.println("Impossible de vendre l'objet, vous n'avez peut-être plus de place dans votre inventaire !");
		}
	}
}
