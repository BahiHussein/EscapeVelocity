package escapevelocity.zulucoding.com.escapevelocity;

import android.content.Context;
import android.net.ConnectivityManager;
 
public class ConnectionDetector {

	private Boolean offline_app = AppContent.offline_app; 
    private Context mContext;
     
    public ConnectionDetector(Context context){
        this.mContext = context;
    }
 
    public boolean isConnectingToInternet(){
    	
    	ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if((cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected() == true) || offline_app == true)
		{
			return true; 
		}
		
		return false;

    }
}