package escapevelocity.zulucoding.com.escapevelocity;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.Toast;
 
public class Functions {
     
    private Context mContext;
    public Boolean hide_hint_on_focus = AppContent.hide_hint_on_focus;
     
    public Functions(Context context){
        this.mContext = context;
    }
    
    // Toast Message (Long)
    public void toast(String message) 
	{
		Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_LONG)
		.show();
	}
    
    // Get Device Information 
    public String getDeviceName() {
    	  String manufacturer = Build.MANUFACTURER;
    	  String model = Build.MODEL;
    	  if (model.startsWith(manufacturer)) {
    	    return capitalize(model);
    	  } else {
    	    return capitalize(manufacturer) + " " + model;
    	  }
    	}
    
    // Capitalize
	public String capitalize(String text) {
		char first = text.charAt(0);
		if (text == null || text.length() == 0) {
		    return "Unknow";
		}
		else if (Character.isUpperCase(first)) {
		    return text;
		} else {
		    return Character.toUpperCase(first) + text.substring(1);
		}
	} 
	
	// Hide Hint On Focus
	public void hideHint(final EditText edittext){
		if(hide_hint_on_focus == true){
			edittext.setOnFocusChangeListener(new OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
				    if(hasFocus){
				        edittext.setHint("");
				    }
				   }
				});
		}
	}
}