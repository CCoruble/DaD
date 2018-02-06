package DaD.manager;

import DaD.city.Bank;
import DaD.creature.Hero;
import DaD.data.types.HeroGender;
import DaD.data.types.HeroRace;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.inventory.HeroInventory;
import DaD.inventory.Inventory;
import DaD.item.ItemInstance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
	 * Name of the file were we save game.
	 */
	private static final String FILE_NAME = "Save.txt"; // path from the folder root

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
			out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

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
		boolean loadSuccess = true;

		ObjectInputStream in = null;
		try{
			//opening the file
			in = new ObjectInputStream(new FileInputStream(FILE_NAME));

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
					Hero.getInstance().getInventory().addItem(
							new ItemInstance(
									// templateId
									in.readInt(),
									// Equipped
									in.readBoolean(),
									// Stack
									in.readInt()
							)
					);
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
			loadSuccess = false;
		}finally {
			try{
				// in.close need a try to be executed, but it must be executed anyway
				in.close();
			}
			catch(Exception ex){
				ex.printStackTrace();
				loadSuccess = false;
			}
		}
		if(loadSuccess)
			System.out.println("Chargement réussie !");
		else
			System.out.println("Chargement échouée !");
		return loadSuccess;
	}
}