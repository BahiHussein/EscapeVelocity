package escapevelocity.zulucoding.com.escapevelocity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import escapevelocity.zulucoding.com.escapevelocity.Rateme.AppRater;


public class MainActivity extends ActionBarActivity {

    // Functions
    Functions mFunctions;
    ConnectionDetector mConnectionDetector;
    WebViewSettings mWebViewSettings;

    // UI Variables
    protected WebView mWebView;
    protected ProgressBar mProgressBar;
    public Boolean fullscreen_webview = AppContent.fullscreen_webview;
    // mWebView Controls
    String appName = AppContent.application_name;
    String webUrl = AppContent.mobile_website_url;
    String shareMessage = AppContent.share_message;
    String shareUrl = AppContent.share_url;
    String contact_url = AppContent.contact_url;
    String about_url = AppContent.about_url;
    Boolean saveLastUrl = AppContent.save_url;
    Boolean rateActive = AppContent.rate_dialog_active;
    String admobBannerID = AppContent.google_play_ads_banner_id;
    String admobInterstitialID = AppContent.google_play_ads_interstitial_id;
    Boolean admobBannerActive = AppContent.google_play_ads_banner_active;
    Boolean admobInterstitialActive = AppContent.google_play_ads_interstitial_active;
    String connection_error_message = AppContent.connection_error_message;
    //Google Play Ads
    private AdView adView;
    private InterstitialAd interstitial;
    // Geo-location
    Boolean geoLocationValue = false;
    // Logs
    String tag = "state";
    // Others
    public Boolean isConnected = false;
    protected String last_link = "#null";
    protected String link_bank = "#null";
    protected int adviewShowOnce = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //===>> Logging
        Log.d(tag,"In OnCreate Event()");

        // Hiding the Action Bar for different android versions
        hideActionBar();

        // Set Content View
        setContentView(R.layout.activity_main);

        // UI
        UI();

        // Declaring mConnection Detector
        mConnectionDetector = new ConnectionDetector(getApplicationContext());

        // Declaring mFunction
        mFunctions = new Functions(getApplicationContext());

        // Declaring mWebView Settings
        mWebViewSettings = new WebViewSettings(getApplicationContext());

        // Restore LastLink
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        last_link = pref.getString("lastlink", "#null");

        // Getting WebView Ready
        mWebViewSettings.mWebViewSettings(mWebView);
        mWebView.setWebViewClient(new mWebViewClient());
        mWebView.setWebChromeClient(new mWebViewChromeClient());

        // Check Internet, User and Form State
        isConnected = mConnectionDetector.isConnectingToInternet();

        if(isConnected == false){
            Intent i = new Intent(MainActivity.this, Error.class);
            startActivity(i);
            finish();
        }else {
            if (last_link != "#null" && saveLastUrl == true) {
                mWebView.loadUrl(last_link);
            } else {
                mWebView.loadUrl(webUrl);
            }
        }

        // RateApp
        if (rateActive == true) {
            AppRater.app_rate(this);
        }

