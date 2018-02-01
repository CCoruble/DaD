package DaD.handler;

import DaD.Debug.DebugLogger;
import DaD.creature.Hero;
import DaD.data.types.FightExitState;
import DaD.data.types.HeroDeathReason;
import DaD.dungeon.*;
import DaD.generator.DungeonGenerator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private static final String[] _options = {"Entrer dans la salle","Afficher tes caractéristiques","Quitter le donjon"};

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
     * @return DungeonInstance
     */
    public DungeonInstance dungeonSetting(Hero hero){
        Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the player's choice
        String input;
        int choice;
        // Retrieve all dungeons the player can choose
        ArrayList<DungeonTemplate> availableDungeons = DungeonHolder.getInstance().getAvailableDungeon(hero);

        System.out.println("Dans quelle donjon veux-tu entrer ?");
        for(int i = 0; i < availableDungeons.size(); i++){
            System.out.println((i+1) + ": " + availableDungeons.get(i).getName());
        }
        System.out.println("Autre: Quitter");

        try{
            input = scanner.nextLine();
            choice = Integer.parseInt(input);
            if(choice > 0 && choice <= availableDungeons.size()){ // Player did a correct choice
                // Order start at 0 in ArrayList meanwhile hero choices are displayed starting at 1
                return DungeonGenerator.getInstance().createDungeon(availableDungeons.get(choice-1));
            }
        } catch (Exception e) {
            DebugLogger.log(e);
        }
        // Player want to leave
        return null;
    }

    /**
     * Show available options to hero.
     * Depending on his choice call different functions.
     * <p>
     *     If you choose to enter the room here,
     *     this will call the {@link FightHandler}.
     *     Depending on the result of the fight we will
     *     either go to next room or leave the dungeon.
     * </p>
     * @param hero Hero that enter the dungeon
     * @param dungeon Choosen dungeonInstance
     */
    public void StartDungeon(Hero hero,DungeonInstance dungeon) {
        _dungeon = dungeon;
        while (true)
        {
            Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player

            System.out.println("Tu es actuellement à la salle " + _dungeon.getCurrentRoomOrder() + " sur " + _dungeon.getTotalRoomCount() + ".\nQue veux-tu faire ?");
            int playerChoice = 0;
            while (playerChoice < 1 || playerChoice > _options.length) //  Condition to stay is not giving a good answer (1 or 2)
            {
                for(int i = 0; i < _options.length; i++){
                    System.out.println((i+1) + " : " + _options[i]);
                }
                playerChoice = scanner.nextInt();
            }
            ///////////////////////////////////////////THIS MUST BE REWORKED TO BE MORE EFFICIENT/////////////////////////////////
            switch (playerChoice)
            {
                case 1:
                    if (goToDungeonRoom()) // Return true if the Hero succeed the DungeonRoomInstance
                    {
                        if (_dungeon.getCurrentRoomOrder() > _dungeon.getRoomList().size()) // If we finished all dungeon room
                        {
                            giveReward(hero); // The hero did success the entire DungeonInstance so he get rewarded and leave
                            System.out.println("Bravo tu as finis ce Donjon aventurier " + hero.getName() + " !");
                            return;
                        } else {
                            System.out.println("Bravo tu as finis cette salle !");
                        }
                    }
                    else { // The hero did fail the DungeonRoomInstance and died
                        System.out.println("Tu es mort aventurier !");
                        return;
                    }
                    break;
                case 2:
                    System.out.println(hero.displayFullCharacteristic());
                    break;
                case 3:
                    System.out.println("A bientot dans un autre donjon aventurier !");
                    return;
            }
            ///////////////////////////////////////////////////////END OF REWORK NEEDED//////////////////////////////////////////
        }
    }

    /**
     * Enter a dungeon room, start the fight
     * with monsters in it and depending on
     * fight result execute function.
     * Return true if Hero succeeded on killing monsters.
     * @return boolean
     */
    private boolean goToDungeonRoom(){
        FightExitState exitState = FightHandler.getInstance().startFight(Hero.getInstance(), _dungeon.getCurrentRoom().getMonsterList()); // This function will return true if the hero killed all the monsters
        switch(exitState) {
            case HERO_ESCAPED:
                throw new NotImplementedException();

            case HERO_SUCCEEDED:
                // Increase the room order so next time he'll enter next dungeon room
                _dungeon.increaseCurrentRoomOrder();
                return true;

            case HERO_DIED:
                // Hero is dead he make him go back to life and apply Killed_By_Monster loss (exp / gold)
                Hero.getInstance().resurect(HeroDeathReason.KILLED_BY_MONSTER);
                return false;

            default: // Should never be reached
                return false;
        }
    }

    /**
     * Give to the hero rewards
     * for finishing dungeon.
     * @param hero Hero who receives rewards
     */
    private void giveReward(Hero hero){
        hero.addGold(_dungeon.getGoldReward());
        hero.addExperience(_dungeon.getExperienceReward());
    }
}