package org.encorelab.rutgers;

import org.apache.cordova.DroidGap;

import org.encorelab.rutgers.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class RutgersShell extends DroidGap {
	
	static final int SET_PREFERENCES = 0;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //super.loadUrl(getAppUrl());
    }
    
    public void onShowLoginClick(View view) {
    	ConnectivityManager connMgr = (ConnectivityManager) 
    	        getSystemService(Context.CONNECTIVITY_SERVICE);
    	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    	    
	    if (networkInfo != null && networkInfo.isConnected()) {
	        // load URL
	    	super.loadUrl(getAppUrl());
	    } else {
	        // display error
	    	Toast toast = Toast.makeText(getApplicationContext(), "No network connection", Toast.LENGTH_LONG);
	    	toast.show();
	    } 
    }
    
    public String getAppUrl() {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        
    	String defaultAppUrl = "http://mobile.rutgers.badger.encorelab.org/index.html";
    	
        String appUrl = prefs.getString("app_url", defaultAppUrl);
        if (appUrl.length() == 0) // make sure that the URL isn't blank
        	appUrl = defaultAppUrl;
        
        if (appUrl.length() == 0) // make sure that it still isn't blank (in case defaultSailAppUrl was hosed)
        	appUrl = "file:///android_asset/www/index.html";
        
        return appUrl;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rutgers, menu);
        return true;
    }
    
    
    
    @Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Log.v(this.getClass().getName(), "Menu item selected: " + item.toString() + " (" + item.getItemId() + ")");
    	if (item.getItemId() == R.id.settings) {
    		Intent prefsActivity = new Intent(getBaseContext(), RutgersSettings.class);
    		startActivityForResult(prefsActivity, RutgersShell.SET_PREFERENCES);
    		return true;
    	} else {
    		return false;
    	}
		//return false;
	}

	/*@Override
	public boolean onKeyDown(int i,KeyEvent e){
		return false;
	}*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  if (keyCode == KeyEvent.KEYCODE_BACK) {
	    if(appView.canGoBack()){
	       appView.goBack();
	        return true;
	    }
	  }
	  return super.onKeyDown(keyCode, event);
	}

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SET_PREFERENCES) {
            if (resultCode == RESULT_OK) {
            	super.onActivityResult(requestCode, resultCode, intent);
        		this.loadUrl(this.getAppUrl());
            }
        } else {
        	super.onActivityResult(requestCode, resultCode, intent);
        }
    }
    
    public void reload(MenuItem item) {
    	Log.d("PhoneGapShell", "Deleting cache...");
    	this.getCacheDir().delete();
    	this.loadUrl(this.appView.getOriginalUrl());
    }
    
}
