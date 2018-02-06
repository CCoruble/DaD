package DaD.generator;

import DaD.commons.MultiValueSet;
import DaD.creature.Hero;
import DaD.data.types.HeroGender;
import DaD.data.types.HeroRace;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.formulas.HeroFormulas;

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
	 */
	public void createNewHero() {
		try
		{
			// Initializing a MultiValueSet that will contain the values of the new hero
			MultiValueSet heroInformation = new MultiValueSet();

			// Setting name of hero
			heroInformation.set("name", setupHeroName());

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
			heroInformation.set("gold", HeroFormulas.BASE_GOLD);
			heroInformation.set("inventorySize", HeroFormulas.BASE_INVENTORY_SIZE);

			// Set the new hero values
			Hero.setInstance(heroInformation);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Erreur lors de la création du personnage !");
			System.exit(1);
		}
	}

	/**
	 * Ask player to input a name for hero and return it.
	 * @return String
	 */
	private String setupHeroName(){
		// Initializing a scanner for the player choice
		Scanner scanner = new Scanner(System.in);

		while(true){
			System.out.println("Veuillez rentrer un nom de personnage :");
			try{
				return scanner.next();
			} catch (Exception e){
				System.out.println("Nom incorrect veuillez recommencer !");
			}
		}
	}

	/**
	 * Ask player to choose a gender among the
	 * list of available genders.
	 * @return HeroGender
	 */
	private HeroGender setupHeroGender(){
		// Initializing a scanner for the player choice
		Scanner scanner = new Scanner(System.in);

		//First get the choice of gender
		int choice = 0;
		while (choice < 1 || choice > HeroGender.VALUES.length) // Condition to stay is to not contain H or F
		{
			// Display all genders available
			System.out.println("Veuillez choisir un genre :");
			for(HeroGender gender : HeroGender.VALUES) // Display all the possible gender for a hero
				System.out.println((gender.ordinal()+1) + " : " + gender);

			String input = scanner.next();
			// Try to parse the player choice
			try{
				choice = Integer.parseInt(input);
			} catch (Exception e){ // If this is a char / string, we force the player to re-choose a gender
				System.out.println("Ce n'est pas un choix valide, veuillez recommencer !");
			}
		}
		//Get the right gender from the choice previously made
		return HeroGender.VALUES[choice-1];
	}

	/**
	 * Ask player to choose a race among the
	 * list of available races.
	 * @return HeroRace
	 */
	private HeroRace setupHeroRace(){
		// Initializing a scanner for the player choice
		Scanner scanner = new Scanner(System.in);

		//First get the choice of race
		int choice = 0;
		while (choice < 1 || choice > HeroRace.VALUES.length){
			// First we display all available races
			System.out.println("Veuillez choisir une race :");
			for(HeroRace race : HeroRace.VALUES) // Display all the possible race for a hero
				System.out.println((race.ordinal()+1) + " : " + race);

			String input = scanner.next();
			// Then try to parse the player choice
			try{
				choice = Integer.parseInt(input);
			} catch (Exception e) { // If this is a char / string, we force the player to re-choose a race
				System.out.println("Ce n'est pas un choix valide, veuillez recommencer !");
			}
		}

		//Get the right race name from the choice previously made
		return HeroRace.VALUES[choice-1];
	}
}
