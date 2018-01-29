package DaD.dungeon;

import DaD.commons.MultiValueSet;

import java.util.ArrayList;

/**
 * Template used to create {@link DungeonInstance}
 */
public class DungeonTemplate {

	private final String _name;
	private final int _requiredLevel;
	private final double _goldReward;
	private final double _experienceReward;
	/**
	 * A simple indicator, not really
	 * used, to warn player.
	 */
	private final int _levelDifficulty;
	/**
	 * ArrayList containing all
	 * {@link DungeonRoomTemplate} in this dungeon.
	 */
	private final ArrayList<DungeonRoomTemplate> _roomTemplateList =  new ArrayList<>(); // Contains all the roomTemplate of the dungeon

	/**
	 * Create a DungeonTemplate from a
	 * {@link MultiValueSet} containing all
	 * information.
	 * @param dungeonStats MultiValue set containing all information
	 */
	public DungeonTemplate(MultiValueSet dungeonStats){
		_name = dungeonStats.getString("name");
		_levelDifficulty = dungeonStats.getInteger("difficulty");
		_requiredLevel = dungeonStats.getInteger("requiredLevel");
		_goldReward = dungeonStats.getInteger("goldReward");
		_experienceReward = dungeonStats.getInteger("experienceReward");

		ArrayList<MultiValueSet> allRooms = (ArrayList<MultiValueSet>)dungeonStats.getObject("rooms");
		for(MultiValueSet room: allRooms){
			_roomTemplateList.add(new DungeonRoomTemplate(room));
		}
	}

	/**
	 * Return number of room in this dungeon.
	 * @return int
	 */
	public int getTotalRoomCount(){
		return _roomTemplateList.size();
	}

	/**
	 * Return a room corresponding to the given index.
	 * @param index Index of the room in the roomList
	 * @return DungeonRoomTemplate
	 */
	public DungeonRoomTemplate getDungeonRoom(int index){
		return _roomTemplateList.get(index);
	}

	/**
	 * Return name of the dungeon.
	 * @return String
	 */
	public String getName(){
		return _name;
	}

	/**
	 * Return required level of dungeon.
	 * @return int
	 */
	public int getRequiredLevel(){
		return _requiredLevel;
	}

	/**
	 * Return amount of gold earned when
	 * finishing this dungeon.
	 * @return double
	 */
	public double getGoldReward(){
		return _goldReward;
	}

	/**
	 * Return amount of experience earned
	 * when finishing this dungeon.
	 * @return double
	 */
	public double getExperienceReward(){
		return _experienceReward;
	}

	/**
	 * Return difficulty of this dungeon.
	 * @return int
	 */
	public int getLevelDifficulty(){
		return _levelDifficulty;
	}

	/**
	 * Display each attributes of this template and
	 * each of his room.
	 * <p>
	 *     Iterate on the list of room
	 *     and call displayTemplate for each.
	 * </p>
	 */
	public void displayTemplate(){
		System.out.println("name: " + _name);
		System.out.println("requiredLevel: " + _requiredLevel);
		System.out.println("goldReward: " + _goldReward);
		System.out.println("experienceReward: " + _experienceReward);
		System.out.println("levelDifficulty: " + _levelDifficulty);
		for(DungeonRoomTemplate roomTemplate: _roomTemplateList){
			roomTemplate.displayTemplate();
		}
	}
}
