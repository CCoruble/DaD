package DaD.creature;

import DaD.calculator.Calculator;
import DaD.commons.MultiValueSet;
import DaD.commons.Spacer;
import DaD.data.types.HeroDeathReason;
import DaD.data.types.HeroGender;
import DaD.data.types.HeroRace;
import DaD.data.types.Stats.Env;
import DaD.formulas.HeroFormulas;
import DaD.inventory.HeroInventory;
import DaD.inventory.Inventory;
import DaD.item.ItemInstance;

/**
 * Created by Clovis on 07/02/2017.
 * Singleton for the hero. For the moment
 * there is only one hero at a time
 * during a game.
 * @see Creature
 * @see DaD.generator.HeroGenerator
 * @see HeroDeathReason
 * @see HeroGender
 * @see HeroRace
 * @see HeroInventory
 */
public class Hero extends Creature
{
	/**
	 * Private instance of class.
	 */
	private static Hero _instance = new Hero();
	/**
	 * Gender of the hero
	 */
	private HeroGender _gender;
	/**
	 * Race of the hero.
	 */
	private HeroRace _race;
	/**
	 * Max experience for the hero,
	 * upon reaching this amount of
	 * experience, hero will level up.
	 */
	private Env _experienceMax;
	/**
	 * Actual amount of experience
	 * the hero has.
	 */
	private Env _experience;
	/**
	 * Inventory where every items
	 * are stored.
	 */
	private HeroInventory _inventory;

	/**
	 * Private constructor.
	 */
	private Hero(){}

	/**
	 * Private constructor.
	 * <p>
	 *     This constructor is used when loading a hero
	 *     from a previous save or when creating a new one.
	 *     We set the different attributes values
	 *     depending on what is inside the MultiValueSet.
	 * </p>
	 * @param heroInformation MultiValueSet containing all information about the hero
	 */
	private Hero(MultiValueSet heroInformation) {
		super(heroInformation);
		_gender = (HeroGender) heroInformation.get("gender");
		_race = (HeroRace) heroInformation.get("race");
		_experienceMax = heroInformation.getEnv("experienceMax");
		_experience = heroInformation.getEnv("experience");

		// Inventory
		// When loading an existing hero he will already possess several items in his inventory
		if (heroInformation.getArrayList("allItems") != null) {
			_inventory = new HeroInventory(heroInformation.getArrayList("allItems"),heroInformation.getInteger("inventorySize"));
		} else if(heroInformation.getInteger("inventorySize",-1) != -1){
			// When loading an existing Hero without items in his inventory but with extra inventory size
			_inventory = new HeroInventory(heroInformation.getInteger("inventorySize"));
		} else { // When creating a new Hero
			_inventory = new HeroInventory(HeroFormulas.BASE_INVENTORY_SIZE);
		}
	}

	/**
	 * Accessor for private instance of class.
	 * @return Hero
	 */
	public static Hero getInstance()
	{
		return _instance;
	}

	/**
	 * Set the instance of by calling the
	 * {@link #Hero(MultiValueSet) constructor}.
	 * @param heroInformation MultiValueSet containing all information
	 */
	public static void setInstance(MultiValueSet heroInformation) {
		_instance = new Hero(heroInformation);
	}

	/**
	 * If value of {@link #_experience} of hero
	 * is higher than value of {@link #_experienceMax}
	 * call {@link #levelUp()} function.
	 */
	private void checkLevelUp(){
		if(_experience.getValue() >= _experienceMax.getValue())
			levelUp();
	}

	/**
	 * Increase stats of the hero
	 * depending on his {@link #_race}
	 * and {@link #_gender}.
	 */
	private void levelUp(){
		// Decrease experience value by experienceMax value
		_experience.setValue(_experience.getValue() - _experienceMax.getValue());

		// Increase level
		setLevel(getLevel()+1);

		// Call the calculator to re-calculate all stat of the hero after he leveled up
		Calculator.getInstance().calculateAllStats(this);

		Spacer.displayLevelUpSpacer();
		System.out.println("Bravo " + getName() + ", tu as gagné un niveau !");
		Spacer.displayLevelUpSpacer();
	}

	/**
	 * Make the hero come back to life and
	 * apply a death penalty
	 * to the hero depending on the
	 * {@link HeroDeathReason}.
	 * @param deathReason Enum representing the reason why hero died.
	 */
	public void resurect(HeroDeathReason deathReason){
		switch(deathReason){ // Depending of the source / reason of death the gold & experience loss can be changed
			case KILLED_BY_MONSTER: // The only reason of death for the moment
				setGold((int)(getGold() * 0.9));
				setHpValue((int)(getHpMax().getValue()*0.5));
				setExperienceValue((int)(getExperience().getValue()*0.9));
				break;
		}
	}

	/**
	 * Create and return a short string
	 * which resume the hero information.
	 * @return String
	 */
	@Override
	public String toString(){
		return getName() + " Lv." + getLevel()
				+ "(" + HeroFormulas.calcPercentExperience(_instance) + "%)"
				+  " HP :[" + (int)getHp().getValue() + "/" + (int)getHpMax().getValue() + "]";
		// If you want to display more information add them here
	}

