package DaD.handler;

/**
 * Created by Clovis on 19/05/2017.
 */
public class ItemHandler
{
	private static final ItemHandler _instance = new ItemHandler();

	public static final ItemHandler getInstance()
	{
		return _instance;
	}

	private ItemHandler() {}
}
