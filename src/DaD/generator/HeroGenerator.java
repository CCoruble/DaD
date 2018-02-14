package DaD.generator;

import DaD.Commons.Utils.InputFunction;
import DaD.Debug.DebugLogger;
import DaD.Commons.Collections.MultiValueSet;
import DaD.creature.Hero;
import DaD.data.types.HeroGender;
import DaD.data.types.HeroRace;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.formulas.HeroFormulas;
import DaD.manager.SaveManager;

import java.util.Scanner;

/**
 * Created by Clovis on 07/02/2017.
 * Singleton used to generate a hero
 * for new players.
 */
public class HeroGenerator
{
	/**
	 * Private instance of class.
	 */
	private static final HeroGenerator _instance = new HeroGenerator();

	/**
	 * Private constructor of class.
	 */
	private HeroGenerator(){}

	/**
	 * Accessor for private instance of class.
	 * @return HeroGenerator
	 */
	public static final HeroGenerator getInstance()
	{
		return _instance;
	}

	/**
	 * Set default stats of hero, also call
	 * different function to set race, gender and name.
	 * Return true if hero was successfully created.
	 * @return boolean
	 */
	public boolean createNewHero() {
		// Initializing a MultiValueSet that will contain the values of the new hero
		MultiValueSet heroInformation = new MultiValueSet();

		// Setting name of hero
		String name = setupHeroName();
		if(name.isEmpty())
			return false; // Player want to leave
		heroInformation.set("name", name);

		// Setting gender & race of hero
		HeroGender gender = setupHeroGender();
		HeroRace race = setupHeroRace();
		heroInformation.set("gender", gender);
		heroInformation.set("race", race);

		// Setting all other parameters
		heroInformation.set("level", 1);
		heroInformation.set("experience", new Env(0., StatType.SET, Stats.EXPERIENCE, 1));
		heroInformation.set("experienceMax", HeroFormulas.calcBaseMaxExperience(race,gender));
		Env hpMax = HeroFormulas.calcBaseHpMax(race, gender);
		heroInformation.set("hpMax", hpMax);
		Env hp = new Env(hpMax.getValue(),StatType.SET,Stats.HP);
		heroInformation.set("hp", hp);
		Env mpMax = HeroFormulas.calcBaseMpMax(race,gender);
		heroInformation.set("mpMax",mpMax);
		Env mp = new Env(mpMax.getValue(),StatType.SET,Stats.MP);
		heroInformation.set("mp",mp);
		heroInformation.set("attack", HeroFormulas.calcBaseAttack(race, gender));
		heroInformation.set("defense", HeroFormulas.calcBaseDefense(race, gender));
		heroInformation.set("inventorySize", HeroFormulas.BASE_INVENTORY_SIZE);

		// Set the new hero values
		Hero.setInstance(heroInformation);
		return true;
	}

	/**
	 * Ask player to input a name for hero and return it.
	 * @return String
	 */
	private String setupHeroName(){
		String name;
		while(true){
			System.out.println("Veuillez rentrer un nom de personnage, 'x' pour quitter.");
			name = InputFunction.getStringInput();

			// Player wants to leave
			if(name.equals("x"))
				return "";

			// If name is available we return it
			if (isNameAvailable(name))
				return name;

			// By reaching this point it mean player doesn't want to leave but name is unavailable
			System.out.println("Ce nom est déjà prit, veuillez en choisir un autre.");
		}
	}

	private boolean isNameAvailable(String name){
		for(String string: SaveManager.getInstance().getAllUsedName())
			if(string.equals(name))
				return false;
		// If no save name match the given name, it is available
		return true;
	}

	/**
	 * Ask player to choose a gender among the
	 * list of available genders.
	 * @return HeroGender
	 */
	private HeroGender setupHeroGender(){
		int choice;
		while(true){
			// Display all the possible gender for hero
			System.out.println("Veuillez choisir un genre :");
			for(HeroGender gender : HeroGender.VALUES)
				System.out.println((gender.ordinal()+1) + " : " + gender);

			// Get hero choice
			choice = InputFunction.getIntInput();

			// If hero make a valid choice return the choosen HeroGender
			if(choice > 0 || choice > HeroGender.VALUES.length){
				return HeroGender.VALUES[choice-1];
			}

			// By reaching this point it means hero did a non valid choice
			System.out.println("Ce n'est pas un choix valide!");
		}
	}

	/**
	 * Ask player to choose a race among the
	 * list of available races.
	 * @return HeroRace
	 */
	private HeroRace setupHeroRace(){
		int choice;
		while(true){
			// Display all the possible race for a hero
			System.out.println("Veuillez choisir une race :");
			for(HeroRace race : HeroRace.VALUES)
				System.out.println((race.ordinal()+1) + " : " + race);

			// Get hero choice
			choice = InputFunction.getIntInput();

			// If hero make a valid choice return the choosen race
			if(choice > 0 || choice > HeroRace.VALUES.length){
				return HeroRace.VALUES[choice-1];
			}

			// By reaching this point it means hero did a non valid choice
			System.out.println("Ce n'est pas un choix valide!");
		}
	}
}
