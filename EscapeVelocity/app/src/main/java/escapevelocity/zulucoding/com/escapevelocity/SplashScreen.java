package escapevelocity.zulucoding.com.escapevelocity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends ActionBarActivity {

    ConnectionDetector mConnectionDetector;
    Functions mFunctions;
    public Boolean isConnected = false;
    private static int SPLASH_TIME_OUT = 4000;
    public static boolean reg_form = AppContent.registration_form_active;
    public static boolean offline_app = AppContent.offline_app;
    public int user_reg = 0;
    String tag = "appState";
    ImageView ic_logo;
    Animation fade_in;


    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Content View
        setContentView(R.layout.activity_splash_screen);

        // Defining Logo
        ic_logo = (ImageView)findViewById(R.id.splash_ic_logo);
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        ic_logo.startAnimation(fade_in);

        // Declaring mConnection Detector
        mConnectionDetector = new ConnectionDetector(getApplicationContext());

        // Declaring mFunction
        mFunctions = new Functions(getApplicationContext());

        // Check preferenses
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        user_reg = pref.getInt("valid", 0);

        // Post SplashScreen Time Out
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                // Check Internet, User and Form State
                isConnected = mConnectionDetector.isConnectingToInternet();

                if (offline_app) {
                    Log.d(tag, "This is an offline app");
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else if(!isConnected){
                    Log.d(tag,"This is an online: cannot connect");
                    Intent i = new Intent(SplashScreen.this, ErrorActivity.class);
                    startActivity(i);
                    finish();
                } else if(user_reg == 1 || !reg_form) {
                    Log.d(tag,"User is reg or Reg is not active");
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else if(user_reg == 0){
                    Log.d(tag,"User is not reg");
                    Intent i = new Intent(SplashScreen.this, Registration.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash_screen, menu);
        return true;
    }

    public void onStart(){
        super.onStart();
    }

    public void onRestart(){
        super.onRestart();
    }

    public void onResume(){
        super.onResume();
    }

    public void onPause(){
        super.onPause();
    }

    public void onStop(){
        super.onStop();
    }

    public void onDestroy(){
        super.onDestroy();
    }

}