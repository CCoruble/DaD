package DaD.dungeon;

import DaD.commons.MultiValueSet;

import java.util.ArrayList;

public class DungeonTemplate {

	private final String _name;
	private final int _requiredLevel;
	private final double _goldReward;
	private final double _experienceReward;
	// Add an item reward later
	private final int _levelDifficulty;
	private final ArrayList<DungeonRoomTemplate> _roomTemplateList =  new ArrayList<>(); // Contains all the roomTemplate of the dungeon

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

	public int getTotalRoomCount(){
		return _roomTemplateList.size();
	}
	public DungeonRoomTemplate getDungeonRoom(int index){
		return _roomTemplateList.get(index);
	}
	public String getName(){
		return _name;
	}
	public int getRequiredLevel(){
		return _requiredLevel;
	}
	public double getGoldReward(){
		return _goldReward;
	}
	public double getExperienceReward(){
		return _experienceReward;
	}
	public int getLevelDifficulty(){
		return _levelDifficulty;
	}

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
