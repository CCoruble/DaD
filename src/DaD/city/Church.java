package DaD.city;

import DaD.Commons.Utils.InputFunction;
import DaD.creature.Hero;
import DaD.manager.SaveManager;

/**
 * Created by Clovis on 15/05/2017.
 * Singleton used to handle game saving.
 */
public class Church
{
	/**
	 * Private instance of class.
	 */
	private static final Church _instance = new Church();

	private static final String[] _options = {"Sauvegarder"};

	/**
	 * Constructor of class.
	 */
	private Church(){}

	/**
	 * Accessor for private instance of class
	 * @return Church
	 */
	public static Church getInstance(){
		return _instance;
	}

	/**
	 * Main menu of this class,
	 * this is where choices will be
	 * prompted to hero.
	 */
	public void churchMenu(Hero hero) {
		System.out.println("Que veux-tu faire ?");

		//Display all options to the player
		for (int i = 0; i < _options.length; i++) {
			System.out.println((i + 1) + " : " + _options[i]);
		}
		System.out.println("Autre: Quitter l'eglise");

		switch (InputFunction.getIntInput()) {
			case 1:
				SaveManager.getInstance().save();
				break;
		}
	}
}
