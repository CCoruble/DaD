package DaD.handler;

import DaD.Commons.Utils.InputFunction;
import DaD.Holder.DungeonHolder;
import DaD.Template.DungeonRoomTemplate;
import DaD.Template.DungeonTemplate;
import DaD.creature.Hero;
import DaD.data.types.DungeonExitState;
import DaD.data.types.FightExitState;
import DaD.dungeon.*;
import DaD.generator.DungeonGenerator;

import java.util.*;

/**
 * Class used to handle dungeons.
 * When hero want to enter a dungeon it is
 * redirected here. Every events will be handled here.
 * @see DungeonInstance
 * @see DungeonRoomInstance
 * @see DungeonTemplate
 * @see DungeonRoomTemplate
 */
public class DungeonHandler {

    /**
     * Private instance of class.
     */
    private static final DungeonHandler _instance = new DungeonHandler();
    /**
     * Instance of the dungeon to handle.
     */
    private DungeonInstance _dungeon;
    /**
     * All available options.
     */
    private static final String[] _options = {"Commencer le combat","Afficher tes statistiques","Quitter le donjon"};

    /**
     * Private constructor of class.
     */
    private DungeonHandler() {}

    /**
     * Accessor for private instance of class.
     * @return DungeonHandler
     */
    public static final DungeonHandler getInstance() {
        return _instance;
    }

    /**
     * Function that makes the hero
     * choose which dungeon he wants to enter.
     * @param hero Hero entering the dungeon
     * @return DungeonInstance
     */
    public DungeonInstance dungeonSetting(Hero hero){
        int choice;
        // Retrieve all dungeons the player can choose
        ArrayList<DungeonTemplate> availableDungeons = DungeonHolder.getInstance().getAvailableDungeon(hero);

        System.out.println("Dans quelle donjon veux-tu entrer ?");
        for(int i = 0; i < availableDungeons.size(); i++){
            System.out.println((i+1) + ": " + availableDungeons.get(i).getName());
        }
        System.out.println("Autre: Quitter");

        choice = InputFunction.getIntInput();
        if(choice > 0 && choice <= availableDungeons.size()){ // Player did a correct choice
            // Order start at 0 in ArrayList meanwhile hero choices are displayed starting at 1
            return DungeonGenerator.getInstance().createDungeon(availableDungeons.get(choice-1));
        }

        // Player want to leave
        return null;
    }

    /**
     * Here we handle all event while in
     * dungeon, displaying menu, entering room
     * and give rewards. This is done
     * by calling several sub-function.
     * @param hero Hero that enter the dungeon
     * @param dungeonInstance Chosen dungeonInstance
     */
    public void enterDungeon(Hero hero, DungeonInstance dungeonInstance) {
        initializeDungeon(hero,dungeonInstance);
        DungeonExitState exitState = dungeonLoop(hero);
        switch (exitState) {
            case HERO_SUCCEEDED:
                System.out.println("Bravo tu as fini ce donjon !");
                endDungeon(hero);
                break;
            case HERO_LEFT:
                System.out.println("A bientot dans un autre donjon aventurier !");
                break;
            case HERO_DIED:
                System.out.println("Tu es mort aventurier !");
                break;
        }
    }

    /**
     * For each room in the dungeon, call
     * {@link #dungeonRoomMenu()} and
     * call the right function depending on
     * the choice made by player.
     * @param hero Hero entering dungeon
     * @return DungeonExitState
     */
    public DungeonExitState dungeonLoop(Hero hero){
        while (_dungeon.getCurrentRoomOrder() <= _dungeon.getRoomList().size()) { // While we have not finished all rooms
            // Get the room the player will get into
            DungeonRoomInstance dungeonRoomInstance = _dungeon.getRoomList().get(_dungeon.getCurrentRoomOrder()-1); // List index start at 0 when currentRoomOrder start at 1
            int playerChoice = dungeonRoomMenu();
            FightExitState exitState = FightExitState.NONE;
            switch (playerChoice)
            {
                case 1:
                    exitState = FightHandler.getInstance().startFight(hero,dungeonRoomInstance.getMonsterList());
                    break;
                case 2:
                    hero.displayFullCharacteristic();
                    break;
                default:
                    return DungeonExitState.HERO_LEFT;
            }
            // Depending on what happened in the dungeonRoom fight
            switch (exitState){
                case HERO_SUCCEEDED:
                    _dungeon.increaseCurrentRoomOrder(); // Give access to the next room
                    break;
                case HERO_ESCAPED:
                    return DungeonExitState.HERO_LEFT;
                case HERO_DIED:
                    return DungeonExitState.HERO_DIED;
            }
        }
        // If we go here it mean hero won each dungeonRoom.
        return DungeonExitState.HERO_SUCCEEDED;
    }

    /**
     * Display dungeon options to player and return his choice
     * @return int
     */
    public int dungeonRoomMenu(){
        System.out.println("Tu es actuellement à la salle " + _dungeon.getCurrentRoomOrder() + " sur " + _dungeon.getTotalRoomCount() + ".\nQue veux-tu faire ?");

        for(int i = 0; i < _options.length; i++){
            System.out.println((i+1) + " : " + _options[i]);
        }
        System.out.println("Autre: Quitter");

        return InputFunction.getIntInput();
    }

    /**
     * Called at the beginning of {@link #enterDungeon(Hero, DungeonInstance) enterDungeon},
     * we set the attribute {@link #_dungeon} and
     * later can be used to apply special effect to the hero.
     * @param hero Hero entering dungeon
     * @param dungeonInstance Dungeon hero is entering in
     */
    public void initializeDungeon(Hero hero, DungeonInstance dungeonInstance){
        _dungeon = dungeonInstance;
        // Later can be used to apply effects to the hero.
    }

    /**
     * Called at the end of the dungeon, when
     * hero has finished it. Give rewards and
     * clean buffs.
     * @param hero Hero that has finished the dungeon
     */
    public void endDungeon(Hero hero){
        giveReward(hero);
        // Later can be used for special rewards / buff or buff clearing
    }

    /**
     * Give to the hero rewards
     * for finishing dungeon.
     * @param hero Hero who receives rewards
     */
    private void giveReward(Hero hero){
        hero.addExperience(_dungeon.getExperienceReward());
        System.out.println("Récompenses du donjon:");
        System.out.println("+" + (int)_dungeon.getExperienceReward() + " exp");
    }
}