	/**
	 * Return string representing all
	 * characteristics of the hero.
	 * @return String
	 */
	public void displayFullCharacteristic(){
		String charac =
				"=============================" + "\n" +
				getName() + " Lv." + getLevel() + "\n" +
				"Sexe : " + getHeroGender() + "\n" +
				"Race : " + getHeroRace() + "\n" +
				"Hp : [" + (int)getHp().getValue() + "/" + (int)getHpMax().getValue() + "] (" + (int)getPercentHp() + "%)\n" +
				"Attaque : " + (int)getAttack().getValue() + "\n" +
				"Defense : " + (int)getDefense().getValue() + "\n" +
				"Gold : " + getGold() + "\n" +
				"Experience : [" + (int)getExperience().getValue() + "/" + (int)getExperienceMax().getValue() + "] (" + getPercentExperience() + "%) \n" +
				"Inventory space : [" + (_inventory.getInventorySize() - _inventory.getInventorySizeLeft()) + "/" + _inventory.getInventorySize() + "]\n" +
				"=============================";
		System.out.println(charac);
	}

	//Race and gender
	/**
	 * Return gender of the hero.
	 * @return HeroGender
	 */
	public HeroGender getHeroGender(){
		return _gender;
	}
	/**
	 * Return race of the hero.
	 * @return HeroRace
	 */
	public HeroRace getHeroRace(){
		return _race;
	}

	/**
	 * Set the gender of the hero to
	 * the given HeroGender.
	 * @param gender New gender for the hero.
	 */
	public void setHeroGender( HeroGender gender){
		_gender = gender;
	}
	/**
	 * Set the race of the hero to
	 * the given HeroRace.
	 * @param race New race for the hero.
	 */
	public void setHeroRace( HeroRace race){
		_race = race;
	}

	//Experience
	/**
	 * Return an int representing percent
	 * of experience to max experience.
	 * @return int
	 */
	private int getPercentExperience(){
		return (int)((getExperience().getValue() / _experienceMax.getValue()) * 100);
	}

	/**
	 * Set the attribute experience.
	 * @param experience New value for experience
	 */
	public void setExperience(Env experience){
		_experience = experience;
	}

	/**
	 * Set the value of experience.
	 * @param experience New experience value.
	 */
	private void setExperienceValue(double experience){
		_experience.setValue(experience);
	}

	/**
	 * Add experience to the hero.
	 * @param experience Amount to add.
	 */
	public void addExperience(double experience){
		setExperienceValue(_experience.getValue() + experience);
		checkLevelUp();
	}

	/**
	 * Return {@link #_experience}
	 * @return Env
	 */
	public Env getExperience(){
		return _experience;
	}

	//ExperienceMax
	/**
	 * Return {@link #_experienceMax}
	 * @return Env
	 */
	public Env getExperienceMax(){
		return _experienceMax;
	}

	/**
	 * Set the attribute {@link #_experienceMax}.
	 * @param experienceMax New value of attribute.
	 */
	public void setExperienceMax(Env experienceMax){
		_experienceMax = experienceMax;
	}

	// Equipment & inventory
	/**
	 * Return true if the item can be equipped.
	 * <p>
	 *     This only take care of primary
	 *     requirement like is item equipable
	 *     and does hero has the required level.
	 *     If there is an item already equipped here
	 *     it can still return true.
	 * </p>
	 * @param item Item you wish to know if you can equip it.
	 * @return boolean
	 */
	public boolean tryEquip(ItemInstance item){
		// Here we only test the basic requirement: level & equipable, the inventory space left & other are tested in "equip" function
		// If the item is not equipable we do nothing
		if(!item.getTemplate().isEquipable())
			return false;
		// If you do not have the required level we do nothing
		if(item.getTemplate().getRequiredLevel() > Hero.getInstance().getLevel())
			return false;
		// Nothing stop us from equipping this items
		_inventory.equip(this,item);
		return true;
	}

	/**
	 * Return true if item can be unequipped.
	 * <p>
	 *     This is not used for now, but
	 *     there might be future item that
	 *     can only be unequipped in some
	 *     specific way.
	 *     example: cursed items
	 * </p>
	 * @param item Item wish you want to know if you can unequip it.
	 * @return boolean
	 */
	public boolean tryUnequip(ItemInstance item){
		// Maybe used later to prevent hero to unequip some special items (cursed / blessed / ...)
		_inventory.unequip(this, item);
		return true;
	}

	// Inventory
	/**
	 * Return {@link #_inventory}
	 * @return HeroInventory
	 */
	public HeroInventory getInventory(){
		return _inventory;
	}

	/**
	 * Set the attribute {@link #_inventory}.
	 * @param inventory New value of attribute.
	 */
	public void setInventory(HeroInventory inventory){
		_inventory = inventory;
	}
}
