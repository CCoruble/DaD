package DaD.monster;

import DaD.commons.MultiValueSet;
import DaD.commons.RandomGenerator;
import DaD.creature.Creature;
import DaD.creature.Hero;
import DaD.creature.Npc;
import DaD.data.types.MonsterRace;
import DaD.data.types.MonsterRarity;
import DaD.formulas.HeroFormulas;
import DaD.generator.ItemGenerator;
import DaD.item.ItemDropInfo;
import DaD.item.ItemInstance;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

/**
 * Created by Clovis on 12/06/2017.
 * Instance of monster, generated from a {@link MonsterTemplate template}.
 * @see MonsterTemplate
 */
public class MonsterInstance extends Npc
{
	/**
	 * Reference to the template from which this instance was created.
	 * @see MonsterTemplate
	 */
	private final MonsterTemplate _template;

	/**
	 * Rarity of the monster, this will influence his statistics.
	 * @see Npc
	 * @see Creature
	 */
	private MonsterRarity _rarity;

	/**
	 * Race of the monster, this will influence his statistics.
	 *
	 * <p>
	 *     This should be deleted because the race of monster
	 *     can be found in his template.
	 * </p>
	 * @see Npc
	 * @see Creature
	 */
	private MonsterRace _race;

	/**
	 * Constructor of class.
	 * @param stats MultiValueSet containing the rarity, race and template of monster.
	 * @see MultiValueSet
	 */
	public MonsterInstance(MultiValueSet stats){
		super(stats);
		_rarity = (MonsterRarity)stats.getEnum("rarity",MonsterRarity.class);
		_race = (MonsterRace)stats.getEnum("race",MonsterRace.class);
		_template = MonsterHolder.getInstance().getTemplate(stats.getInteger("templateId"));
	}

	/**
	 * When a Monster HP drop to 0, we give reward to the killer.
	 * <p>
	 *		Be careful, the instance is not deleted when calling this function !
	 *		This function only give rewards to the hero, the monster must be deleted
	 *		outside of this function.
	 * </p>
	 * @param killer The {@link Creature} that killed the monster.
	 * @see DaD.handler.FightHandler
	 */
	public void giveRewards(Creature killer){
		// If we are killed a by Hero
		if (killer instanceof Hero)
		{
			System.out.println(this + " vient de mourir sous les coups de " + killer.getName());
			Hero hero = (Hero)killer;
			// Then give the experience & gold to the killer
			double experience = HeroFormulas.calcExperienceGained(hero,this);
			hero.addExperience(experience);
			hero.addGold(getGold());
			dropItems(hero);
		}
	}

	/**
	 * Give items to the hero.
	 * <p>
	 *     The template contains a list of possibly droped items.
	 *     For each of them we call a randomNumberGenerator, if it success
	 *     then the item is dropped.
	 * </p>
	 * @param hero The hero that killed the monster
	 * @see DaD.inventory.Inventory
	 * @see ItemDropInfo
	 * @see RandomGenerator
	 */
	private void dropItems(Hero hero){
		ArrayList<ItemDropInfo> itemDropInfoList = _template.getItemDropInfoList();
		// for each itemDropInfo we try to create the item if the luck is enough for it
		for(ItemDropInfo itemDropInfo: itemDropInfoList) {
			// Each item can be dropped several times => a monster can have several times the same object
			ItemInstance itemInstance = ItemGenerator.getInstance().createItem(itemDropInfo);
			if (itemInstance == null) { // Item was not created because of RNG
				continue;
			} else if (hero.getInventory().addItem(itemInstance)) { // Item was created and inventory is not full
					System.out.println(getName() + " fait tomber " + itemInstance.getTemplate().getName() + "[x" + itemInstance.getStack() + "]");
			} else { // Item was created but inventory is full
					System.out.println("Votre inventaire est plein !");
			}
		}
	}

	/**
	 * Accessor for template of instance.
	 * @return MonsterTemplate
	 */
	public MonsterTemplate getTemplate(){
		return _template;
	}

	/**
	 * Accessor for rarity of instance.
	 * @return MonsterRarity
	 */
	public MonsterRarity getRarity() {
		return _rarity;
	}

	/**
	 * Mutator for rarity of instance.
	 * @param rarity New rarity of the mosnter.
	 */
	public void setRarity(MonsterRarity rarity){
		_rarity = rarity;
	}

	/**
	 * Accessor for race of instance.
	 * @return MonsterRace
	 */
	public MonsterRace getRace() {
		return _race;
	}

	/**
	 * Mutator for race of instance.
	 * @param race New race of the instance
	 */
	public void setRace(MonsterRace race) {
		_race = race;
	}

	/**
	 * Return the name of the monster.
	 * @return String
	 */
	@Override
	public String toString(){
		return getName();
	}

	/**
	 * Return general information about monster.
	 * <p>
	 *     Return a string containing almost all information about the instance.
	 *     This is used to display monster during a fight.
	 *     It contains rarity, name, name, hp and hp max of the monster.
	 *     The string is formatted with "{", "[", "/" to separate information
	 *     and easily understand them.
	 * </p>
	 * @return String
	 */
	public String getResumedInformation(){
		return ("{" + _rarity + "} " + getName() + " lv." + getLevel() + " [" + getHp().getValue() + "/" + getHpMax().getValue() + "]");
	}
}
