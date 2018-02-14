package DaD.Commons.Collections;

import DaD.data.types.Stats.Env;
import DaD.item.ItemDropInfo;
import DaD.Template.ItemTemplate;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class was not created by Clovis.
 * Simply used to store several types of information
 * into the same object and easily retrieve them.
 * @param <T> Object type
 */
public class MultiValueSet<T> extends HashMap<T, Object>
{
	public MultiValueSet()
	{
		super();
	}

	public MultiValueSet(int size)
	{
		super(size);
	}

	private MultiValueSet(MultiValueSet<T> set)
	{
		super(set);
	}

	public void set(T key, Object value)
	{
		put(key, value);
	}

	public void set(T key, String value)
	{
		put(key, value);
	}

	public void set(T key, boolean value)
	{
		put(key, value ? Boolean.TRUE : Boolean.FALSE);
	}

	public void set(T key, int value)
	{
		put(key, Integer.valueOf(value));
	}

	public void set(T key, int[] value)
	{
		put(key, value);
	}

	public void set(T key, long value)
	{
		put(key, Long.valueOf(value));
	}

	public void set(T key, double value)
	{
		put(key, Double.valueOf(value));
	}

	public void set(T key, Enum<?> value)
	{
		put(key, value);
	}

	public void unset(T key)
	{
		remove(key);
	}

	public boolean isSet(T key)
	{
		return get(key) != null;
	}

	@Override
	public MultiValueSet<T> clone()
	{
		return new MultiValueSet<T>(this);
	}

	public ArrayList getArrayList(T key)
	{
		Object val = get(key);

		return (ArrayList)val;
	}

