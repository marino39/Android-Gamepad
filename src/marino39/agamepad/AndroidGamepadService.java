package marino39.agamepad;

import marino39.agamepad.net.BroadcastReceiver;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Service responsible for running BroadcastReceiver and AGPClient
 * 
 * @author Marcin Gorzynski
 *
 */
public class AndroidGamepadService extends Service {

	private final BroadcastReceiver broadcastClient = new BroadcastReceiver();
	private final AGPServerServiceBinder binder = new AGPServerServiceBinder();
	
	@Override
	public IBinder onBind(Intent i) {
		if (!broadcastClient.isRunning()) {
			broadcastClient.start();
		}
		return binder;
	}
	
	/**
	 * It helps to bind Service with Activities
	 * 
	 * @author Marcin Gorzynski
	 *
	 */
	public class AGPServerServiceBinder extends Binder {
		
		public AndroidGamepadService getService() {
			return AndroidGamepadService.this;
		}
		
	}

	public BroadcastReceiver getBroadcastClient() {
		return broadcastClient;
	}	
}
