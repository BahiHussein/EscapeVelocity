package escapevelocity.zulucoding.com.escapevelocity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ErrorActivity extends ActionBarActivity {

    protected ImageView ic_connection;
    protected TextView connection_textview;
    Animation fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        ic_connection = (ImageView)findViewById(R.id.connection_ic_launcher);
        connection_textview = (TextView)findViewById(R.id.connection_textview);

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        ic_connection.startAnimation(fade_in);
        connection_textview.setAnimation(fade_in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.error, menu);
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