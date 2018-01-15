package DaD.creature;

import DaD.commons.MultiValueSet;
import DaD.data.types.Stats.Env;
import DaD.formulas.FightFormulas;

/**
 * Created by Clovis on 07/02/2017.
 */
public abstract class Creature
{
	private String _name;
	private int _level;
	private Env _hp;
	private Env _hpMax;
	private Env _attack;
	private Env _defense;
	private double _gold;
	private Env _mp;
	private Env _mpMax;
	// Add _spell, _critsChance, _dodge ...

	Creature(){}

	Creature(MultiValueSet stats) {
		_name = stats.getString("name");
		_level = stats.getInteger("level");

		//Env
		_attack = stats.getEnv("attack");
		_defense = stats.getEnv("defense");
		_hp = stats.getEnv("hp");
		_hpMax = stats.getEnv("hpMax");
		_gold = stats.getDouble("gold");
	}

	public int attack(Creature receiver){
		int damageDealt = FightFormulas.calcDamageDealt(this, receiver);
		receiver.setHpValue(Math.max(receiver.getHp().getValue() - damageDealt, 0));
		return damageDealt;
	}

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
	public void setLevel(int level)
	{
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
	public void setHpValue(double hp)
	{
		_hp.setValue(Math.min(hp,_hpMax.getValue()));
	}
	public void addHp(double hp){
		_hp.setValue(_hp.getValue() + hp);
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
	public int getPercentMp(){
		return (int)((_mp.getValue() / _mpMax.getValue()) * 100);
	}

	//Gold
	public double getGold(){
		return _gold;
	}
	public void setGold(double gold){
		_gold = gold;
	}
	public void addGold(double gold){
		_gold += gold;
	}
	public void decreaseGold(double gold){
		_gold -= gold;
	}


	// Mana points
	public Env getMp(){
		return _mp;
	}
	public void setMp(Env mp){
		_mp = mp;
	}
	public void setMpValue(double mp){
		_mp.setValue(mp);
	}
	public double getMpMax(){
		return _mpMax.getValue();
	}
	public void setMpMax(Env mpMax){
		_mpMax = mpMax;
	}
	public void setMpMaxValue(double mpMax){
		_mpMax.setValue(mpMax);
	}
}