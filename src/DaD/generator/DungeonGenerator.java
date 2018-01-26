package DaD.generator;

import DaD.commons.MultiValueSet;
import DaD.data.types.MonsterInfo;
import DaD.dungeon.*;
import DaD.monster.MonsterInstance;

import java.util.ArrayList;
import java.util.Set;

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
	 * Return an instance from the choosen
	 * {@link DungeonTemplate} id.
	 * @param dungeonTemplateId The id of the dungeonTemplate
	 * @return DungeonInstance
	 */
	public DungeonInstance createDungeon(int dungeonTemplateId){
		// Set the MultiValue Set where we'll stock all the dungeon information
		MultiValueSet dungeonInformation = new MultiValueSet();

		// Get the dungeonTemplate from the list of template in the DungeonHolder
		DungeonTemplate dungeonTemplate = DungeonHolder.getInstance().getTemplate(dungeonTemplateId);

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
		dungeonInformation.put("goldReward",dungeonTemplate.getGoldReward());
		return new DungeonInstance(dungeonInformation);
	}
}