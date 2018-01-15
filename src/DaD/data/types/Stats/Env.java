package DaD.data.types.Stats;

import DaD.commons.MultiValueSet;

/**
 * Created by Clovis on 09/03/2017.
 */
public class Env
{
	private StatType _type;
	private Stats _stat;
	private int _order;
	private double _value;

	public Env(double bonus, StatType type){
		_value = bonus;
		_type = type;
	}

	public Env(double bonus, StatType type, Stats stat, int order){
		_value = bonus;
		_type = type;
		_stat = stat;
		_order = order;
	}

	public Env(MultiValueSet envInformation){
		_value = envInformation.getDouble("bonus");
		_type = (StatType)envInformation.getEnum("type",StatType.class);
		_order = envInformation.getInteger("order");
		_stat = (Stats)envInformation.getEnum("stat",Stats.class);
	}

	public void setStatsType(StatType type){
		_type = type;
	}

	public double calcStat(int base){
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
			default : // NONE
				return base - _value;
		}
	}
	public int calcStat(int base, int iteration){
		double baseD = base;
		for(int i = 0; i < iteration; i++)
			baseD = calcStat(baseD);
		return (int)baseD;
	}

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
	public double calcStat(double base, int iteration){
		for(int i = 0; i < iteration; i++)
			base = calcStat(base);
		return base;
	}

	// Stat
	public Stats getStat()
	{
		return _stat;
	}

	// Order
	public int getOrder(){
		return _order;
	}
	public void setOrder(int order){
		_order = order;
	}

	// Value
	public double getValue(){
		return _value;
	}
	public void setValue(double value){
		_value = value;
	}

	@Override
	public String toString(){
		return "type: " + _type + "\n" +
				"stat: " + _stat + "\n" +
				"order: " + _order + "\n" +
				"value: " + _value;
	}
}
