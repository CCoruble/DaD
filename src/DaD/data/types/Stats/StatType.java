package DaD.data.types.Stats;

/**
 * Created by Clovis on 09/03/2017.
 * Represent the type of stat of an {@link Env}.
 */
public enum StatType
{
	NONE,
	SET,
	MULTIPLY,
	DIVIDE,
	ADD,
	SUBTRACT;

	@Override
	public String toString() {
		switch(this){
			case SET:
				return "=";
			case ADD:
				return "+";
			case SUBTRACT:
				return "-";
			case MULTIPLY:
				return "*";
			case DIVIDE:
				return "/";
			default: // Should not get here as value "NONE" should not get used
				return "";
		}
	}
}
