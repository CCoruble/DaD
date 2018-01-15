package DaD.handler;

import DaD.commons.Spacer;
import DaD.creature.Hero;
import DaD.data.types.DungeonRoomExitState;
import DaD.monster.MonsterInstance;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Clovis on 09/02/2017.
 */
public class FightHandler
{
	private Hero _hero;
	private int _turn;
	private ArrayList<MonsterInstance> _monsterList;

	private static final FightHandler _instance = new FightHandler();

	private FightHandler(){
		// Let this empty
	}

	public static final FightHandler getInstance()
	{
		return _instance;
	}

	public DungeonRoomExitState startFight(Hero hero, ArrayList<MonsterInstance> monsterList)
	{
		_hero = hero;
		_monsterList = monsterList;
		_turn = 0;
		while (!_monsterList.isEmpty() && !_hero.isDead()) // Condition to stay is "there is still monster left" and "the hero is alive"
		{
			Spacer.displayFightSpacer();
			// Increase & display turn
			_turn++;
			System.out.println("Le tour " + _turn + " commence !");

			// Hero turn
			heroTurn();

			// Monsters turn
			monsterTurn();
			Spacer.displayFightSpacer();
		}
		return endFight();
	}

	private DungeonRoomExitState endFight()
	{ //  Either the hero is dead OR all the monsters are dead
		if (_hero.isDead())
		{ // The hero is dead
			return DungeonRoomExitState.HERO_DIED;
		} else if(_monsterList.isEmpty())
		{// All the monsters are dead
			return DungeonRoomExitState.HERO_SUCCEEDED;
		} else
		{ // Hero escaped
			return DungeonRoomExitState.HERO_ESCAPED;
		}
	}

	private void heroTurn(){
		Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
		int countTarget; // This will be used to display the number of the monster
		int playerChoice = 0; // This will contain the player's choice
		int damageDealt; // This will contain the damage deal to the monster
		while(playerChoice < 1 || playerChoice > _monsterList.size())
		{
			countTarget = 1; // In order to properly display the monster list
			System.out.println(_hero.toString());
			System.out.println("Veuillez choisir une cible :");
			for (MonsterInstance target : _monsterList)
			{
				displayMonsterInformation(target,countTarget);
				countTarget++;
			}
		playerChoice = scanner.nextInt();
		}
		playerChoice --; // If the player choose the monster N*2 this is equals to _monsterList.get(1) because the ArrayList start at index 0
		damageDealt = _hero.attack(_monsterList.get(playerChoice)); // We make the hero attack the target
		System.out.println(_hero.getName() + " inflige " + damageDealt + " à " + _monsterList.get(playerChoice));
		if(_monsterList.get(playerChoice).isDead()){ // Check if the monster we just attacked is dead (hp < 1)
			_monsterList.get(playerChoice).die(_hero); // Make the monster die and give the experience to the hero
			_monsterList.remove(playerChoice); // Remove the monster that just died of the list
		}
	}

	private void monsterTurn(){
		int countAttacker = 1;
		for(MonsterInstance attacker: _monsterList){
			int damageDealt = attacker.attack(_hero); // Get the damageDealt by the attacker
			System.out.println(countAttacker + " : " + attacker.getName() + " Lv." + attacker.getLevel()
					+ " vous inflige " + damageDealt + " points de dégats !");
			if(_hero.isDead()){
				return;
			}
			countAttacker++;
		}
	}

	private void displayMonsterInformation(MonsterInstance monster, int order){
		System.out.println(order + " : " + monster.getResumedInformation());
	}
}