package DaD.data.types;

import DaD.Commons.Collections.MultiValueSet;
import DaD.data.types.Stats.Env;
import org.w3c.dom.Node;

import java.util.ArrayList;

/**
 * Created by Clovis on 07/02/2017.
 * Represent the race of the hero.
 * @see Env
 * @see DaD.loader.HeroRaceLoader
 */
public enum HeroRace
{
		HUMAN,
		DWARF,
		ELF;

		private Env _attackModifier;
		private Env _defenseModifier;
		private Env _hpMaxModifier;
		private Env _mpMaxModifier;
		private Env _experienceModifier;
		private Env _experienceMaxModifier;

	/**
	 * Array containing all enum values.
	 */
	public static final HeroRace[] VALUES = values();

	/**
	 * Called by {@link DaD.loader.HeroRaceLoader#encodeHeroRace(Node)}
	 * and adjust each race buff.
	 * @param raceInformation MultiValueSet containing all buff information
	 */
	public void adjustRaceInformation(MultiValueSet raceInformation){
		_attackModifier	= raceInformation.getEnv("attackModifier");
		_defenseModifier = raceInformation.getEnv("defenseModifier");
		_hpMaxModifier	= raceInformation.getEnv("hpMaxModifier");
		_mpMaxModifier	= raceInformation.getEnv("mpMaxModifier");
		_experienceModifier	= raceInformation.getEnv("experienceModifier");
		_experienceMaxModifier	= raceInformation.getEnv("experienceMaxModifier");
	}

	/**
	 * Return the attack modifier.
	 * @return Env
	 */
	public Env getAttackModifier(){
		return _attackModifier;
	}
	/**
	 * Return the defense modifier.
	 * @return Env
	 */
	public Env getDefenseModifier(){
		return _defenseModifier;
	}
	/**
	 * Return the hp max modifier.
	 * @return Env
	 */
	public Env getHpMaxModifier(){
		return _hpMaxModifier;
	}
	/**
	 * Return the mp max modifier.
	 * @return Env
	 */
	public Env getMpMaxModifier(){
		return _mpMaxModifier;
	}
	/**
	 * Return the experience modifier.
	 * @return Env
	 */
	public Env getExperienceModifier(){
		return _experienceModifier;
	}
	/**
	 * Return the experience max modifier.
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
