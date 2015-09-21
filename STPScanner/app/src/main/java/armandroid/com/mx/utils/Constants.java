package armandroid.com.mx.utils;

/**
 * Created by zadtanikus on 26/06/15.
 */
public class Constants {
    //TODO TIME CONSTANTS
    public static final int DELAY = 3000;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    //TODO ACTION CONSTANTS
    public static final int ACTION_SCANFORM = 1;
    public static final int ACTION_EXPORT   = 2;
    public static final int ACTION_SCAN     = 3;

    // Keys for storing activity state in the Bundle.
    public final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    public final static String LOCATION_KEY = "location-key";
    public final static String RESULT_SCAN = "result-scanner";
    public final static String OBJECT_TO_RECORD = "object-tareas";

    public final static int RECORD = 1;
    public final static int UPDATE = 2;
    public final static int QUERY = 3;

    public static final String  REPORT_SUFIX		= ".txt";


}
