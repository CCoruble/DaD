package DaD.generator;

import DaD.commons.MultiValueSet;
import DaD.data.types.MonsterInfo;
import DaD.data.types.MonsterRace;
import DaD.data.types.MonsterRarity;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import DaD.data.types.Stats.Stats;
import DaD.formulas.MonsterFormulas;
import DaD.monster.MonsterHolder;
import DaD.monster.MonsterInstance;
import DaD.monster.MonsterTemplate;

/**
 * Created by Clovis on 08/02/2017.
 * Used to split information from a
 * {@link MonsterInfo} and return a {@link MonsterInstance}
 */
public class NpcGenerator
{
	/**
	 * Private instance of class.
	 */
	private static final NpcGenerator _instance = new NpcGenerator();

	/**
	 * Private constructor of class.
	 */
	private NpcGenerator(){}

	/**
	 * Accessor for instance of class.
	 * @return NpcGenerator
	 */
	public static final NpcGenerator getInstance()
	{
		return _instance;
	}

	/**
	 * Separate information from the
	 * MonsterInfo, call constructor and
	 * return the MonsterInstance created
	 * @param monsterInfo Information about the monster.
	 * @return MonsterInstance
	 */
	public MonsterInstance createMonster(MonsterInfo monsterInfo){
		// Retrieve the template accord to id in monsterInfo
		int templateID = monsterInfo.getId();
		MonsterTemplate template = MonsterHolder.getInstance().getTemplate(templateID);

		// Get all stats of the monster created from the template
		String name = template.getName();
		MonsterRace race = template.getMonsterRace();
		MonsterRarity rarity = monsterInfo.getRarity();
		int level = monsterInfo.getLevel(); // get the monster level

		double hpMaxValue = MonsterFormulas.calcHpMax(template,level,rarity);
		Env hpMax = new Env(hpMaxValue, StatType.SET, Stats.HP_MAX, 10);

		double hpValue = MonsterFormulas.calcHp(hpMaxValue, template.getHpPercent());
		Env hp = new Env(hpValue, StatType.SET, Stats.HP, 10);

		double attackValue = MonsterFormulas.calcAttack(template,level,rarity);
		Env attack = new Env(attackValue, StatType.SET, Stats.ATTACK, 10);

		double defenseValue = MonsterFormulas.calcDefense(template,level,rarity);
		Env defense = new Env(defenseValue, StatType.SET, Stats.DEFENSE, 10);

		double gold = MonsterFormulas.calcGold(template);

		// Add the stats to the MultiValueSet
		MultiValueSet stats = new MultiValueSet();
		stats.set("templateId",templateID);
		stats.set("name",name);
		stats.set("race",race);
		stats.set("rarity",rarity);
		stats.set("level",level);
		stats.set("hpMax",hpMax);
		stats.set("hp",hp);
		stats.set("attack",attack);
		stats.set("defense",defense);
		stats.set("gold",gold);

		// Create a MonsterInstance
		return new MonsterInstance(stats);
	}
}
