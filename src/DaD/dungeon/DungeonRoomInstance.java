package DaD.dungeon;

import DaD.monster.MonsterInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 08/02/2017.
 */
public class DungeonRoomInstance
{
	private final int _order;
	private final ArrayList<MonsterInstance> _monsterList;

	public DungeonRoomInstance(ArrayList<MonsterInstance> monsterList, int order){
		_order = order;
		_monsterList = monsterList;
	}

	public int getOrder(){
		return _order;
	}

	public ArrayList<MonsterInstance> getMonsterList() {
		return _monsterList;
	}
}