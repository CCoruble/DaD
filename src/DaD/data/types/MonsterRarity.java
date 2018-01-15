package DaD.data.types;

import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;

/**
 * Created by Clovis on 08/02/2017.
 */
public enum MonsterRarity
{
	COMMON(1,1,1,1,1,1),
	UNCOMMON(1.2,1,1.2,1,1.3,1),
	RARE(1.4,1.3,1,1.2,1.2,1.6),
	EPIC(1.8,1.7,1.6,1.5,2,1.5),
	LEGENDARY(2.5,2.5,2.5,2.5,3,4);

	private final Env _attackModifier;
	private final Env _defenseModifier;
	private final Env _hpMaxModifier;
	private final Env _mpMaxModifier; // This will be used later
	private final Env _experienceModifier;
	private final Env _goldModifier;

	public static final MonsterRarity[] VALUES = values();

	MonsterRarity(double attackMod, double defenseMod, double hpMaxMod, double mpMaxMod, double experienceMod, double goldMod){
		_attackModifier = new Env(attackMod, StatType.MULTIPLY);
		_defenseModifier = new Env(defenseMod, StatType.MULTIPLY);
		_hpMaxModifier = new Env(hpMaxMod, StatType.MULTIPLY);
		_mpMaxModifier = new Env(mpMaxMod, StatType.MULTIPLY);
		_experienceModifier = new Env(experienceMod, StatType.MULTIPLY);
		_goldModifier = new Env(goldMod, StatType.MULTIPLY);
	}

	public Env getAttackModifier(){
		return _attackModifier;
	}
	public Env getDefenseModifier(){
		return _defenseModifier;
	}
	public Env getHpMaxModifier(){
		return _hpMaxModifier;
	}
	public Env getMpMaxModifier(){
		return _mpMaxModifier;
	}
	public Env getExperienceModifier(){
		return _experienceModifier;
	}
	public Env getGoldModifier(){
		return _goldModifier;
	}
}
