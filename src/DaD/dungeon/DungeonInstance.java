package DaD.dungeon;

import DaD.commons.MultiValueSet;

import java.util.ArrayList;

/**
 * Created by Clovis on 08/02/2017.
 * Instance of dungeon based on a
 * {@link DungeonTemplate}, it contains
 * {@link DungeonRoomInstance}.
 */
public class DungeonInstance
{
	/**
	 * Name of dungeon.
	 */
	private final String _name;
	/**
	 * Used to know in which room we are.
	 */
	private int _currentRoomOrder;
	/**
	 * Total of {@link DungeonRoomInstance}
	 * in this dungeon.
	 */
	private final int _totalRoomCount;
	/**
	 * Amount of gold earned by player
	 * upon finishing this dungeon
	 */
	private final double _goldReward;
	/**
	 * Amount of experience earned by player
	 * upon finishing this dungeon
	 */
	private final double _experienceReward;
	// Add an item reward later
	/**
	 * Indicator of difficulty.
	 */
	private final int _levelDifficulty;
	/**
	 * Required level to get in the dungeon.
	 */
	private final int _requiredLevel;
	/**
	 * ArrayList containing all
	 * {@link DungeonRoomInstance}.
	 */
	private final ArrayList<DungeonRoomInstance> _roomList; // Contains all the room of the dungeon

	/**
	 * Constructor of class.
	 * @param dungeonInformation MultiValueSet containing all information about dungeon
	 */
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

	/**
	 * Return a {@link DungeonRoomInstance} according
	 * to the {@link #_currentRoomOrder}.
	 * @return  DungeonRoomInstance
	 */
	public DungeonRoomInstance getCurrentRoom(){
		// In arraylist index start at 1, CurrentRoomOrder start at 0
		return _roomList.get(_currentRoomOrder - 1);
	}

	/**
	 * Return {@link #_currentRoomOrder}.
	 * @return int
	 */
	public int getCurrentRoomOrder(){
		return _currentRoomOrder;
	}

	/**
	 * Increase {@link #_currentRoomOrder} by 1.
	 */
	public void increaseCurrentRoomOrder(){
		_currentRoomOrder++;
	}

	/**
	 * Return {@link #_totalRoomCount}.
	 * @return int
	 */
	public int getTotalRoomCount(){
		return _totalRoomCount;
	}

	/**
	 * Return {@link #_levelDifficulty}
	 * @return int
	 */
	public int getLevelDifficulty(){
		return _levelDifficulty;
	}

	/**
	 * Return {@link #_goldReward}.
	 * @return double
	 */
	public double getGoldReward(){
		return _goldReward;
	}

	/**
	 * Return {@link #_experienceReward}.
	 * @return double
	 */
	public double getExperienceReward(){
		return _experienceReward;
	}

	/**
	 * Return an ArrayList containing all
	 * {@link DungeonRoomInstance} in this dungeon.
	 * @return ArrayList
	 */
	public ArrayList<DungeonRoomInstance> getRoomList(){
		return _roomList;
	}
}
