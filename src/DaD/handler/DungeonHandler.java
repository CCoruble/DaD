package DaD.handler;

import DaD.commons.MultiValueSet;
import DaD.creature.Hero;
import DaD.data.types.DungeonRoomExitState;
import DaD.data.types.HeroDeathReason;
import DaD.data.types.MonsterInfo;
import DaD.dungeon.*;
import DaD.generator.DungeonGenerator;
import DaD.generator.NpcGenerator;
import DaD.monster.MonsterInstance;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
    public DungeonInstance dungeonSetting(){
        Scanner scanner = new Scanner(System.in); // Setting up a scanner to get the choice made by player
        String input;
        int dungeonId;
        while(true){
            try {
                System.out.println("Dans quelle donjon veux-tu entrer ?");
                for(Map.Entry<Integer, DungeonTemplate> entry : DungeonHolder.getInstance().getTemplateList().entrySet()){
                    DungeonTemplate dungeonTemplate = entry.getValue();
                    if(dungeonTemplate.getRequiredLevel() <= Hero.getInstance().getLevel()){
                        System.out.println(dungeonTemplate.getName());
                    }
                }
                input = scanner.next();
                dungeonId = Integer.parseInt(input);
                if (!DungeonHolder.getInstance().getTemplateList().containsKey(dungeonId)
                        || DungeonHolder.getInstance().getTemplateList().get(dungeonId).getRequiredLevel() > Hero.getInstance().getLevel())
                {
                    System.out.println("Ce niveau de donjon est trop élevé ou ce donjon n'existe pas.");
                    continue;
                }
                return DungeonGenerator.getInstance().createDungeon(dungeonId);
            } catch (Exception e){
                e.printStackTrace();
                System.out.println("Ce n'est pas une valeur valide!");
            }
        }
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
     * @return
     */
    private boolean goToDungeonRoom(){
        DungeonRoomExitState exitState = FightHandler.getInstance().startFight(Hero.getInstance(), _dungeon.getCurrentRoom().getMonsterList()); // This function will return true if the hero killed all the monsters
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
     */
    private void giveReward(Hero hero){
        hero.addGold(_dungeon.getGoldReward());
        hero.addExperience(_dungeon.getExperienceReward());
    }
}