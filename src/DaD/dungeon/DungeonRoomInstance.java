package DaD.dungeon;

import DaD.monster.MonsterInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 08/02/2017.
 * Instance base on a {@link DungeonRoomTemplate}, this is where
 * player will enter and face monsters.
 */
public class DungeonRoomInstance
{
	/**
	 * Order of the room.
	 */
	private final int _order;
	/**
	 * ArrayList containing all {@link MonsterInstance}
	 * present in the room.
	 */
	private final ArrayList<MonsterInstance> _monsterList;

	/**
	 * Constructor of class.
	 * @param monsterList ArrayList containing all MonsterInstance
	 * @param order order of the room
	 */
	public DungeonRoomInstance(ArrayList<MonsterInstance> monsterList, int order){
		_order = order;
		_monsterList = monsterList;
	}

	/**
	 * Return room order.
	 * @return int
	 */
	public int getOrder(){
		return _order;
	}

	/**
	 * Return ArrayList containing all MonsterInstance
	 * @return ArrayList
	 */
	public ArrayList<MonsterInstance> getMonsterList() {
		return _monsterList;
	}
}