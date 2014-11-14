package escapevelocity.zulucoding.com.escapevelocity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Rateme {

	

	public static class AppRater {
		
	    private final static String appname = AppContent.application_name;
	    private final static String packageName = AppContent.package_name;
	    private final static int usesBeforeRateLaunch = AppContent.number_of_uses_before_launching_the_rate_dialog;

	    public static void app_rate(Context mContext) {
	        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
	        if (prefs.getBoolean("dntshow", false)) { return ; }

	        SharedPreferences.Editor editor = prefs.edit();

	        // Increment launch counter
	        long launch_count = prefs.getLong("launch_count", 0) + 1;
	        editor.putLong("launch_count", launch_count);

	       
	        // Wait at least n of launches
	        if (launch_count >= usesBeforeRateLaunch)
	        {
	        	showRateDialog(mContext, editor);
	        }

	        editor.commit();
	    }   

	    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
	        final Dialog dialog = new Dialog(mContext);
	        dialog.setTitle("Rate " + appname);

	        LinearLayout ll = new LinearLayout(mContext);
	        ll.setOrientation(LinearLayout.VERTICAL);

	        TextView tv = new TextView(mContext);
	        tv.setText("If you enjoy using " + appname + ", please take a moment to rate it. Thanks for your support!");
	        tv.setWidth(240);
	        tv.setPadding(4, 0, 4, 10);
	        ll.addView(tv);

	        Button b1 = new Button(mContext);
	        b1.setText("Rate " + appname);
	        b1.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
	                dialog.dismiss();
	                editor.putBoolean("dntshow", true);
                    editor.commit();
	            }
	        });        
	        ll.addView(b1);

	        Button b2 = new Button(mContext);
	        b2.setText("Remind me later");
	        b2.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                dialog.dismiss();
	            }
	        });
	        ll.addView(b2);

	        Button b3 = new Button(mContext);
	        b3.setText("No, thanks");
	        b3.setOnClickListener(new OnClickListener() {
	            public void onClick(View v) {
	                if (editor != null) {
	                    editor.putBoolean("dntshow", true);
	                    editor.commit();
	                }
	                dialog.dismiss();
	            }
	        });
	        ll.addView(b3);

	        dialog.setContentView(ll);        
	        dialog.show();        
	    }
	}






}
