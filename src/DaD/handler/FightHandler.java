package DaD.handler;

import DaD.commons.Spacer;
import DaD.creature.Hero;
import DaD.data.types.DungeonRoomExitState;
import DaD.monster.MonsterInstance;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Clovis on 09/02/2017.
 * Class used to handle fights.
 * When a fight begin we enter
 * this class to handle all events.
 * @see DungeonRoomExitState
 */
public class FightHandler
{
	/**
	 * Hero that is part of the fight.
	 */
	private Hero _hero;
	/**
	 * Number of turn, start at 0 and is
	 * incremented before each turn.
	 */
	private int _turn;
	/**
	 * ArrayList containing all monsters
	 * that are part of the fight.
	 */
	private ArrayList<MonsterInstance> _monsterList;

	/**
	 * Private instance of class.
	 */
	private static final FightHandler _instance = new FightHandler();

	/**
	 * Private constructor.
	 */
	private FightHandler(){}

	/**
	 * Accessor for instance of class.
	 * @return FightHandler
	 */
	public static final FightHandler getInstance()
	{
		return _instance;
	}

	/**
	 * Initialize fight by setting attributes
	 * values and starting the {@link #fightLoop()} function.
	 * @param hero The hero part of the fight
	 * @param monsterList List of {@link MonsterInstance} part of the fight
	 * @return DungeonRoomExitState
	 */
	public DungeonRoomExitState startFight(Hero hero, ArrayList<MonsterInstance> monsterList) {
		_hero = hero;
		_monsterList = monsterList;
		_turn = 0;
		return fightLoop();
	}

	/**
	 * Function that call {@link #heroTurn()} and {@link #monsterTurn()}
	 * until either hero or all monsters are dead.
	 * @return DungeonRoomExitState
	 */
	public DungeonRoomExitState fightLoop() {
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

	/**
	 * When either hero or all monsters are dead return
	 * the appropriate state.
	 * <p>
	 *     If hero died return {@link DungeonRoomExitState#HERO_DIED},
	 *     if all monsters died return {@link DungeonRoomExitState#HERO_SUCCEEDED}
	 *     else return {@link DungeonRoomExitState#HERO_ESCAPED}.
	 *     Only these 3 states exist for the moment.
	 * </p>
	 * @return DungeonRoomExitState
	 */
	private DungeonRoomExitState endFight() { //  Either the hero is dead OR all the monsters are dead
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

	/**
	 * Handle hero turn, this is where hero
	 * will choose what he wants to do.
	 * Hero turn is always happening before {@link #monsterTurn()}.
	 */
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

	/**
	 * Handle monster turn, this is where each
	 * monster will attack the hero.
	 */
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

	/**
	 * Display order and monster name, here to help
	 * hero to choose which monster he wants to attack.
	 * @param monster MonsterInstance you want the name to be displayed
	 * @param order Order of the MonsterInstance
	 * @see MonsterInstance
	 */
	private void displayMonsterInformation(MonsterInstance monster, int order){
		System.out.println(order + " : " + monster.getResumedInformation());
	}
}