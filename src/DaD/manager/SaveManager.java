package DaD.manager;

import DaD.Debug.DebugLogger;
import DaD.city.Bank;
import DaD.creature.Hero;
import DaD.data.types.HeroGender;
import DaD.data.types.HeroRace;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.inventory.HeroInventory;
import DaD.item.ItemInstance;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Clovis on 12/05/2017.
 * Singleton using to save and load games.
 */
public class SaveManager
{
	/**
	 * Private instance of class.
	 */
	private static final SaveManager _instance = new SaveManager();
    /**
     * Folder for all saves.
     */
    private static final String FOLDER_NAME = "Saves/";
    /**
     * Extension for save files.
     */
    private static final String SAVE_EXTENSION = ".txt";
	/**
	 * Actual version of game, this is used
	 * to verify is version of saved game is correct.
	 */
	private static final String VERSION = "DADVer3";

	/**
	 * Accessor for private instance of class.
	 * @return SaveManager
	 */
	public static final SaveManager getInstance()
	{
		return _instance;
	}

	/**
	 * Private constructor of class.
	 */
	private SaveManager() {}

	/**
	 * Function to save all game and hero information
	 * into a save file.
	 */
	public void save(){
		boolean saveSuccess = true;

		ObjectOutputStream out = null;
		try{
			//opening the file
			out = new ObjectOutputStream(new FileOutputStream(FOLDER_NAME + Hero.getInstance().getName() + SAVE_EXTENSION));

			//Saving
			out.writeUTF(VERSION); // Saving the version, checking save compatibility
			//Creature
			out.writeObject(Hero.getInstance().getName());
			out.writeInt(Hero.getInstance().getLevel());
			out.writeDouble(Hero.getInstance().getAttack().getValue());
			out.writeDouble(Hero.getInstance().getDefense().getValue());
			out.writeDouble(Hero.getInstance().getHpMax().getValue());
			out.writeDouble(Hero.getInstance().getHp().getValue());
			out.writeDouble(Hero.getInstance().getMpMax().getValue());
			out.writeDouble(Hero.getInstance().getMp().getValue());
			//Hero
			out.writeInt(Hero.getInstance().getHeroGender().ordinal());
			out.writeInt(Hero.getInstance().getHeroRace().ordinal());
			out.writeDouble(Hero.getInstance().getExperienceMax().getValue());
			out.writeDouble(Hero.getInstance().getExperience().getValue());
			out.writeDouble(Hero.getInstance().getGold());

			// Bank
			out.writeInt(Bank.getInstance().getMoney());

			// Inventory
			out.writeInt(Hero.getInstance().getInventory().getInventorySize());

			// Items
			// write number of items to then ensure we retrieve them all
			out.writeInt(Hero.getInstance().getInventory().getNumberOfItems());
			// Then write first the templateId then the stack of items
			for (ItemInstance itemInstance : Hero.getInstance().getInventory().getItemList()) {
				// templateId
				out.writeInt(itemInstance.getTemplate().getId());
				// equipped
				out.writeBoolean(itemInstance.isEquipped());
				// stacks
				out.writeInt(itemInstance.getStack());
			}

		} catch (Exception ex){
			saveSuccess = false;
		}finally {
			try{
				// out.close need a try to be executed, but it must be executed anyway
				out.close();
			}
			catch(Exception ex){
				saveSuccess = false;
			}
		}
		if(saveSuccess)
			System.out.println("Sauvegarde réussie !");
		else
			System.out.println("Sauvegarde échouée !");
	}

