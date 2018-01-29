package DaD.handler;

/**
 * Created by Clovis on 19/05/2017.
 * Class used to handle effect of differents items.
 */
public class ItemHandler
{
	/**
	 * Private instance of class.
	 */
	private static final ItemHandler _instance = new ItemHandler();

	/**
	 * Accessor for instance of class.
	 * @return ItemHandler
	 */
	public static final ItemHandler getInstance()
	{
		return _instance;
	}

	/**
	 * Private constructor of of class.
	 */
	private ItemHandler() {}
}
