package DaD.generator;

/**
 * Created by Clovis on 08/02/2017.
 */
public class DungeonGenerator
{
	private static final DungeonGenerator _instance = new DungeonGenerator();

	private DungeonGenerator(){
		// Let this empty
	}

	public static final DungeonGenerator getInstance()
	{
		return _instance;
	}

}