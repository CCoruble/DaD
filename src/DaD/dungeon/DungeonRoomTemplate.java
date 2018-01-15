package DaD.dungeon;

import DaD.commons.MultiValueSet;
import DaD.data.types.MonsterInfo;
import DaD.data.types.MonsterRarity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Clovis on 26/09/2017.
 */
public class DungeonRoomTemplate
{
	private final int _order;
	private final Set<MonsterInfo> _monsterList;

	public DungeonRoomTemplate(MultiValueSet roomInformation){
		_monsterList = new HashSet<>();
		_order = roomInformation.getInteger("id");
		ArrayList<MultiValueSet> allMonsters = roomInformation.getArrayList("monsters");
		for(MultiValueSet monster: allMonsters){
			int id = monster.getInteger("id");
			int level = monster.getInteger("level");
			int rarity = monster.getInteger("rarity");
			// how many of this monster are present in this room, Example: 2 skeletons lvl 2
			int monsterQuantity = monster.getInteger("quantity");
			for(int j = 0; j < monsterQuantity; j++){
				_monsterList.add(new MonsterInfo(id, level, MonsterRarity.VALUES[rarity]));
			}
		}
	}

	public Set<MonsterInfo> getMonsterList(){
		return _monsterList;
	}

	public void displayTemplate(){
		System.out.println("order: " + _order);
		for(MonsterInfo monsterInfo: _monsterList){
			monsterInfo.displayInfo();
		}
	}
}