	public ArrayList getArrayList(T key, ArrayList defaultValue)
	{
		Object val = get(key);

		try
		{
			return (ArrayList)val;
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}

	public Env getEnv(T key)
	{
		Object val = get(key);

		return (Env)val;
	}

	public Env getEnv(T key, Env defaultValue)
	{
		Object val = get(key);

		try
		{
			return (Env)val;
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}

	public ItemTemplate getItemTemplate(T key)
	{
		Object val = get(key);

		return (ItemTemplate)val;
	}

	public ItemTemplate getItemTemplate(T key, ItemTemplate defaultValue)
	{
		Object val = get(key);

		try
		{
			return (ItemTemplate)val;
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}

	public ItemDropInfo getItemDropInfo(T key)
	{
		Object val = get(key);

		return (ItemDropInfo)val;
	}

	public ItemDropInfo getItemDropInfo(T key, ItemDropInfo defaultValue)
	{
		Object val = get(key);

		try
		{
			return (ItemDropInfo)val;
		}
		catch(Exception e)
		{
			return defaultValue;
		}
	}

	public boolean getBool(T key)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).intValue() != 0;
		if(val instanceof String)
			return Boolean.parseBoolean((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue();

		throw new IllegalArgumentException("Boolean value required, but found: " + val + "!");
	}

	public boolean getBool(T key, boolean defaultValue)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).intValue() != 0;
		if(val instanceof String)
			return Boolean.parseBoolean((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue();

		return defaultValue;
	}

	public int getInteger(T key)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).intValue();
		if(val instanceof String)
			return (int) Double.parseDouble((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue() ? 1 : 0;

		throw new IllegalArgumentException("Integer value required, but found: " + val + "!");
	}

	public int getInteger(T key, int defaultValue)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).intValue();
		if(val instanceof String)
			return (int) Double.parseDouble((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue() ? 1 : 0;

		return defaultValue;
	}

	public int[] getIntegerArray(T key)
	{
		return getIntegerArray(key, ";");
	}

	private int[] getIntegerArray(T key, String separator)
	{
		Object val = get(key);

		if(val instanceof int[])
			return (int[]) val;
		if(val instanceof Number)
			return new int[] { ((Number) val).intValue() };
		if(val instanceof String)
		{
			String[] vals = ((String) val).split(separator);

			int[] result = new int[vals.length];

			int i = 0;
			for(String v : vals)
				result[i++] = (int) Double.parseDouble(v);

			return result;
		}

		throw new IllegalArgumentException("Integer array required, but found: " + val + "!");
	}

	public int[] getIntegerArray(T key, int[] defaultArray)
	{
		return getIntegerArray(key, defaultArray, ";");
	}

	private int[] getIntegerArray(T key, int[] defaultArray, String separator)
	{
		try
		{
			return getIntegerArray(key, separator);
		}
		catch(IllegalArgumentException e)
		{
			return defaultArray;
		}
	}

	public long getLong(T key)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).longValue();
		if(val instanceof String)
			return Long.parseLong((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue() ? 1L : 0L;

		throw new IllegalArgumentException("Long value required, but found: " + val + "!");
	}

	public long getLong(T key, long defaultValue)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).longValue();
		if(val instanceof String)
			return Long.parseLong((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue() ? 1L : 0L;

		return defaultValue;
	}

	public long[] getLongArray(T key)
	{
		return getLongArray(key, ";");
	}

	private long[] getLongArray(T key, String separator)
	{
		Object val = get(key);

		if(val instanceof long[])
			return (long[]) val;
		if(val instanceof Number)
			return new long[] { ((Number) val).longValue() };
		if(val instanceof String)
		{
			String[] vals = ((String) val).split(separator);

			long[] result = new long[vals.length];

			int i = 0;
			for(String v : vals)
				result[i++] = (int) Double.parseDouble(v);

			return result;
		}

		throw new IllegalArgumentException("Integer array required, but found: " + val + "!");
	}

	public double getDouble(T key)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).doubleValue();
		if(val instanceof String)
			return Double.parseDouble((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue() ? 1. : 0.;

		throw new IllegalArgumentException("Double value required, but found: " + val + "!");
	}

	public double getDouble(T key, double defaultValue)
	{
		Object val = get(key);

		if(val instanceof Number)
			return ((Number) val).doubleValue();
		if(val instanceof String)
			return Double.parseDouble((String) val);
		if(val instanceof Boolean)
			return ((Boolean) val).booleanValue() ? 1. : 0.;

		return defaultValue;
	}

	public double[] getDoubleArray(T key)
	{
		return getDoubleArray(key, ";");
	}

	private double[] getDoubleArray(T key, String separator)
	{
		Object val = get(key);

		if(separator.equals("."))
			throw new IllegalArgumentException("Illegal separator symbol for double array!");

		if(val instanceof double[])
			return (double[]) val;
		if(val instanceof Number)
			return new double[] { ((Number) val).doubleValue() };
		if(val instanceof String)
		{
			String[] vals = ((String) val).split(separator);

			double[] result = new double[vals.length];

			int i = 0;
			for(String v : vals)
				result[i++] = (int) Double.parseDouble(v);

			return result;
		}

		throw new IllegalArgumentException("Double array required, but found: " + val + "!");
	}

	public double[] getDoubleArray(T key, double[] defaultArray)
	{
		return getDoubleArray(key, defaultArray, ";");
	}

	private double[] getDoubleArray(T key, double[] defaultArray, String separator)
	{
		try
		{
			return getDoubleArray(key, separator);
		}
		catch(IllegalArgumentException e)
		{
			return defaultArray;
		}
	}

	public String getString(T key)
	{
		Object val = get(key);

		if(val != null)
			return String.valueOf(val);

		throw new IllegalArgumentException("String value required, but not specified!");
	}

	public String getString(T key, String defaultValue)
	{
		Object val = get(key);

		if(val != null)
			return String.valueOf(val);

		return defaultValue;
	}

	public Object getObject(T key)
	{
		return get(key);
	}

	public Object getObject(T key, Object defaultValue)
	{
		Object val = get(key);

		if(val != null)
			return val;

		return defaultValue;
	}

	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> E getEnum(T name, Class<E> enumClass)
	{
		Object val = get(name);

		if(val != null && enumClass.isInstance(val))
			return (E) val;
		if(val instanceof String)
			return Enum.valueOf(enumClass, (String) val);

		throw new IllegalArgumentException("Enum value of type " + enumClass.getName() + " required, but found: " + val + "!");
	}

	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> E getEnum(T name, Class<E> enumClass, E defaultValue)
	{
		Object val = get(name);

		if(val != null && enumClass.isInstance(val))
			return (E) val;
		if(val instanceof String)
			return Enum.valueOf(enumClass, (String) val);

		return defaultValue;
	}
}
