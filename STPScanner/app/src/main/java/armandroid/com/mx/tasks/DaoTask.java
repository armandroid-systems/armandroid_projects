package armandroid.com.mx.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.Objects;

import armandroid.com.mx.dao.BoussinesApp;
import armandroid.com.mx.interfaces.DataPass;
import armandroid.com.mx.utils.Constants;
import recibos.Tareas;

/**
 * Created by zadtanikus on 26/06/15.
 */
public class DaoTask extends AsyncTask<Object,Void,Object>{
    private final static String TAG = DaoTask.class.getName();

    private int action;
    private ProgressDialog dialogProgress;
    private Context contexto;
    private DataPass delegate;

    public DaoTask(Context mContext, DataPass delegate,int action){
        this.action = action;
        this.contexto = mContext;
        this.delegate = delegate;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialogProgress = new ProgressDialog(this.contexto);
        dialogProgress.setMessage("Conectando con base de datos...");
        dialogProgress.setCancelable(false);
        dialogProgress.setIndeterminate(true);
        dialogProgress.show();
    }

    @Override
    protected Object doInBackground(Object... params) {
        Object respuesta = new Object();
        switch(this.action){
            case Constants.RECORD:
                Log.d(TAG,"PREPARE TO WRITE..."+((Tareas)params[0]).toString());
                /*Tareas tarea = */
                respuesta = new BoussinesApp(this.contexto).insertRecibo((Tareas)params[0]);
                break;
            case Constants.UPDATE:

                try {
                    Log.d(TAG,(String)params[0]);
                    new BoussinesApp(this.contexto).exportDataRecibos((String)params[0]);
                    respuesta = 1;
                } catch (Exception e) {
                    Log.e(TAG,"FATAL ERROR ON WRITE FILE..."+e);
                    e.printStackTrace();
                    respuesta = 0;
                }
                break;
            case Constants.QUERY:
                new BoussinesApp(this.contexto).getCountReg();
                break;
            default:
                break;

        }
        return respuesta;
    }

    @Override
    protected void onPostExecute(Object o) {

        delegate.getData(o,null);
        try{
            dialogProgress.dismiss();
        }catch(Exception e){
            Log.e(TAG,"ERROR ON DISMIS "+e);
        }
        super.onPostExecute(o);




    }
}
