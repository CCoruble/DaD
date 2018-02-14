package DaD.creature;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.Stats.Env;
import DaD.formulas.FightFormulas;

/**
 * Created by Clovis on 07/02/2017.
 * Mother class for every creature, both
 * player and non player in the game.
 * @see Npc
 * @see Hero
 * @see Env
 */
public abstract class Creature
{
	private String _name;
	private int _level;
	private Env _hp;
	private Env _hpMax;
	private Env _attack;
	private Env _defense;
	private Env _mp;
	private Env _mpMax;
	// Add _spell, _critsChance, _dodge ...

	/**
	 * Constructor of class.
	 */
	Creature(){}

	/**
	 * Constructor of class.
	 * @param stats MultiValueSet containing all information.
	 */
	Creature(MultiValueSet stats) {
		_name = stats.getString("name");
		_level = stats.getInteger("level");

		//Env
		_attack = stats.getEnv("attack");
		_defense = stats.getEnv("defense");
		_hpMax = stats.getEnv("hpMax");
		_hp = stats.getEnv("hp");
		_mpMax = stats.getEnv("mpMax");
		_mp = stats.getEnv("mp");
	}

	/**
	 * Call different function to calculate and inflict
	 * damage to a, receiver, creature.
	 * <p>
	 *     Amount of damage dealt is calculated by
	 *     {@link FightFormulas#calcDamageDealt(Creature, Creature) calcDamageDealt},
	 * 	   reduce hp of the receiver and return this same amount.
	 * </p>
	 * @param receiver Creature receiving the attack.
	 * @return int Amount of damage dealt
	 * @see FightFormulas
	 */
	public int attack(Creature receiver){
		int damageDealt = FightFormulas.calcDamageDealt(this, receiver);
		receiver.removeHp(damageDealt);
		return damageDealt;
	}

	/**
	 * Return true if hp value is below 1.
	 * @return boolean
	 */
	public boolean isDead()
	{
		return _hp.getValue() < 1;
	} // return true if the creature is dead

	// Name
	public String getName()
	{
		return _name;
	}
	public void setName(String name)
	{
		_name = name;
	}

	// Level
	public int getLevel()
	{
		return _level;
	}
	public void setLevel(int level) {
		_level = level;
	}

	// Attack
	public Env getAttack(){
		return _attack;
	}
	public void setAttack(Env attack){
		_attack = attack;
	}
	void setAttackValue(double attack)
	{
		_attack.setValue(attack);
	}
	public void addAttack(double attack){
		_attack.setValue(_attack.getValue() + attack);
	}

	// Defense
	public Env getDefense()
	{
		return _defense;
	}
	public void setDefense(Env defense){
		_defense = defense;
	}
	void setDefenseValue(double defense)
	{
		_defense.setValue(defense);
	}
	public void addDefense(double defense){
		_defense.setValue(_defense.getValue() + defense);
	}

	// HP
	public Env getHp()
	{
		return _hp;
	}
	public void setHp(Env hp){
		_hp =  hp;
	}
	public void addHp(double hp){
		_hp.setValue(Math.min(_hp.getValue() + hp, _hpMax.getValue()));
	}
	public void removeHp(double hpLost){
		_hp.setValue(Math.max(_hp.getValue() - hpLost,0));
	}
	public double getPercentHp(){
		return (_hp.getValue() /_hpMax.getValue()) * 100;
	}

	// HPMax
	public Env getHpMax()
	{
		return _hpMax;
	}
	public void setHpMax(Env hpMax){
		_hpMax = hpMax;
	}
	void setHpMaxValue(double hpMax)
	{
		_hpMax.setValue(hpMax);
	}
	public void addHpMax(double hpMax){
		_hpMax.setValue(_hpMax.getValue() + hpMax);
	}

	// MP
	public Env getMp(){
		return _mp;
	}
	public void setMp(Env mp){
		_mp = mp;
	}
	public void setMpValue(double mp){
		_mp.setValue(mp);
	}
	public double getPercentMp(){
		return (_mp.getValue() / _mpMax.getValue()) * 100;
	}

	// MPMax
	public Env getMpMax(){
		return _mpMax;
	}
	public void setMpMax(Env mpMax){
		_mpMax = mpMax;
	}
	public void setMpMaxValue(double mpMax){
		_mpMax.setValue(mpMax);
	}
}