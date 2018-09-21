package DaD.data.types.Stats;

import DaD.Commons.Collections.MultiValueSet;

/**
 * Created by Clovis on 09/03/2017.
 * Class used to calculate stats.
 * This can represent either a basic stats
 * or a modifier to a a stat.
 */
public class Env
{
	private StatType _type;
	private Stats _stat;
	private int _order;
	private double _value;

	/**
	 * Constructor of class.
	 * @param bonus value of the bonus
	 * @param type type of the bonus
	 * @param stat Stat of the bonus
	 */
	public Env(double bonus, StatType type, Stats stat){
		_value = bonus;
		_type = type;
		_stat = stat;
		_order = getOrderByStatType(type);
	}

	/**
	 * Constructor of class.
	 * @param bonus value of the bonus
	 * @param type type of the bonus
	 * @param stat stat concerning of the bonus
	 * @param order order of the stat
	 */
	public Env(double bonus, StatType type, Stats stat, int order){
		_value = bonus;
		_type = type;
		_stat = stat;
		_order = order;
	}

	/**
	 * Constructor of class.
	 * @param envInformation MultiValueSet containing all information
	 */
	public Env(MultiValueSet envInformation){
		_value = envInformation.getDouble("bonus");
		_type = (StatType)envInformation.getEnum("type",StatType.class);
		_order = envInformation.getInteger("order");
		_stat = (Stats)envInformation.getEnum("stat",Stats.class);
	}

	/**
	 * Return "base" + or / or * or -
	 * {@link #_value} depending on {@link #_type}.
	 * @param base the base value you want to affect with the Env
	 * @return double
	 */
	public double calcStat(double base){
		switch (_type){
			case SET:
				return _value;
			case MULTIPLY:
				return base * _value;
			case DIVIDE:
				return base / _value;
			case ADD:
				return base + _value;
			case SUBTRACT:
				return base - _value;
		}
		return base;
	}

	/**
	 * Call {@link #calcStat(double)} X times.
	 * @param base base value to affect with the Env
	 * @param iteration number of time you call the function.
	 * @return double
	 */
	public double calcStat(double base, int iteration){
		for(int i = 0; i < iteration; i++)
			base = calcStat(base);
		return base;
	}
	
	// Stat
	/**
	 * Return {@link #_stat}.
	 * @return Stats
	 */
	public Stats getStat() {
		return _stat;
	}

	// Order
	/**
	 * Return order of Env.
	 * @return int
	 */
	public int getOrder(){
		return _order;
	}
	/**
	 * Set the value of order.
	 * @param order value to set
	 */
	public void setOrder(int order){
		_order = order;
	}
	/**
	 * Depending on the StatType, return an int
	 * representing the order of the stat.
	 * This should be fixed and never change.
	 * @param statType Type of stat
	 * @return int
	 */
	public int getOrderByStatType(StatType statType){
		switch(statType){
			case SET:
				return 10;
			case ADD:
				return 20;
			case SUBTRACT:
				return 20;
			case MULTIPLY:
				return 30;
			case DIVIDE:
				return 30;
			default: // Should not get in here, value "NONE" should not be used
				return -1;
		}
	}

	// Value
	/**
	 * Return value of the Env.
	 * @return double
	 */
	public double getValue(){
		return _value;
	}
	/**
	 * Set the value of the env.
	 * @param value value to set.
	 */
	public void setValue(double value){
		_value = value;
	}

	/**
	 * Return a string representing all
	 * attributes and their values.
	 */
	@Override
	public String toString(){
		return "type: " + _type + "\n" +
				"stat: " + _stat + "\n" +
				"order: " + _order + "\n" +
				"value: " + _value;
	}

	/**
	 * Display all attributes in a human
	 * readable form.
	 */
	public void displayEnv(){
		System.out.println(_stat.toString() + " " + _type.toString() + " " + _value);
	}
}
