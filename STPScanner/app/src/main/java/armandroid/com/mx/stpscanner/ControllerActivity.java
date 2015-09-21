package armandroid.com.mx.stpscanner;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import armandroid.com.mx.utils.Constants;
import armandroid.com.mx.utils.HandlerTextClic;


public class ControllerActivity extends Activity {

    private static final String TAG = ControllerActivity.class.getName();

    private TextView textButtonScan;
    private TextView textButtonExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        textButtonScan = (TextView) findViewById(R.id.textViewScan);
        textButtonExport = (TextView) findViewById(R.id.textViewExport);

        textButtonScan.setOnClickListener(new HandlerTextClic(this,ControllerActivity.this, Constants.ACTION_SCANFORM,null));
        textButtonExport.setOnClickListener(new HandlerTextClic(this, ControllerActivity.this, Constants.ACTION_EXPORT,null));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
