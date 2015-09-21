package armandroid.com.mx.stpscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import armandroid.com.mx.utils.Constants;


public class SplashActivity extends Activity {
    private static final String TAG = SplashActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        waitAndGoAhead();

    }

    public void waitAndGoAhead(){
        Log.d(TAG, "NO HAY CREDENCIAL VALIDA REDIRECCCIONANDO A LOGIN...");
        TimerTask delaySlash = new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG,"GO TO SCANNER");
                Intent mIntent = new Intent(SplashActivity.this,ControllerActivity.class);
                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(delaySlash, Constants.DELAY);
    }

}
