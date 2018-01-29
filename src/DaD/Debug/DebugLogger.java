package DaD.Debug;

import DaD.manager.GameManager;

/**
 * Used to display logs only on
 * debug mode.
 */
public class DebugLogger {
    /**
     * Display log if application is
     * started on debug mode.
     * @param log log to print.
     */
    public static void log(String log){
        if(GameManager.getInstance().isDebug)
            System.out.println(log);
    }

    /**
     * Display exception stack trace if application is
     * started on debug mode.
     * @param e exception to print stack trace
     */
    public static void log(Exception e){
        if(GameManager.getInstance().isDebug)
            e.printStackTrace();
    }
}
