package DaD.Template;

import DaD.Commons.Utils.RandomGenerator;
import DaD.Commons.Collections.MultiValueSet;
import DaD.Holder.MonsterHolder;
import DaD.data.types.MonsterRace;
import DaD.data.types.Stats.Env;
import DaD.item.ItemDropInfo;
import DaD.creature.MonsterInstance;

import java.util.ArrayList;

/**
 * Created by Clovis on 08/02/2017.
 * Template to create a monsterInstance. It contains every stats
 * to create a monster depending on its level and rarity.
 * YOU MUST UNDERSTAND {@link Env} TO UNDERSTAND HOW STATS ARE CALCULATED AND USED.
 * @see Env
 * @see MonsterInstance
 * @see MonsterHolder
 */
public class MonsterTemplate
{
	/**
	 * Id of the template.
	 */
	private final int _id;
	/**
	 * Name of the template.
	 */
	private final String _name;
	/**
	 * Description of the template.
	 */
	private final String _description;

	// Base stats
	/**
	 * Percent of max hp the monster will start with.
	 * <p>
	 *     The value must be between 0 and 100 !
	 *     This is usefull on certain boss with heal skills.
	 *     If a monster has 10 Hp max, and 70 of percent hp,
	 *     his current hp will be 7 on beginning of fight.
	 *     HOWEVER, his max hp is still 10 !
	 * </p>
	 */
	private final double _hpPercent;
	/**
	 * Maximum hp that a monster can reach.
	 * <p>
	 *     You cannot have more Hp than maxHp.
	 *     If you want a monster to have an amount of Hp higher than his actual maxHPp
	 *     you MUST rise the maxHp ABOVE OR EQUALLY to this amount.
	 * </p>
	 */
	private final Env _maxHp;
	/**
	 * Attack of the monster.
	 */
	private final Env _attack;
	/**
	 * Defense of the monster.
	 */
	private final Env _defense;
	/**
	 * Experience of the monster, this will be given to the killer when it dies.
	 */
	private final Env _experience;

	/**
	 * List of possibly dropped items.
	 * <p>
	 *     Each possibly dropped items has a percent of chance
	 *     to be dropped.
	 *     When the monster dies we will use a RandomNumberGenerator
	 *     for each possibly dropped items to see if it is dropped
	 *     or not.
	 * </p>
	 * @see ItemDropInfo
	 * @see RandomGenerator
	 */
	private final ArrayList<ItemDropInfo> _itemDropInfoList;

	// Env per level
	/**
	 * Max Hp per level, used to calculate max Hp of the monsterInstance.
	 */
	private final Env _maxHpPerLevel;
	/**
	 * Attack per level used to calculate attack of the monsterInstance.
	 */
	private final Env _attackPerLevel;
	/**
	 * Defense per level, used to calculate defense of the monsterInstance.
	 */
	private final Env _defensePerLevel;
	/**
	 * Experience per level, used to calculate experience of the monsterInstance.
	 */
	private final Env _experiencePerLevel;

	// Rarity modifier
	/**
	 * Max hp for each rarity above minimum rarity, used to calculate max Hp of the monsterInstance.
	 */
	private final Env _maxHpRarityModifier;
	/**
	 * Attack for each rarity above minimum rarity, used to calculate attack of the monsterInstance.
	 */
	private final Env _attackRarityModifier;
	/**
	 * Defense for each rarity above minimum rarity, used to calculate defense of the monsterInstance.
	 */
	private final Env _defenseRarityModifier;
	/**
	 * Experience for each rarity above minimum rarity, used to calculate experience of the monsterInstance.
	 */
	private final Env _experienceRarityModifier;

	// Rarity and Race
	/**
	 * Minimum rarity of the monster.
	 * <p>
	 *     A monster has a rarity chosen between his
	 *     minimum and maximum rarity.
	 * </p>
	 * @see DaD.data.types.MonsterRarity
	 */
	private final int _minimumRarity;
	/**
	 * Maximum rarity of the monster.
	 * <p>
	 *     A monster has a rarity chosen between his
	 *     minimum and maximum rarity.
	 * </p>
	 * @see DaD.data.types.MonsterRarity
	 */
	private final int _maximumRarity;
	/**
	 * Race of the monster.
	 * @see MonsterRace
	 */
	private final MonsterRace _race;

	/**
	 * Constructor of class.
	 * @param monstersStats MultiValueSet containing all information on the template.
	 */
	public MonsterTemplate(MultiValueSet monstersStats){
		_id = monstersStats.getInteger("id");
		_name = monstersStats.getString("name");
		_description = monstersStats.getString("description");

		// Base stats
		_maxHp = monstersStats.getEnv("maxHp");
		_hpPercent = monstersStats.getDouble("hpPercent",100);
		_attack = monstersStats.getEnv("attack");
		_defense = monstersStats.getEnv("defense");
		_experience = monstersStats.getEnv("experience");
		_itemDropInfoList = monstersStats.getArrayList("itemDropInfoList");

		// Env per level
		_maxHpPerLevel = monstersStats.getEnv("maxHpPerLevel");
		_attackPerLevel = monstersStats.getEnv("attackPerLevel");
		_defensePerLevel = monstersStats.getEnv("defensePerLevel");
		_experiencePerLevel = monstersStats.getEnv("experiencePerLevel");

		// Rarity modifier
		_maxHpRarityModifier = monstersStats.getEnv("maxHpRarityModifier");
		_attackRarityModifier = monstersStats.getEnv("attackRarityModifier");
		_defenseRarityModifier = monstersStats.getEnv("defenseRarityModifier");
		_experienceRarityModifier = monstersStats.getEnv("experienceRarityModifier");

		// Race and Rarity
		_race = (MonsterRace)monstersStats.getEnum("race",MonsterRace.class);
		_minimumRarity = monstersStats.getInteger("minimumRarity");
		_maximumRarity = monstersStats.getInteger("maximumRarity");
	}