	/**
	 * Function to load a game from a save file.
	 * @return boolean Status of load process, true if successfully loaded game.
	 */
	public boolean load(){
	    String heroName = askSelectedSave();
	    if(heroName.equals("")) // If empty
	        return false; // Get out of here

		boolean loadSuccess = true;
		ObjectInputStream in = null;

		try{
			//opening the file
			in = new ObjectInputStream(new FileInputStream(FOLDER_NAME + heroName + SAVE_EXTENSION));

			//Saving
			if(!in.readUTF().equals(VERSION)) { // Checking save version
				System.out.println("La sauvegarde n'est pas compatible !");
				loadSuccess = false;
			} else { // Version is compatible
				//Creature
				Hero.getInstance().setName((String)in.readObject());
				Hero.getInstance().setLevel(in.readInt());

				double attackValue = in.readDouble();
				Hero.getInstance().setAttack(new Env(attackValue, StatType.SET, Stats.ATTACK));

				double defenseValue = in.readDouble();
				Hero.getInstance().setDefense(new Env(defenseValue, StatType.SET, Stats.DEFENSE));

				double hpMaxValue = in.readDouble();
				Hero.getInstance().setHpMax(new Env(hpMaxValue, StatType.SET, Stats.HP_MAX));

				double hpValue = in.readDouble();
				Hero.getInstance().setHp(new Env(hpValue, StatType.SET, Stats.HP));

				double mpMaxValue = in.readDouble();
				Hero.getInstance().setMpMax(new Env(mpMaxValue,StatType.SET,Stats.MP));

				double mpValue = in.readDouble();
				Hero.getInstance().setMp(new Env(mpValue,StatType.SET,Stats.MP));

				//Hero
				Hero.getInstance().setHeroGender(HeroGender.VALUES[in.readInt()]);
				Hero.getInstance().setHeroRace(HeroRace.VALUES[in.readInt()]);

				double experienceMaxValue = in.readDouble();
				Hero.getInstance().setExperienceMax(new Env(experienceMaxValue, StatType.SET, Stats.EXPERIENCE_MAX));

				double experienceValue = in.readDouble();
				Hero.getInstance().setExperience(new Env(experienceValue, StatType.SET, Stats.EXPERIENCE));

				Hero.getInstance().setGold(in.readDouble());

				// Bank
				Bank.getInstance().setMoney(in.readInt());

				// Inventory
				Hero.getInstance().setInventory(new HeroInventory(in.readInt()));

				// Items
				int numberOfItems = in.readInt();
				for (int i = 0; i < numberOfItems; i++) {
					int templateId = in.readInt();
					boolean isItemEquipped = in.readBoolean();
					int stack = in.readInt();
					ItemInstance itemInstance = new ItemInstance(templateId,stack);
					Hero.getInstance().getInventory().addItem(itemInstance);
					if(isItemEquipped){
						Hero.getInstance().getInventory().equip(Hero.getInstance(),itemInstance);
					}
				}
			}
		} catch (Exception ex){
			DebugLogger.log(ex);
			loadSuccess = false;
		}finally {
			try{
				// in.close need a try to be executed, but it must be executed anyway
				in.close();
			}
			catch(Exception ex){
			    DebugLogger.log(ex);
				loadSuccess = false;
			}
		}
		if(loadSuccess)
			System.out.println("Chargement réussie !");
		else
			System.out.println("Chargement échouée !");
		return loadSuccess;
	}

    /**
     * Display all available saves and
     * ask the player to choose one.
     * @return String
     */
	public String askSelectedSave(){
	    ArrayList<String> allSaveName = getAllUsedName();
	    if(allSaveName.size() == 0){
	        System.out.println("Aucune sauvegarde disponible !");
	        return "";
        }
        System.out.println("Quelle partie voulez-vous charger?");
	    while(true) {
            for (int i = 0; i < allSaveName.size(); i++) {
                System.out.println((i + 1) + ": " + allSaveName.get(i));
            }
            System.out.println("Autre: quitter");
            Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
            String input;
            int choice;
            try {
                input = scanner.next();
                choice = Integer.parseInt(input);
                if(choice < 1 || choice > allSaveName.size()) // He choose to quite
                    return "";
                return allSaveName.get(choice - 1); // Else he made a valid choice
            } catch (Exception e){
                DebugLogger.log(e);
                System.out.println("Ce n'est pas un choix valide !");
            }
        }
    }

    /**
     * Return an ArrayList containing all name of
     * saves in save folder without save file extension.
     * @return ArrayList
     */
	public ArrayList<String> getAllUsedName(){
	    ArrayList<String> arrayList = new ArrayList<>();
        File folder = new File(FOLDER_NAME);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains(SAVE_EXTENSION)) {
                arrayList.add(file.getName().replace(SAVE_EXTENSION,""));
            }
        }
        return arrayList;
    }
}