package marino39.agamepad;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import marino39.agamepad.R;
import marino39.ui.*;
import marino39.ui.components.Image;
import marino39.ui.components.MouseController;
import marino39.ui.main.UIMain;
import marino39.utils.IBinderWrapper;
import marino39.agamepad.conf.Configuration;
import marino39.agamepad.protocol.*;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

/**
 * 
 * @author Marcin Gorzynski
 *
 */
public class AndroidGamepadActivity extends Activity {
    
	private PadView diablo3Pad = null;
	private WakeLock mWakeLock;
	private Socket server = null;
	private IBinder mBinder = null;
	private AndroidGamepadService mService = null;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIMain main = new UIMain(this);
        setContentView(main);
        
        // Loading Data
        Bundle extras = getIntent().getExtras();
        IBinderWrapper iBinderWrapped = (IBinderWrapper) extras.get("IBinder");
        mBinder = iBinderWrapped.binder;
        
        if (mBinder.isBinderAlive()) {
        	mService = ((AndroidGamepadService.AGPServerServiceBinder) mBinder).getService();
        	
        	// Configuration
        	Configuration c = Configuration.getDefaultConfiguration(this);
            c.populate(main);
        }
        
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
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