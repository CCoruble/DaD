package DaD.city;

import DaD.creature.Hero;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Scanner;

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
    private static final String[] _options = {"Stocker des pièces d'or","Retirer des pièces d'or","Faire un prêt","Quitter la banque"};
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
     */
    public void bankMenu() {
        Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
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
            int choice; // Variable that represent the player's choice
            choice = scanner.nextInt(); // Get the player's choice
            switch (choice) {
                case 1:
                    stockMoney();
                    break;
                case 2:
                    retrieveMoney();
                    break;
                case 3:
                    borrowMoney();
                    break;
                default:
                    stayInBank = false;
                    break;
            }
        }
    }

    /**
     * Menu to store golds into bank.
     */
    private void stockMoney(){
        Scanner scanner = new Scanner(System.in);
        String input;
        int gold;

        while(true){
            System.out.println("Vous avez " + Hero.getInstance().getGold() + " pièces d'or.");
            System.out.println("Combien voulez-vous stocker de pièces d'or? Minimum " + _minimumGoldPerStock + " pièces!");
            System.out.println("Inscrivez un montant négatif pour quitter.");
            input = scanner.next();
            try {
                gold = Integer.parseInt(input);
                if(gold <= 0){
                    break;
                } else if(gold < _minimumGoldPerStock){
                    System.out.println("Ce n'est pas suffisant !");
                } else if(gold > Hero.getInstance().getGold()){
                    System.out.println("Vous n'avez pas assez d'argent...");
                } else {
                    System.out.println("Vous avons ajouter les " + gold + " pièces d'or dans nos coffre.");
                    addMoney(gold);
                    Hero.getInstance().decreaseGold(gold);
                    break;
                }
            } catch (Exception e){
                System.out.println("Ce n'est pas un montant valide !");
            }
        }
    }

    /**
     * Menu to retrieve golds from bank.
     */
    private void retrieveMoney(){
        Scanner scanner = new Scanner(System.in);
        String input;
        int gold;

        while(true){
            System.out.println("Il y a " + _stockedGold + " pièces d'or dans nos coffres.");
            System.out.println("Combien voulez-vous en retirer ?");
            System.out.println("Inscrivez un montant négatif pour quitter.");
            input = scanner.next();
            try {
                gold = Integer.parseInt(input);
                if (gold <= 0) {
                    break;
                } else if(gold > _stockedGold) {
                    System.out.println("Vous ne pouvez retirer plus le montant stocké!");
                } else {
                    System.out.println("Vous avons retirer les " + gold + " de notre coffre.");
                    decreaseMoney(gold);
                    Hero.getInstance().addGold(gold);
                    break;
                }
            } catch (Exception e){
                System.out.println("Ce n'est pas un montant valide !");
            }
        }
    }

    /**
     * Main menu to borrow money,
     * not implemented yet.
     */
    private void borrowMoney(){
        throw new NotImplementedException();
    }
}
