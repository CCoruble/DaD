package DaD.data.types.Stats;

/**
 * Created by Clovis on 30/09/2017.
 * Represent the stat an {@link Env} is
 * modifying.
 */
public enum Stats
{
	ATTACK,
	DEFENSE,
	HP,
	HP_MAX,
	MP,
	MP_MAX,
	EXPERIENCE,
	EXPERIENCE_MAX,
	CRIT_CHANCE,
	CRIT_DAMAGE,
	DODGE;

	public static final Stats[] VALUES = values();
}
