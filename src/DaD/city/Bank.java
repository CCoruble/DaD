package DaD.city;

import DaD.Commons.Utils.InputFunction;
import DaD.Commons.Utils.ItemFunction;
import DaD.creature.Hero;
import DaD.item.ItemInstance;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Singleton used to handle money
 * exchange between player and a safe.
 */
public class Bank {
    /**
     * Private instance of class.
     */
    private static final Bank _instance = new Bank();
    /**
     * All available choices.
     */
    private static final String[] _options = {"Stocker des pièces d'or","Retirer des pièces d'or","Faire un prêt"};
    /**
     * Minimum of gold to put in bank, if
     * amount if below it will refuse
     * stocking money.
     */
    private final int _minimumGoldPerStock = 10;
    /**
     * Golds in bank.
     */
    private int _stockedGold;

    /**
     * Private constructor.
     */
    private Bank(){}

    /**
     * Accessor for private instance.
     * @return Bank
     */
    public static Bank getInstance(){
        return _instance;
    }

    /**
     * Increase {@link #_stockedGold} value.
     * @param gold Added value
     */
    private void addMoney(int gold){
        _stockedGold += gold;
    }

    /**
     * Decrease {@link #_stockedGold} value.
     * @param gold Amount to decrease
     */
    private void decreaseMoney(int gold){
        _stockedGold -= gold;
    }

    /**
     * Return {@link #_stockedGold}.
     * @return int
     */
    public int getMoney(){
        return _stockedGold;
    }

    /**
     * Set the value of {@link #_stockedGold}.
     * @param gold New value to set.
     */
    public void setMoney(int gold){
        _stockedGold = gold;
    }

    /**
     * Display the main menu where hero
     * will be prompted choices.
     * @param hero Hero accessing menu
     */
    public void bankMenu(Hero hero) {
        Boolean stayInBank = true;

        System.out.println("Bienvenue dans notre banque !");
        if(_stockedGold == 0){
            System.out.println("Vous n'avez pas d'argent dans notre banque pour le moment.");
        } else {
            System.out.println("Vous avez déjà " + _stockedGold + " pièces d'or dans nos coffres.");
        }

        while(stayInBank) {
            //Display all options to the player
            for (int i = 0; i < _options.length; i++) {
                System.out.println((i + 1) + " : " + _options[i]);
            }
            System.out.println("Autre: Quitter");
            switch (InputFunction.getIntInput()) {
                case 1:
                    stockMoney(hero);
                    break;
                case 2:
                    retrieveMoney(hero);
                    break;
                case 3:
                    borrowMoney(hero);
                    break;
                default:
                    stayInBank = false;
                    break;
            }
        }
    }

    /**
     * Menu to store golds into bank.
     * @param hero Hero accessing bank
     */
    private void stockMoney(Hero hero){
        // Retrieve gold item from hero inventory
        ItemInstance gold = hero.getInventory().getItemById(ItemFunction.goldId);

        // If hero does not have any gold
        if(gold == null){
            System.out.println("Vous n'avez pas d'argent !");
            return;
        }

        System.out.println("Vous avez " + gold.getStack() + " pièces d'or.");
        System.out.println("Combien voulez-vous stocker de pièces d'or? Minimum " + _minimumGoldPerStock + " pièces!");
        System.out.println("Autre ou montant négatif: Quitter.");
        int amount = InputFunction.getIntInput();

        if(amount <= 0 || amount == Integer.MAX_VALUE){
            // Player wants to leave
        } else if(amount < _minimumGoldPerStock){
            // Player input is too low
            System.out.println("Ce n'est pas suffisant !");
        } else if(amount > gold.getStack()){
            // Player doesn't have the money he wants to stock
            System.out.println("Vous n'avez pas assez d'argent...");
        } else {
            // Player has enough money, remove stock of gold item
            addMoney(amount);
            hero.getInventory().removeItemStack(gold,amount);
            System.out.println("Vous avons ajouté les " + amount + " pièces d'or dans nos coffre.");
        }
    }

    /**
     * Menu to retrieve golds from bank.
     * @param hero Hero accessing bank
     */
    private void retrieveMoney(Hero hero){
        System.out.println("Il y a " + _stockedGold + " pièces d'or dans nos coffres.");
        System.out.println("Combien voulez-vous en retirer ?");
        System.out.println("Autre ou montant négatif: Quitter.");
        int amount = InputFunction.getIntInput();

        if (amount <= 0 || amount == Integer.MAX_VALUE) {
            // Player wants to leave
        } else if(amount > _stockedGold) {
            System.out.println("Vous ne pouvez retirer plus que le montant stocké!");
        } else if(hero.canAddGold()){
            decreaseMoney(amount);
            hero.addGold(amount);
            System.out.println("Vous avons retirer les " + amount + " de notre coffre.");
        } else {
            System.out.println("Impossible d'ajouter de l'argent à votre inventaire !");
        }
    }

    /**
     * Main menu to borrow money,
     * not implemented yet.
     * @param hero Hero accessing bank
     */
    private void borrowMoney(Hero hero){
        throw new NotImplementedException();
    }
}
