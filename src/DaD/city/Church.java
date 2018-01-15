package DaD.city;

import DaD.manager.SaveManager;

import java.util.Scanner;

/**
 * Created by Clovis on 15/05/2017.
 */
public class Church
{
	private static final Church _instance = new Church();
	private Church(){}
	public static Church getInstance(){
		return _instance;
	}

	public void churchMenu()
	{
		Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
		System.out.println("Que veux-tu faire ?");

		//Display all options to the player
		System.out.println("1: Sauvegarder");
		System.out.println("Autre: Quitter l'eglise");
		try {
			int choice; // Variable that represent the player's choice
			choice = scanner.nextInt(); // Get the player's choice
			switch (choice) {
				case 1:
					SaveManager.getInstance().save();
					break;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
