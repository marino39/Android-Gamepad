package marino39.agamepad;

import marino39.ui.main.UIMain;
import marino39.utils.IBinderWrapper;
import marino39.agamepad.conf.Configuration;
import marino39.agamepad.ui.AGUIMain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

/**
 * Main Activity of Android Gamepad. It's responsible for 
 * creation of proper gui and handling user input events.
 * 
 * @author Marcin Gorzynski
 *
 */
public class AndroidGamepadActivity extends Activity {
    
	private WakeLock mWakeLock;
	private IBinder mBinder = null;
	private AndroidGamepadService mService = null;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Loading Data
        Bundle extras = getIntent().getExtras();
        IBinderWrapper iBinderWrapped = (IBinderWrapper) extras.get("IBinder");
        mBinder = iBinderWrapped.binder;
        
        if (mBinder.isBinderAlive()) {
        	mService = ((AndroidGamepadService.AGPServerServiceBinder) mBinder).getService();
        	
        	// Configuration
        	Configuration c = Configuration.getDefaultConfiguration(this);
        	
        	// ------ TO BE MOVED -------
            UIMain main = new AGUIMain(this);
            c.populate(main);
            setContentView(main);
        }
        
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.gamepad_menu, menu);
        return true;
    }
    
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        case R.id.settings:
            return true;
 
        case R.id.help:
            //Toast.makeText(AndroidMenusActivity.this, "Save is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }  
    
    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        super.onDestroy();
    }

	public IBinder getmBinder() {
		return mBinder;
	}

	public void setmBinder(IBinder mBinder) {
		this.mBinder = mBinder;
	}

	public AndroidGamepadService getmService() {
		return mService;
	}

	public void setmService(AndroidGamepadService mService) {
		this.mService = mService;
	}
    
}