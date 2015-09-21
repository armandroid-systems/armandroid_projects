package armandroid.com.mx.dao;

import android.content.Context;
import android.util.Log;

import java.io.FileWriter;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import recibos.DaoSession;
import recibos.Tareas;
import recibos.TareasDao;

/**
 * Created by zadtanikus on 26/06/15.
 */
public class BoussinesApp extends ConnectionFactory{
    private static final String TAG = BoussinesApp.class.getName();

    private DaoSession daoSession = daoMaster.newSession();

    public BoussinesApp(Context contexto){
        super(contexto);
    }

    /**
     * INSERTS
     */
    public boolean insertRecibo(Tareas tareas){
        Log.d(TAG,"RECORD A NEW REGISTER...");
        boolean flag = false;

        TareasDao daoTransaction = daoSession.getTareasDao();
        try{
            daoTransaction.insertOrReplaceInTx(tareas);
            flag = true;
            Log.d(TAG,"DATA RECORDED SUCCESFULLY...");
        }catch(Exception e){
            Log.e(TAG,"ERROR RECORDING DATA "+e);
        }
         return flag;
    }
    /**
     * QUERIES
     */
    public List<Tareas> getTareasCapturadas(){
        TareasDao mTareas = daoSession.getTareasDao();
        List<Tareas> query = null;
        try{
            query = mTareas.loadAll();
            Log.d(TAG,"QUERY EJECUTADO...");
        }catch(Exception e){
            Log.e(TAG,"ERROR ON QUERY ["+e+"]");
        }
        return query;
    }

    public long getCountReg(){
        TareasDao mTareas = daoSession.getTareasDao();
        long count = 0;
        try{
            count = mTareas.count();
            Log.d(TAG,"QUERY EJECUTADO...");
        }catch(Exception e){
            Log.e(TAG,"ERROR ON QUERY ["+e+"]");
        }
        return count;
    }
    /**
     * ESPECIAL
     */

    public void exportDataRecibos(String file) throws Exception{

        List<Tareas> listaReporte = getTareasCapturadas();
        if(listaReporte.size() > 0){
            Log.d(TAG,"GENERANDO ARCHIVO...");
            CSVWriter writerCSV = new CSVWriter (new FileWriter(file),',',CSVWriter.NO_QUOTE_CHARACTER);


            Log.d(TAG, "LIST SIZE: ["+listaReporte.size()+"]");

            for(int k = 0;  k < listaReporte.size(); k++){

                StringBuilder sb = new StringBuilder();

                sb.append(listaReporte.get(k).getSnr()).append(",");
                sb.append(listaReporte.get(k).getLongitud()).append(",");
                sb.append(listaReporte.get(k).getLatitud()).append(",");
                sb.append(listaReporte.get(k).getFechahora()).append(",");
                sb.append(listaReporte.get(k).getBcRecibo()).append(",");
                sb.append(listaReporte.get(k).getAtendido()).append(",");


                String [] entrada = sb.toString().split(",");
                writerCSV.writeNext(entrada);
            }
            writerCSV.close();
            //writerCSV.
        }


    }

    /**
     * UPDATES
     *
     */

    /**
     * DELETES
     */
}
