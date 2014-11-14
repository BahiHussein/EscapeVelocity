package escapevelocity.zulucoding.com.escapevelocity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

public class Registration extends ActionBarActivity {

    // UI
    protected Spinner reg_mSpinner_gender;
    protected EditText reg_mEditText_name;
    protected EditText reg_mEditText_email;
    protected EditText reg_mEditText_mobile;
    protected Button reg_mButton_submit;
    protected WebView reg_mWebView;
    protected ProgressBar reg_mProgressBar;
    protected ImageView reg_ic_logo;

    // Object and Classes
    protected Functions mFunctions;
    protected WebViewSettings mWebViewSettings;

    // Imported Values
    int username_minimum_character_length = AppContent.username_minimum_character_length;
    int username_maximum_character_length = AppContent.username_maximum_character_length;
    int user_mobile_minimum_character_length = AppContent.user_mobile_miniumum_number_length;
    int user_mobile_maximum_character_length = AppContent.user_mobile_maximum_number_length;
    String invalid_email_error_message = AppContent.invalid_email_error_message;
    String invalid_mobile_number_error_message = AppContent.invalid_mobile_number_error_message;
    String username_is_too_short_error_message = AppContent.username_is_too_short_error_message;
    String username_is_too_long_error_message = AppContent.username_is_too_long_error_message;
    String username_should_be_letters_only_error_message = AppContent.username_should_be_letters_only_error_message;
    String connection_error_message = AppContent.connection_error_message;
    String loading_message = AppContent.loading_message;
    String php_url = AppContent.php_url;
    String admin_name = AppContent.admin_name;

    // Local Variables
    protected String[] gender_array;
    int index;
    Animation fade_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Declearing mFunctions
        mFunctions = new Functions(getApplicationContext());

        // Declaring mWebView Settings
        mWebViewSettings = new WebViewSettings(getApplicationContext());

        // UI
        gender_array = getResources().getStringArray(R.array.Gender);
        reg_mSpinner_gender = (Spinner)findViewById(R.id.reg_mSpinner_gender);
        reg_mEditText_name = (EditText)findViewById(R.id.reg_mEditText_name);
        reg_mEditText_email = (EditText)findViewById(R.id.reg_mEditText_email);
        reg_mEditText_mobile = (EditText)findViewById(R.id.reg_mEditText_mobile);
        reg_mButton_submit = (Button)findViewById(R.id.reg_mButton_submit);
        reg_mWebView = (WebView)findViewById(R.id.reg_mWebView);
        reg_ic_logo = (ImageView)findViewById(R.id.reg_ic_logo);
        reg_mProgressBar = (ProgressBar)findViewById(R.id.reg_mProgressBar);

        // Forced UI
        forcedUI();

        // Animation UI
        animationUI();

        // Hiding ProgressBar
        reg_mProgressBar.setVisibility(View.INVISIBLE);

        // Declearing TinyWebView Options
        mWebViewSettings.mWebViewSettings(reg_mWebView);
        reg_mWebView.setWebViewClient(new mWebViewClient());

        // TinyWebView
        reg_mWebView.loadUrl("bahi");

        // Adapting Array String
        spinnerAdapter();

        // Submit Action
        submit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registration, menu);
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

    public void spinnerAdapter(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.drawable.spinner_item, gender_array);
        reg_mSpinner_gender.setAdapter(adapter);
        reg_mSpinner_gender.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                index = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    // Username Validation 
    public Boolean regFormUsername(String username){
        if (username.length() < username_minimum_character_length){
            mFunctions.toast(username_is_too_short_error_message);
        } else if (username.length() > username_maximum_character_length){
            mFunctions.toast(username_is_too_long_error_message);
        } else if (username.matches(".*\\d.*")){
            mFunctions.toast(username_should_be_letters_only_error_message);
        } else {
            return true;
        }
        return false;
    }

    // Email Validation
    public boolean regFormEmail(String user_email){
        if(!user_email.contains("@") || !user_email.contains(".")){
            mFunctions.toast(invalid_email_error_message);
        } else {
            return true;
        }
        return false;
    }

    // Mobile Validation
    public Boolean regFormMobile(String user_mobile){
        if(user_mobile.length() < user_mobile_minimum_character_length){
            mFunctions.toast(invalid_mobile_number_error_message);
        } else if (user_mobile.length() > user_mobile_maximum_character_length){
            mFunctions.toast(invalid_mobile_number_error_message);
        } else {
            return true;
        }
        return false;
    }

    // Submit
    public void submit(){
        reg_mButton_submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(regFormUsername(reg_mEditText_name.getText().toString()) == true &&
                        regFormEmail(reg_mEditText_email.getText().toString()) == true &&
                        regFormMobile(reg_mEditText_mobile.getText().toString()) == true){

                    // Change Button Text To Loading Message
                    reg_mButton_submit.setText(loading_message);

                    // Show Progress Bar
                    reg_mProgressBar.setVisibility(View.VISIBLE);

                    // Retrieving Adapter result
                    spinnerAdapter();

                    // Posting Data to server
                    postData(reg_mEditText_name.getText().toString(), reg_mEditText_email.getText().toString(), reg_mEditText_mobile.getText().toString(), gender_array[index], mFunctions.getDeviceName());
                }
            }
        });
    }

    // Post Data
    public void postData(String value1, String value2, String value3, String value4, String value5) {
        reg_mWebView.loadUrl(php_url + "?" + "id_name=" + value1 + "&" + "id_email=" + value2 + "&" + "id_mobile=" + value3 + "&" + "id_gender=" + value4 + "&" + "id_device=" + value5);
    }

    // mWebViewClient
    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            reg_mWebView.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        // On Page Finished
        @Override
        public void onPageFinished(WebView view, String url) {

            //Hide ProgressBar
            reg_mProgressBar.setVisibility(View.INVISIBLE);

            // Check If Url Exists
            if (reg_mWebView.getUrl() != null){

                //Checking Results
                if(reg_mWebView.getUrl().toString().contains("08")){

                    mFunctions.toast("Invalid input, try again");

                } else if(reg_mWebView.getUrl().toString().contains("123")){

                    if(!reg_mEditText_name.getText().toString().contains(admin_name)){

                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        Editor editor = pref.edit();
                        editor.putInt("valid", 1);
                        editor.commit();
                        //Go To WebView
                        Intent i = new Intent(Registration.this, MainActivity.class);
                        startActivity(i);
                        finish();

                    }else {
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                        Editor editor = pref.edit();
                        editor.putInt("valid", 0);
                        editor.commit();
                        //Go To WebView
                        Intent i = new Intent(Registration.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }
        }
    }

    // Forced UI
    public void forcedUI(){
        mFunctions.hideHint(reg_mEditText_name);
        mFunctions.hideHint(reg_mEditText_email);
        mFunctions.hideHint(reg_mEditText_mobile);
    }

    public void animationUI(){
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        reg_ic_logo.startAnimation(fade_in);
        reg_mSpinner_gender.setAnimation(fade_in);
        reg_mEditText_name.setAnimation(fade_in);
        reg_mEditText_email.setAnimation(fade_in);
        reg_mEditText_mobile.setAnimation(fade_in);
        reg_mButton_submit.setAnimation(fade_in);
    }

}