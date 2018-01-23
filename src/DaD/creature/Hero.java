package DaD.creature;

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
 */
public class Hero extends Creature
{
	private static Hero _instance = new Hero();

	private HeroGender _gender;
	private HeroRace _race;
	private Env _experienceMax;
	private Env _experience;
	private HeroInventory _inventory;

	private Hero(){}
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

	public static Hero getInstance()
	{
		return _instance;
	}
	public static void setInstance(MultiValueSet heroInformation) {
		_instance = new Hero(heroInformation);
	}

	private void checkLevelUp(){
		if(getExperience().getValue() >= _experienceMax.getValue())
			levelUp();
	}
	private void levelUp(){
		// Subtract the required experience to lvl up to his actual experience
		setExperienceValue(getExperience().getValue() - _experienceMax.getValue());

		// Update the experience required to lvl up
		_experienceMax.setValue(HeroFormulas.calcLevelUpMaxExperience(_instance));

		// Upgrade all the hero stats
		setHpMaxValue(HeroFormulas.calcLevelUpHpMax(_instance));
		setAttackValue(HeroFormulas.calcLevelUpAttack(_instance));
		setDefenseValue(HeroFormulas.calcLevelUpDefense(_instance));

		// The hero get 10% of this maxHp back
		setHpValue(getHp().getValue()*1.1);

		// Increase level & display it
		setLevel(getLevel()+1);
		Spacer.displayLevelUpSpacer();
		System.out.println("\n Bravo " + getName() + ", tu as gagné un niveau !");
		Spacer.displayLevelUpSpacer();
	}

	public void resurect(HeroDeathReason deathReson){
		switch(deathReson){ // Depending of the source / reason of death the gold & experience loss can be changed
			case KILLED_BY_MONSTER: // The only reason of death for the moment
				setGold((int)(getGold() * 0.9));
				setHpValue((int)(getHpMax().getValue()*0.5));
				setExperienceValue((int)(getExperience().getValue()*0.9));
				break;
		}
	}

	@Override
	public String toString(){
		return getName() + " Lv." + getLevel()
				+ "(" + HeroFormulas.calcPercentExperience(_instance) + "%)"
				+  " HP :[" + (int)getHp().getValue() + "/" + (int)getHpMax().getValue() + "]";
		// If you want to display more information add them here
	}
	public String displayFullCharacteristic(){
		return  "=============================" + "\n" +
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
	}

	//Race and gender
	public HeroGender getHeroGender(){
		return _gender;
	}
	public HeroRace getHeroRace(){
		return _race;
	}
	public void setHeroGender( HeroGender gender){
		_gender = gender;
	}
	public void setHeroRace( HeroRace race){
		_race = race;
	}

	//Experience
	private int getPercentExperience(){
		return (int)((getExperience().getValue() / _experienceMax.getValue()) * 100);
	}
	public void setExperience(Env experience){
		_experience = experience;
	}
	private void setExperienceValue(double experience){
		_experience.setValue(experience);
	}
	public void addExperience(double experience){
		setExperienceValue(_experience.getValue() + experience);
		checkLevelUp();
	}
	public void addExperience(Env experience){
		setExperienceValue(_experience.getValue() + experience.getValue());
		checkLevelUp();
	}
	public Env getExperience(){
		return _experience;
	}

	//ExperienceMax
	public Env getExperienceMax(){
		return _experienceMax;
	}
	public void setExperienceMax(Env experienceMax){
		_experienceMax = experienceMax;
	}
	public void setExperienceMaxValue(double experienceMax){
		_experienceMax.setValue(experienceMax);
	}
	public void addExperienceMax(double experienceMax){
		_experienceMax.setValue(_experienceMax.getValue() + experienceMax);
	}

	// Equipment & inventory
	public boolean tryEquip(ItemInstance item){
		// Here we only test the basic requirement: level & equipable, the inventory space left & other are tested in "equip" function
		// If the item is not equipable we do nothing
		if(!item.getTemplate().isEquipable())
			return false;
		// If you do not have the required level we do nothing
		if(item.getTemplate().getRequiredLevel() > Hero.getInstance().getLevel())
			return false;

		// Nothing stop us to equip the item, unequip old item will be manage in "equip" function
		item.equip(this);
		return true;
	}
	public void tryUnequip(ItemInstance item){
		// This can be later use to prevent special item to be unequip by certain way (cursed items, holy items, ...)
		item.unequip(this);
	}

	// Inventory
	public Inventory getInventory(){
		return _inventory;
	}
	public void setInventory(HeroInventory inventory){
		_inventory = inventory;
	}
}
