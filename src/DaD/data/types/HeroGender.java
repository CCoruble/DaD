package DaD.data.types;

import DaD.commons.MultiValueSet;
import DaD.data.types.Stats.Env;
import DaD.data.types.Stats.StatType;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Clovis on 07/02/2017.
 * Represent the gender of hero.
 */
public enum HeroGender
{
	MALE,
	FEMALE;

	private Env _attackModifier;
	private Env _defenseModifier;
	private Env _hpMaxModifier;
	private Env _mpMaxModifier;
	private Env _experienceModifier;
	private Env _experienceMaxModifier;

	/**
	 * Array containing all enum values.
	 */
	public static final HeroGender[] VALUES = values();

	/**
	 * Called by {@link DaD.loader.GenderLoader#encodeHeroGender(Node)}
	 * and adjust each gender buff.
	 * @param raceInformation MultiValueSet containing all buff information
	 */
	public void adjustGenderInformation(MultiValueSet raceInformation){
		_attackModifier	= raceInformation.getEnv("attackModifier");
		_defenseModifier	= raceInformation.getEnv("defenseModifier");
		_hpMaxModifier	= raceInformation.getEnv("hpMaxModifier");
		_mpMaxModifier	= raceInformation.getEnv("mpMaxModifier");
		_experienceModifier	= raceInformation.getEnv("experienceModifier");
		_experienceMaxModifier	= raceInformation.getEnv("experienceMaxModifier");
	}

	/**
	 * Return attack modifier.
	 * @return Env
	 */
	public Env getAttackModifier(){
		return _attackModifier;
	}
	/**
	 * Return defense modifier.
	 * @return Env
	 */
	public Env getDefenseModifier(){
		return _defenseModifier;
	}
	/**
	 * Return hp max modifier.
	 * @return Env
	 */
	public Env getHpMaxModifier(){
		return _hpMaxModifier;
	}
	/**
	 * Return mp max modifier.
	 * @return Env
	 */
	public Env getMpMaxModifier(){
		return _mpMaxModifier;
	}
	/**
	 * Return experience modifier.
	 * @return Env
	 */
	public Env getExperienceModifier(){
		return _experienceModifier;
	}
	/**
	 * Return experience max modifier.
	 * @return Env
	 */
	public Env getExperienceMaxModifier(){
		return _experienceMaxModifier;
	}

	/**
	 * Return all env in an ArrayList.
	 * @return ArrayList
	 */
	public ArrayList<Env> getAllEnv() {
		ArrayList<Env> allEnv = new ArrayList<>();
		allEnv.add(_attackModifier);
		allEnv.add(_defenseModifier);
		allEnv.add(_hpMaxModifier);
		allEnv.add(_mpMaxModifier);
		allEnv.add(_experienceModifier);
		allEnv.add(_experienceMaxModifier);

		return allEnv;
	}
}
