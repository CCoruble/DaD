package DaD.dungeon;

import DaD.commons.MultiValueSet;

import java.util.ArrayList;

/**
 * Created by Clovis on 08/02/2017.
 */
public class DungeonInstance
{
	private final String _name;
	private int _currentRoomOrder;
	private final int _totalRoomCount;
	private final double _goldReward;
	private final double _experienceReward;
	// Add an item reward later
	private final int _levelDifficulty;
	private final int _requiredLevel;
	private final ArrayList<DungeonRoomInstance> _roomList; // Contains all the room of the dungeon

	public DungeonInstance(MultiValueSet dungeonInformation){
		_name = dungeonInformation.getString("name");
		_roomList = (ArrayList)dungeonInformation.getObject("roomList");
		_currentRoomOrder = 1; // By default we start at the first room of the dungeon
		_totalRoomCount = _roomList.size();
		_goldReward = dungeonInformation.getDouble("goldReward");
		_experienceReward = dungeonInformation.getDouble("experienceReward");
		_levelDifficulty = dungeonInformation.getInteger("levelDifficulty");
		_requiredLevel = dungeonInformation.getInteger("requiredLevel");
	}

	public DungeonRoomInstance getCurrentRoom(){
		// In arraylist index start at 1, CurrentRoomOrder start at 0
		return _roomList.get(_currentRoomOrder - 1);
	}

	public int getCurrentRoomOrder(){
		return _currentRoomOrder;
	}

	public void increaseCurrentRoomOrder(){
		_currentRoomOrder++;
	}

	public int getTotalRoomCount(){
		return _totalRoomCount;
	}

	public int getLevelDifficulty(){
		return _levelDifficulty;
	}

	public double getGoldReward(){
		return _goldReward;
	}
	public double getExperienceReward(){
		return _experienceReward;
	}

	public ArrayList<DungeonRoomInstance> getRoomList(){
		return _roomList;
	}
}
