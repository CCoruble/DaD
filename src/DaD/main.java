package DaD;

import DaD.calculator.Calculator;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.loader.GeneralLoader;
import DaD.manager.GameManager;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Clovis on 06/02/2017.
 */
public class main
{
	public static void main(String[] args)
	{
		runGame();
		//runTest();
	}

	private static void runGame(){
		// Load everything up
		GeneralLoader.getInstance().loadAll();
		// Launch main menu
		GameManager.getInstance().mainMenu();
	}

	private static void runTest(){

		System.out.println("Running test.");
		/*
		ArrayList<Env> envList = new ArrayList<Env>();
		envList.add(new Env(2, StatType.ADD, Stats.ATTACK,5));
		envList.add(new Env(4, StatType.DIVIDE, Stats.DEFENSE,10));
		envList.add(new Env(5, StatType.ADD, Stats.ATTACK,14));
		envList.add(new Env(4, StatType.DIVIDE, Stats.DODGE,2));
		envList.add(new Env(3, StatType.SUBTRACT, Stats.DEFENSE,1));
		envList.add(new Env(9, StatType.MULTIPLY, Stats.EXPERIENCE,5));
		envList.add(new Env(8, StatType.NONE, Stats.ATTACK,7));

		System.out.println("Avant sort");
		for (Env env: envList)
		{
			System.out.println("Order: " + env.getOrder() + " Bonus: " + env.getValue());
		}

		Calculator.getInstance().sort(envList);

		System.out.println("Apres sort");
		for (Env env: envList)
		{
			System.out.println("Order: " + env.getOrder() + " Bonus: " + env.getValue());
		}
		*/

		// Tests
		//DungeonLoader.getInstance().loadDungeons();

		/*
		Random random = new Random();
		int low = 1;
		int high = 20;
		int total = 0;
		int result;
		int iteration = 100;
		int max = 0;
		int min = high;
		for (int i = 0; i < iteration; i++)
		{
			//result = random.nextInt(high - low + 1) + low;
			result = random.nextInt(high + 1);
			System.out.println(result);
			total += result;
			if (result > max)
				max = result;
			if (result < min)
				min = result;
		}
		System.out.println("Iterations: " + iteration +
				"\n Max: " + max +
				"\n Min: " + min +
				"\n Moyenne :" + ((double)total / (double)iteration));
		*/
		System.out.println("Finished test.");
	}
}