	/**
	 * Accessor for ID of the template.
	 * @return int
	 */
	public int getId(){
		return _id;
	}

	/**
	 * Accessor for name of the template.
	 * @return String
	 */
	public String getName(){
		return _name;
	}

	/**
	 * Accessor for description of the template.
	 * @return String
	 */
	public String getDescription(){
		return _description;
	}

	/**
	 * Display all information about the template.
	 */
	public void displayTemplate(){
		System.out.println("id: " + _id);
		System.out.println("name:" + _name);
		System.out.println("description: " + _description);

		// Base stats
		System.out.println("hpPercent: " + _hpPercent);
		System.out.println("maxHp: \n" + _maxHp);
		System.out.println("attack: \n" + _attack);
		System.out.println("defense: \n" +  _defense);
		System.out.println("experience: \n" + _experience);
		System.out.println("itemDropInfoList: ");
		for(ItemDropInfo itemDropInfo: _itemDropInfoList){
			itemDropInfo.displayItemDropInfo();
		}
		// Add dropable item list here later

		// Env per level
		System.out.println("maxHpPerLevel: \n" + _maxHpPerLevel);
		System.out.println("attackPerLevel: \n" + _attackPerLevel);
		System.out.println("defensePerLevel: \n" + _defensePerLevel);
		System.out.println("experiencePerLevel: \n" + _experiencePerLevel);

		// Rarity modifier
		System.out.println("maxHpRarityModifier: \n" + _maxHpRarityModifier);
		System.out.println("attackRarityModifier: \n" + _attackRarityModifier);
		System.out.println("defenseRarityModifier \n" + _defenseRarityModifier);
		System.out.println("experienceRarityModifier: \n" + _experienceRarityModifier);

		// Rarity and Race
		System.out.println("minimumRarity: " + _minimumRarity);
		System.out.println("maximumRarity: " + _maximumRarity);
		System.out.println("race: " + _race);
	}

	// Base stats
	/**
	 * Accessor for percent Hp of the template.
	 * @return double
	 */
	public double getHpPercent(){
		return _hpPercent;
	}
	/**
	 * Accessor for maxHp of the template.
	 * @return Env
	 */
	public Env getMaxHp(){
		return _maxHp;
	}
	/**
	 * Accessor for attack of the template.
	 * @return Env
	 */
	public Env getAttack(){
		return _attack;
	}
	/**
	 * Accessor for defense of the template.
	 * @return Env
	 */
	public Env getDefense(){
		return _defense;
	}
	/**
	 * Accessor for experience of the template.
	 * @return Env
	 */
	public Env getExperience(){
		return _experience;
	}
	/**
	 * Return the complete list of possibly dropped items.
	 * @return ArrayList
	 * @see ItemDropInfo
	 */
	public ArrayList<ItemDropInfo> getItemDropInfoList() {
		return _itemDropInfoList;
	}

	// Env per level
	/**
	 * Accessor for max Hp per level of the template.
	 * @return Env
	 */
	public Env getMaxHpPerLevel(){
		return _maxHpPerLevel;
	}
	/**
	 * Accessor for attack per level of the template.
	 * @return Env
	 */
	public Env getAttackPerLevel(){
		return _attackPerLevel;
	}
	/**
	 * Accessor for the defense per level of the template.
	 * @return Env
	 */
	public Env getDefensePerLevel(){
		return _defensePerLevel;
	}
	/**
	 * Accessor for experience per level of the template.
	 * @return Env
	 */
	public Env getExperiencePerLevel(){
		return _experiencePerLevel;
	}

	// Rarity modifier
	/**
	 * Accessor for the max Hp rarity modifier of the template.
	 * @return Env
	 */
	public Env getMaxHpRarityModifier(){
		return _maxHpRarityModifier;
	}
	/**
	 * Accessor for the attack rarity modifier of the template.
	 * @return Env
	 */
	public Env getAttackRarityModifier(){
		return _attackRarityModifier;
	}
	/**
	 * Accessor for the defense rarity modifier of the template.
	 * @return Env
	 */
	public Env getDefenseRarityModifier(){
		return _defenseRarityModifier;
	}
	/**
	 * Accessor for the experience rarity modifier of the template.
	 * @return Env
	 */
	public Env getExperienceRarityModifier(){
		return _experienceRarityModifier;
	}

	//Rarity and race
	/**
	 * Accessor for the race of the template.
	 * @return MonsterRace
	 */
	public MonsterRace getMonsterRace(){
		return _race;
	}
	/**
	 * Accessor for the minimum rarity of the template.
	 * @return int
	 */
	public int getMinimumRarity(){
		return _minimumRarity;
	}
	/**
	 * Accessor for the maximum rarity of the template.
	 * @return int
	 */
	public int getMaximumRarity(){
		return _maximumRarity;
	}
}