        // GooglePlayAds
        if (admobBannerActive == true) {
            admob_banner_block();
        }

    }

    public void onStart(){
        super.onStart();
        Log.d(tag, "In the onStart() event");
    }

    public void onRestart(){
        super.onRestart();
        Log.d(tag, "In the onRestart() event");
    }

    public void onResume(){
        super.onResume();
        Log.d(tag, "In the onResume() event");

        // Resume Google Play Ads
        if (adView != null) {
            adView.resume();
        }
    }

    public void onPause(){
        super.onPause();
        Log.d(tag, "In the onPause() event");
        // Stop mWebView
        stopEveryThing();
    }

    public void onStop(){
        super.onStop();
        Log.d(tag, "In the onStop() event");

        // Stop mWebView
        stopEveryThing();

        // Pause Google Play Ads
        if (adView != null) {
            adView.pause();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        Log.d(tag, "In the onDestroy() event");

        // Stop mWebView
        stopEveryThing();

        // Destroy Google Play Ads
        if (adView != null) {
            adView.destroy();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if( id == R.id.share){
            sharepost();
            return true;
        }else if (id == R.id.refresh){
            mProgressBar.setVisibility(View.VISIBLE);
            refresh();
            return true;
        }else if (id == R.id.home){
            mProgressBar.setVisibility(View.VISIBLE);
            mWebView.loadUrl(webUrl);
            return true;
        }else if (id == R.id.contact) {
            mProgressBar.setVisibility(View.VISIBLE);
            mWebView.loadUrl(contact_url);
            return true;
        }else if (id == R.id.about) {
            mProgressBar.setVisibility(View.VISIBLE);
            mWebView.loadUrl(about_url);
            return true;
        }else if (id == R.id.close) {
            exitDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // UI function
    public void UI(){
        mWebView = (WebView) findViewById(R.id.mWebView);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
    }

    // ConnectionDetector Function
    @SuppressLint("NewApi")
    public void hideActionBar(){
        if (Build.VERSION.SDK_INT < 16 && fullscreen_webview == true) {
            // Hide the Action Bar on Android 4.0 and Lower
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else if (fullscreen_webview == true) {
            // Hide the Action Bar on Android 4.1 and Higher
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            android.app.ActionBar actionBar = getActionBar();
            actionBar.hide();
        }
    }



    // mWebViewClient
    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("tel:")) {
                mFunctions.toast("This is aphone number");
            } else if (url.startsWith("http:") || url.startsWith("https:")) {
                mProgressBar.setVisibility(View.VISIBLE);
                mWebView.loadUrl(url);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
                mWebView.loadUrl(url);
            }
            return true;
        }

        // On Page Error
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            mFunctions.toast(connection_error_message);
            finish();
        }

        // On Page Finished
        @Override
        public void onPageFinished(WebView view, String url) {
            mProgressBar.setVisibility(View.INVISIBLE);
            saveCurrentLink();
            if (adviewShowOnce == 0) {
                if (admobInterstitialActive == true) {
                    admob_interstitial_block();
                    adviewShowOnce = 1;
                }
            }
        }
    }

    // Save Current Link (used to retrieve last link, in case of activity destroy)
    public void saveCurrentLink() {
        link_bank = mWebView.getUrl();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        Editor editor = pref.edit();
        editor.putString("lastlink", link_bank);
        editor.commit();
        //===>>logging
        Log.d(tag, "Link Saved");
    }

    // mWebview ChromeClient
    private class mWebViewChromeClient extends WebChromeClient{

        // GeoLocation Code
        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin,
                                                       final GeolocationPermissions.Callback callback) {

            //===>> Logging
            Log.d(tag, "In the OnGeoLocationPermissions");

            // Always grant permission since the app itself requires location
            // permission and the user has therefore already granted it
            // Retrieve GeoLocation Setting
            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            geoLocationValue = pref.getBoolean("geoLocation", false);

            // GeoLocation Dialog
            if (geoLocationValue == false) {
                AlertDialog.Builder geoAlertDialog = new AlertDialog.Builder(MainActivity.this);
                geoAlertDialog.setIcon(R.drawable.ic_launcher)
                        .setTitle(appName)
                        .setMessage("Would Like to Use Your Current Location")
                        .setPositiveButton("Ok", new OnClickListener() {

                            // OnClick
                            public void onClick(DialogInterface dialog, int id) {
                                callback.invoke(origin, true, false);
                                mFunctions.toast("Geolocation Activated");
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                Editor editor = pref.edit();
                                editor.putBoolean("geoLocation", true);
                                editor.commit();
                                Log.d(tag, "GeoLocation = true");
                                mProgressBar.setVisibility(View.VISIBLE);
                                refresh();
                            }

                        }).setNegativeButton("Don't Allow", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        callback.invoke(origin, false, false);
                    }
                }).create();

                // GeoLocation Dialog Show
                geoAlertDialog.show();

            } else {
                callback.invoke(origin, true, false);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int progress) {
            // Activities and WebViews measure progress with different scales.
            // The progress meter will automatically disappear when we reach 100%
            //.setProgress(progress * 100);
        }
    }

    // Stop mWebView
    public void stopEveryThing() {
        mWebView.clearAnimation();
        mWebView.clearDisappearingChildren();
        mWebView.stopLoading();
    }

    // Refresh
    public void refresh() {
        mWebView.stopLoading();
        mWebView.reload();
    }

    // KeyDown_Back
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && link_bank.equals(webUrl)){
            Log.d(tag,"equal home");
            stopEveryThing();
            exitDialog();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && !mWebView.canGoBack() && !link_bank.contains(webUrl)){
            Log.d(tag,"Not eqaul home and can not go back");
            stopEveryThing();
            mProgressBar.setVisibility(View.VISIBLE);
            mWebView.loadUrl(webUrl);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack() && !link_bank.equals(webUrl)){
            Log.d(tag,"Not eqaul home and can go back");
            stopEveryThing();
            mProgressBar.setVisibility(View.VISIBLE);
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // Share Page
    private void sharepost() {
        Intent shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setType("text/plain");
        String xshare = "link";

        if (shareUrl != "null") {
            xshare = shareMessage + shareUrl;
        } else {
            xshare = shareMessage + mWebView.getUrl();
        }

        shareintent.putExtra(Intent.EXTRA_TEXT, xshare);
        startActivity(Intent.createChooser(shareintent, "How do you want to share?"));
    }

    // Exit Dialog
    public void exitDialog() {
        Log.d(tag, "In the exitDialog()");
        AlertDialog.Builder exitAlertDialog = new AlertDialog.Builder(MainActivity.this);

        exitAlertDialog.setTitle("Confirm Exit")
                .setMessage("Do you want to quit?")
                .setPositiveButton("Okay", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //===>> Logging
                        Log.d(tag, "Exit Dialog = true");
                        stopEveryThing();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //===>> Logging
                        Log.d(tag, "Exit Dialog = false");
                    }
                }).create();

        exitAlertDialog.show();
    }

    // Google Play Ads (banner)
    public void admob_banner_block() {
        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(admobBannerID);
        LinearLayout layout = (LinearLayout) findViewById(R.id.banner_ads_layout);
        layout.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    // Google Play Ads (interstitial)
    public void admob_interstitial_block() {
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(admobInterstitialID);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                displayInterstitial();
            }
            public void onAdClosed() {
                // On Ads close
            }
        });
    }

    // Google Play Ads - interstitial (Display).
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

}