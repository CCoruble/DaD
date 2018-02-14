package DaD.generator;

import DaD.Commons.Collections.MultiValueSet;
import DaD.Template.DungeonRoomTemplate;
import DaD.Template.DungeonTemplate;
import DaD.data.types.MonsterInfo;
import DaD.dungeon.*;
import DaD.creature.MonsterInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 08/02/2017.
 * Singleton use to create {@link DungeonInstance} from
 * a {@link DungeonTemplate}
 */
public class DungeonGenerator
{
	/**
	 * Private instance of class.
	 */
	private static final DungeonGenerator _instance = new DungeonGenerator();

	/**
	 * Private constructor of class.
	 */
	private DungeonGenerator(){}

	/**
	 * Accessor for private instance of class.
	 * @return DungeonGenerator
	 */
	public static final DungeonGenerator getInstance()
	{
		return _instance;
	}


	/**
	 * Create a {@link DungeonInstance} from
	 * a given {@link DungeonTemplate}.
	 * @param dungeonTemplate The chosen template.
	 * @return DungeonInstance
	 */
	public DungeonInstance createDungeon(DungeonTemplate dungeonTemplate){
		// Set the MultiValue Set where we'll stock all the dungeon information
		MultiValueSet dungeonInformation = new MultiValueSet();

		DungeonRoomTemplate dungeonRoomTemplate;
		ArrayList<DungeonRoomInstance> dungeonRoomList = new ArrayList<>();

		for(int i = 0; i < dungeonTemplate.getTotalRoomCount(); i++){
			dungeonRoomTemplate = dungeonTemplate.getDungeonRoom(i);
			ArrayList<MonsterInfo> monsterInfoList = dungeonRoomTemplate.getMonsterList();
			// This is the list that will contain all monsterInstance of the room
			ArrayList<MonsterInstance> monsterInstancesList =  new ArrayList<>();
			for (MonsterInfo monsterInfo:monsterInfoList){
				// For each monsterInformation, we create an instance representing it
				monsterInstancesList.add(NpcGenerator.getInstance().createMonster(monsterInfo));
			}
			dungeonRoomList.add(new DungeonRoomInstance(monsterInstancesList,i+1));
		}

		dungeonInformation.put("roomList",dungeonRoomList);
		dungeonInformation.put("name",dungeonTemplate.getName());
		dungeonInformation.put("requiredLevel",dungeonTemplate.getRequiredLevel());
		dungeonInformation.put("levelDifficulty",dungeonTemplate.getLevelDifficulty());
		dungeonInformation.put("experienceReward",dungeonTemplate.getExperienceReward());
		return new DungeonInstance(dungeonInformation);
	}
}