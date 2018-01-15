package DaD.data.types;

import DaD.commons.MultiValueSet;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;

/**
 * Created by Clovis on 07/02/2017.
 */
public enum HeroGender
{
	MALE(),
	FEMALE();

	private Env _attackModifier;
	private Env _defenseModifier;
	private Env _hpMaxModifier;
	private Env _mpMaxModifier;
	private Env _experienceModifier;
	private Env _experienceMaxModifier;


	public static final HeroGender[] VALUES = values();

	HeroGender(){
		_attackModifier = new Env(1, StatType.MULTIPLY);
		_defenseModifier = new Env(1, StatType.MULTIPLY);
		_hpMaxModifier = new Env(1, StatType.MULTIPLY);
		_mpMaxModifier = new Env(1, StatType.MULTIPLY);
		_experienceModifier = new Env(1, StatType.MULTIPLY);
		_experienceMaxModifier = new Env(1, StatType.MULTIPLY);
	}

	public void adjustGenderInformation(MultiValueSet raceInformation){
		_attackModifier	= raceInformation.getEnv("attackModifier");
		_defenseModifier	= raceInformation.getEnv("defenseModifier");
		_hpMaxModifier	= raceInformation.getEnv("hpMaxModifier");
		_mpMaxModifier	= raceInformation.getEnv("mpMaxModifier");
		_experienceModifier	= raceInformation.getEnv("experienceModifier");
		_experienceMaxModifier	= raceInformation.getEnv("experienceMaxModifier");
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
	public Env getExperienceMaxModifier(){
		return _experienceMaxModifier;
	}
}
