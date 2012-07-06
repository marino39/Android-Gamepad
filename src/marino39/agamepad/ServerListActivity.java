package marino39.agamepad;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import marino39.agamepad.AndroidGamepadService.AGPServerServiceBinder;
import marino39.agamepad.R;
import marino39.agamepad.net.AndroidGamepadClient;
import marino39.agamepad.net.BroadcastReceiver;
import marino39.agamepad.net.ServerInfo;
import marino39.utils.IBinderWrapper;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ServerListActivity extends Activity {
	
	private ListView serverList = null;
	private List<String> serverListArray = null;
	
	private AndroidGamepadService mService = null;
	private AndroidGamepadService.AGPServerServiceBinder mBinder = null;
	private Object lock = new Object();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        serverListArray = new ArrayList<String>();
        Intent intent = new Intent(this, AndroidGamepadService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        
        serverList = (ListView) findViewById(R.id.listView1);
        if (serverList != null) {
        	serverList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.list, serverListArray));
        	serverList.setTextFilterEnabled(true);
        	serverList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
			        if (mService != null && mBinder.isBinderAlive()) {
			        	TextView tx = (TextView) arg1;
			        	String server = tx.getText().toString();
			        	List<ServerInfo> siList = mService.getBroadcastClient().getListCopy();
			        	AndroidGamepadClient agc = mService.getAndroidGamepadClient();
			        			
			        	boolean found = false;
			        	for (int i = 0; i < siList.size(); i++) {
			        		ServerInfo si = siList.get(i);
			        		if (si.address.equalsIgnoreCase(server)) {
			        			found = true;
			        			agc.connect(si);
			        		}
			        	}
			        	
			        	int i = 50;
			        	while (!agc.isConnected() && i > 0) {
			        		try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
			        		i--;
			        	}
			        	
			        	if (found && agc.isConnected()) {
			        		Intent runD3GamePad = new Intent(getApplicationContext(), AndroidGamepadActivity.class);
			        		IBinderWrapper wrapper = new IBinderWrapper();
			        		wrapper.binder = mBinder;
			        		runD3GamePad.putExtra("IBinder", wrapper);
			        		startActivity(runD3GamePad);
			        	} else {
			        		//display error
			        	}
			        }
				}
        		
        	});		
        }

        new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						if (mService != null) {
							BroadcastReceiver br = mService.getBroadcastClient();
							List<ServerInfo> siList = br.getListCopy();
							serverListArray = new ArrayList<String>();
							for (int i = 0; i < siList.size(); i++) {
								ServerInfo si = siList.get(i);
								serverListArray.add(si.address);
							}

							serverList.post(new Runnable() {

								@Override
								public void run() {
									serverList.setAdapter(new ArrayAdapter<String>(
													getApplicationContext(),
													R.layout.list,
													serverListArray));
									serverList.invalidate();
								}

							});
						}
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
		}).start();
        
    }
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName cName, IBinder iBinder) {
			synchronized (lock) {
				mBinder = (AGPServerServiceBinder) iBinder;
				mService = mBinder.getService();
				lock.notify();
			}
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mService = null;
		}
    };
}
