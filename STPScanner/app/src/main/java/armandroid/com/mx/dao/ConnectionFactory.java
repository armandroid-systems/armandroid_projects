package armandroid.com.mx.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import recibos.DaoMaster;

/**
 * Created by zadtanikus on 26/06/15.
 */
public class ConnectionFactory {
    private static final String TAG = ConnectionFactory.class.getName();

    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase database;
    public static DaoMaster daoMaster;

    public ConnectionFactory(Context context){
        Log.d(TAG,"CREATING DATABASE...");
        helper = new DaoMaster.DevOpenHelper(context,"recibos",null);
        try{
            database = helper.getWritableDatabase();
        }catch(Exception ex){
            Log.e(TAG,"OCURRIO UNA EXCEPCION AL CREAR EL SCHEMA ["+ex+"]");
        }
        daoMaster = new DaoMaster(database);
        return;
    }

    public void closeDataBase(){
        if(helper != null){
            helper.close();
        }
    }
}
