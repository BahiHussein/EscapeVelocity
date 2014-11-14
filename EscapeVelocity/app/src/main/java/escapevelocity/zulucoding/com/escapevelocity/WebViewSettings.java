package escapevelocity.zulucoding.com.escapevelocity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

public class WebViewSettings {
	
	// mWebView Settings
	public Boolean mWebView_cache = false;
	public Boolean mWebView_zoom = true;
	public Boolean mWebView_file_access = true;
	public Boolean mWebView_database = true;
	public Boolean mWebView_form_data = true;
	public Boolean mWebView_plugins = true;
	public Boolean mWebView_java = true;
	public Boolean mWebView_display_quality = true;
	public Boolean mWebView_geo_location = true;
	
	private Context mContext;
    
    public WebViewSettings(Context context){
        this.mContext = context;
    }
    
 // mWebview Setting 
   	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
   	@SuppressLint("SetJavaScriptEnabled") 
   	public void mWebViewSettings(WebView mWebView) {
   		mWebViewCache(mWebView);
   		mWebViewZoom(mWebView);
   		mWebViewFileAccess(mWebView);
   		mWebViewDataBase(mWebView);
   		mWebViewFormData(mWebView);
   		mWebViewJava(mWebView);
   		mWebViewPlugins(mWebView);
   		mWebViewDisplayQaulity(mWebView);
   		mWebViewGeoLocation(mWebView);
   	}
   	
   	// mWebView Cache Settings
   	@SuppressWarnings("deprecation")
	public void mWebViewCache(WebView mWebView){
   		if(mWebView_cache == false){
   			mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
   		} else {
 	  		mWebView.getSettings().setAppCacheEnabled(true);
 	  		mWebView.getSettings().getCacheMode();
 	  		mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 15);
 	  		mWebView.getSettings().setAppCachePath(mContext.getApplicationContext().getCacheDir().getAbsolutePath());
 	  		mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
   		}
   	}
   	
   	// mWebView Zoom Settings
   	@SuppressLint("NewApi") 
   	@SuppressWarnings("deprecation")
 	public void mWebViewZoom(WebView mWebView){
   		if(mWebView_zoom == true){
   			mWebView.getSettings().setBuiltInZoomControls(true);
   			mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
   			mWebView.getSettings().setDisplayZoomControls(true);
   		}
   	}
   	
   	// mWebView File Access Settings
   	@SuppressLint("NewApi") 
   	public void mWebViewFileAccess(WebView mWebView){
   		if(mWebView_file_access == true){
   			mWebView.getSettings().setAllowContentAccess(true);
   			mWebView.getSettings().setAllowFileAccess(true);
   			mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
   			mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
   		}
   	}

 	// mWebView DataBase
 	public void mWebViewDataBase(WebView mWebView){
 		if(mWebView_database == true){
   		mWebView.getSettings().setAllowFileAccess(true);
   		mWebView.getSettings().setDatabaseEnabled(true);
   		mWebView.getSettings().setGeolocationDatabasePath( mContext.getFilesDir().getPath());
 		}
 	}
 	
 	// mWebView Form Data
 	@SuppressWarnings("deprecation")
	public void mWebViewFormData(WebView mWebView){
 		if(mWebView_form_data == true){
 			mWebView.getSettings().setSaveFormData(true);
 			mWebView.getSettings().setSavePassword(true);
 		}
 	}
 	
 	// mWebView Java
 	@SuppressLint("SetJavaScriptEnabled") 
 	public void mWebViewJava(WebView mWebView){
 		if(mWebView_java == true){
 			mWebView.getSettings().setJavaScriptEnabled(true);
 			mWebView.getSettings().setJavaScriptEnabled(true);
 		}
 	}
 	
 	@SuppressWarnings("deprecation")
	public void mWebViewPlugins(WebView mWebView){
 		if(mWebView_plugins == true){
 			mWebView.getSettings().getPluginState();
 	  		mWebView.getSettings().setPluginState(PluginState.ON);
 	  		mWebView.getSettings().setPluginState(PluginState.ON_DEMAND);
 		}
 	}
 	
 	// mWebView Display Quality
 	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi") 
 	public void mWebViewDisplayQaulity(WebView mWebView){
 		if(mWebView_display_quality == true){
 			 mWebView.getSettings().setUseWideViewPort(true);
 			 mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
 			 mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
 			 mWebView.setHorizontalScrollBarEnabled(false);
 			 mWebView.setVerticalScrollBarEnabled(false);
 			 mWebView.setScrollbarFadingEnabled(true);
 			 mWebView.getSettings().setLightTouchEnabled(true);
 			 mWebView.getSettings().setEnableSmoothTransition(true);
 			 mWebView.getSettings().setSupportMultipleWindows(true);
 			 mWebView.getSettings().setDomStorageEnabled(true);
 			 mWebView.getSettings().setDatabaseEnabled(true);
 		 }
 	}
 	
 	// mWebView GeoLocation 
 	public void mWebViewGeoLocation(WebView mWebView){
 		if(mWebView_geo_location == true){
 			mWebView.getSettings().setGeolocationEnabled(true);
 	  		mWebView.getSettings().setGeolocationDatabasePath( mContext.getFilesDir().getPath());
 		}
 	}
}
