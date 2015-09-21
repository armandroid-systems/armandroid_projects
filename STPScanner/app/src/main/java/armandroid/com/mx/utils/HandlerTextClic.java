package armandroid.com.mx.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import armandroid.com.mx.interfaces.DataPass;
import armandroid.com.mx.stpscanner.R;
import armandroid.com.mx.stpscanner.ScanActivity;
import armandroid.com.mx.tasks.DaoTask;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import recibos.Tareas;

/**
 * Created by zadtanikus on 26/06/15.
 */
public class HandlerTextClic implements View.OnClickListener {
    private static final String TAG = HandlerTextClic.class.getName();
    private Context mContext;
    private int mAction;
    private Activity mActivity;
    private Object[] mObject;
    public HandlerTextClic(Context context,Activity activity,int action,Object...mObject){
        this.mContext = context;
        this.mAction = action;
        this.mActivity = activity;
        this.mObject = mObject;
    }
    @Override
    public void onClick(View v) {
        switch(this.mAction) {
            case Constants.ACTION_SCANFORM:
                Intent mIntent = new Intent(this.mContext, ScanActivity.class);
                mContext.startActivity(mIntent);
                break;

            case Constants.ACTION_EXPORT:
                DataPass mDelegate = new DataPass() {
                    @Override
                    public void getData(Object param, List<Object> params) {
                        if ((Integer) param == 1) {
                            Toast.makeText(mContext, "El archivo se grabo satisfactoriamente", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(mContext, "NO FUE POSIBLE GRABAR EL ARCHIVO!", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                new DaoTask(mContext, mDelegate, Constants.UPDATE).execute(Utils.getFilePath());
                break;

            case Constants.ACTION_SCAN:
                IntentIntegrator.initiateScan(this.mActivity, R.layout.capture,
                        R.id.viewfinder_view, R.id.preview_view, true);
                break;

            default:
                break;
        }
    }
}
