package armandroid.com.mx.stpscanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import armandroid.com.mx.interfaces.DataPass;
import armandroid.com.mx.tasks.DaoTask;
import armandroid.com.mx.utils.Constants;
import armandroid.com.mx.utils.HandlerTextClic;
import armandroid.com.mx.utils.Utils;
import jim.h.common.android.zxinglib.Intents;
import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;
import recibos.Tareas;

public class ScanActivity extends Activity implements ConnectionCallbacks,OnConnectionFailedListener,LocationListener{
    private static final String TAG = ScanActivity.class.getName();

    private Handler handler = new Handler();
    private TextView resultado;
    private DataPass mDelegate;

    protected Tareas mTarea;
    protected GoogleApiClient clienteGoolge;
    protected LocationRequest locationRequest;
    protected Location currentClocation;
    protected Boolean mRequestingLocationUpdates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_form);

        resultado = (TextView)findViewById(R.id.textViewNumeroContrato);
        Button botonEscanear = (Button) findViewById(R.id.buttonScan);
        Button botonGuardar = (Button) findViewById(R.id.buttonSave);

        botonEscanear.setOnClickListener(new HandlerTextClic(this,ScanActivity.this,Constants.ACTION_SCAN,null));
        botonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,currentClocation.toString());
                if(!resultado.getText().toString().isEmpty()){
                    Tareas mTarea = new Tareas();
                    mTarea.setSnr(Utils.getSNR(Utils.getImei(ScanActivity.this)));
                    mTarea.setBcRecibo(resultado.getText().toString());
                    mTarea.setFechahora(Utils.getdayDateTime());
                    String[] res = Utils.convertdToDm(currentClocation).split(",");
                    mTarea.setLatitud(res[1]);
                    mTarea.setLongitud(res[0]);
                    mTarea.setAtendido("1");

                    mDelegate = new DataPass() {
                        @Override
                        public void getData(Object param, List<Object> params) {
                            if ((Boolean) param) {
                                Toast.makeText(ScanActivity.this, "Registro guardado con éxito.", Toast.LENGTH_LONG).show();
                            }
                        }
                    };

                    new DaoTask(ScanActivity.this, mDelegate, Constants.RECORD).execute(mTarea);
                }else{
                    Toast.makeText(ScanActivity.this, "No se ha capturado ningún valor aún", Toast.LENGTH_LONG).show();
                }

            }
        });
        mRequestingLocationUpdates = false;

        updateValuesFromBundle(savedInstanceState);

        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        Log.i(TAG, "Building GoogleApiClient");
        clienteGoolge = new GoogleApiClient.Builder(ScanActivity.this)
                .addConnectionCallbacks(ScanActivity.this)
                .addOnConnectionFailedListener(ScanActivity.this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest();

        // setting interval
        locationRequest.setInterval(Constants.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        locationRequest.setFastestInterval(Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    protected void startLocationUpdates() {
        Log.d(TAG,"INICIANDO PETICION");
        LocationServices.FusedLocationApi.requestLocationUpdates(
                clienteGoolge, locationRequest, this);
    }

    protected void stopLocationUpdates() {
        // It is a good practice to remove location requests when the activity is in a paused or
        // stopped state. Doing so helps battery performance and is especially
        // recommended in applications that request frequent location updates.
        LocationServices.FusedLocationApi.removeLocationUpdates(clienteGoolge, this);
    }

    //TODO update bundle
    private void updateValuesFromBundle(Bundle savedInstanceState) {
        Log.i(TAG, "Updating values from bundle");
        if (savedInstanceState != null) {
                Log.d(TAG,"PODER BUNDLE...");
            if (savedInstanceState.keySet().contains(Constants.REQUESTING_LOCATION_UPDATES_KEY)) {
                mRequestingLocationUpdates = savedInstanceState.getBoolean(
                        Constants.REQUESTING_LOCATION_UPDATES_KEY);

            }


            if (savedInstanceState.keySet().contains(Constants.LOCATION_KEY)) {

                currentClocation = savedInstanceState.getParcelable(Constants.LOCATION_KEY);
            }

            if(savedInstanceState.keySet().contains(Constants.RESULT_SCAN)){
                resultado.setText(savedInstanceState.getString(Constants.RESULT_SCAN));
            }
            if(savedInstanceState.keySet().contains(Constants.OBJECT_TO_RECORD)){
                mTarea = savedInstanceState.getParcelable(Constants.OBJECT_TO_RECORD);
            }
            if(currentClocation != null){
                Log.d(TAG,"LOCATION BUNDLE["+currentClocation.toString()+"]");
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                if (result != null) {
                    Log.d(TAG,"RESULTADO "+result);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            resultado.setText(result);
                            if(currentClocation == null) {

                                currentClocation = LocationServices.FusedLocationApi.getLastLocation(clienteGoolge);
                            }

                            Log.d(TAG,"LOCATION AFTER CAPTURE ["+currentClocation+"]");

                        }

                    });
                }
                Log.d(TAG,"LOCATION FUERA HILO["+currentClocation.toString()+"]");
                break;
            default:
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (currentClocation == null) {
            currentClocation = LocationServices.FusedLocationApi.getLastLocation(clienteGoolge);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        clienteGoolge.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
        currentClocation = location;
        Toast.makeText(this, "SOMETHING HAPPEND",
                Toast.LENGTH_SHORT).show();
        Log.d(TAG,"LOCATION ["+currentClocation.toString()+"]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"ONSTART...");
        clienteGoolge.connect();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"ONRESUME...");
        if (clienteGoolge.isConnected() ) /*argumento chaqueto*/{
            Log.d(TAG,"INICIANDO PETICION...");
            startLocationUpdates();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"ONPAUSE...");
        if (clienteGoolge.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        if(clienteGoolge.isConnected()){
            stopLocationUpdates();
        }
        clienteGoolge.disconnect();
        super.onStop();
    }


   public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
       Log.d(TAG,"SAVING APP");
       savedInstanceState.putBoolean(Constants.REQUESTING_LOCATION_UPDATES_KEY, mRequestingLocationUpdates);
       savedInstanceState.putParcelable(Constants.LOCATION_KEY, currentClocation);
       savedInstanceState.putString(Constants.RESULT_SCAN,resultado.getText().toString());
       savedInstanceState.putParcelable(Constants.OBJECT_TO_RECORD, mTarea);
       //savedInstanceState.putString(Constants.LAST_UPDATED_TIME_STRING_KEY, mLastUpdateTime);
       super.onSaveInstanceState(savedInstanceState);
   }
}
