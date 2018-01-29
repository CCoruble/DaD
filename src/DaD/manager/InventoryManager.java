package DaD.manager;

import DaD.Debug.DebugLogger;
import DaD.creature.Hero;
import DaD.item.ItemInstance;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Clovis on 16/05/2017.
 * Singleton used to display options for your inventory.
 */
public class InventoryManager
{
    /**
     * Private instance of class.
     */
    private static final InventoryManager _instance = new InventoryManager();

    /**
     * All options available for players.
     */
    private static final String[] _iventoryOptions = {"Consulter mon inventaire","Equipper un objet","Enlever un objet","Ne rien faire"};

    /**
     * Accessor for private instance of class.
     * @return InventoryManager
     */
    public static final InventoryManager getInstance()
    {
        return _instance;
    }

    /**
     * Private constructor of class.
     */
    private InventoryManager(){}

    /**
     * Menu for hero inventory.
     * <p>
     *     This is were you can see the list of items, equip
     *     and unequip items.
     * </p>
     * @param hero The hero you want to access inventory menu.
     */
    public void inventoryMenu(Hero hero){
        Scanner scanner = new Scanner(System.in);
        String input;
        int choice;

        while(true) {
            System.out.println("Que souhaites-tu faire ?");
            for (int i = 0; i < _iventoryOptions.length; i++) {
                System.out.println(i + 1 + ": " + _iventoryOptions[i]);
            }
            try {
                input = scanner.next();
                choice = Integer.parseInt(input);

                switch (choice) { // Action depending en the player's choice
                    case 1:
                        hero.getInventory().displayInventory();
                        break;
                    case 2:
                        equipItem(hero);
                        break;
                    case 3:
                        unequipItem(hero);
                        break;
                    case 4: // quite
                        return;
                    default:
                        throw new Exception();
                }
            } catch (Exception e){
                System.out.println("Ce n'est pas un choix valide!");
            }
        }
    }

    /**
     * Menu to equip and item.
     * @param hero The hero who want to equip item
     */
    private void equipItem(Hero hero){
        // Retrieve all Equipable and non equipped items
        ArrayList<ItemInstance> allEquipableUnequipedItems = hero.getInventory().getAllEquipableUnequippedItems();
        // If the list is empty (size = 0) then there is no possible item that we can equip
        if(allEquipableUnequipedItems.size() == 0){
            System.out.println("Vous n'avez aucun item que vous pouvez equiper !");
            return;
        }

        // First display all equipped items
        System.out.println("Liste des items equipes:");
        for(ItemInstance itemInstance : hero.getInventory().getAllEquippedItems()){
            System.out.println(itemInstance.getTemplate().getName());
        }

        // Then display all equipable and non equipped items
        System.out.println("Liste des items equipables (non equipe):");
        int count = 1;
        for(ItemInstance itemInstance : allEquipableUnequipedItems){
            System.out.println(count + ": " + itemInstance.getTemplate().getName());
            count++;
        }

        System.out.println("Quelle item voulez-vous equiper ?");
        Scanner scanner = new Scanner(System.in);
        String input;
        int choice;
        try{
            input = scanner.next();
            choice = Integer.parseInt(input);
            // If this is not a valid choice
            if (choice > allEquipableUnequipedItems.size() || choice < 0){
                throw new Exception();
            } else { // this is a valid choice
                // When displaying the choice, it start at 1 but in the array index start at 0
                if(hero.tryEquip(allEquipableUnequipedItems.get(choice - 1))){
                    // Try Equip can return false if the hero doesn't fulfill the requirements (level, ...)
                    System.out.println("Item equipe !");
                }else {
                    System.out.println("Impossible d'equiper l'item !");
                }
            }
        } catch (Exception e){
            DebugLogger.log(e);
            System.out.println("Ce n'est pas un choix valide!");
        }
    }

    /**
     * Menu to unequip and item.
     * @param hero The hero who want to unequip item
     */
    private void unequipItem(Hero hero){
        // Retrieves all equipped items
        ArrayList<ItemInstance> allEquipedItems = hero.getInventory().getAllEquippedItems();
        // If the list is empty (size = 0) then the hero has no equipped items
        if(allEquipedItems.size() == 0){
            System.out.println("Vous n'avez aucun item equipe !");
            return;
        }

        // Display all equiped items
        System.out.println("Liste des items equipes:");
        int count = 1;
        for(ItemInstance itemInstance : allEquipedItems){
            System.out.println(count + ": " + itemInstance.getTemplate().getName());
            count++;
        }

        System.out.println("Quelle item voulez-vous enlever ?");
        Scanner scanner = new Scanner(System.in);
        String input;
        int choice;
        try{
            input = scanner.next();
            choice = Integer.parseInt(input);
            // If this is not a valid choice
            if (choice > allEquipedItems.size() || choice < 0){
                throw new Exception();
            } else { // this is a valid choice
                hero.tryUnequip(allEquipedItems.get(choice - 1));
            }
        } catch (Exception e){
            System.out.println("Ce n'est pas un choix valide!");
        }
    }
}
