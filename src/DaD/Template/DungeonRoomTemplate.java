package DaD.Template;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.MonsterInfo;
import DaD.data.types.MonsterRarity;
import DaD.dungeon.DungeonRoomInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 26/09/2017.
 * Used to create {@link DungeonRoomInstance}.
 */
public class DungeonRoomTemplate
{
	/**
	 * Order of this room, this room
	 * is part of a dungeonTemplate so
	 * it must have an order / index.
	 */
	private final int _order;
	/**
	 * ArrayList containing all {@link MonsterInfo}.
	 */
	private final ArrayList<MonsterInfo> _monsterList;

	/**
	 * Constructor of class.
	 * @param roomInformation MultiValueSet containing all information abou template.
	 */
	public DungeonRoomTemplate(MultiValueSet roomInformation){
		_monsterList = new ArrayList<>();
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

	/**
	 * Return the list of MonsterInfo
	 * @return ArrayList
	 */
	public ArrayList<MonsterInfo> getMonsterList(){
		return _monsterList;
	}

	/**
	 * Display order then iterate on list on
	 * {@link MonsterInfo} and call {@link MonsterInfo#displayInfo()}
	 * for each.
	 */
	public void displayTemplate(){
		System.out.println("order: " + _order);
		for(MonsterInfo monsterInfo: _monsterList){
			monsterInfo.displayInfo();
		}
	}
}
