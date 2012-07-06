package marino39.agamepad;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import marino39.agamepad.AndroidGamepadService.AGPServerServiceBinder;
import marino39.agamepad.R;
import marino39.agamepad.net.BroadcastReceiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
			        TextView tx = (TextView) arg1;
					String server = tx.getText().toString();
					Intent runD3GamePad = new Intent(getApplicationContext(), AndroidGamepadActivity.class);
					runD3GamePad.putExtra("server", server);
					startActivity(runD3GamePad);
					
				}
        		
        	});		
        }

        new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						if (mService != null) {
							BroadcastReceiver br = mService
									.getBroadcastClient();
							List<BroadcastReceiver.ServerInfo> siList = br
									.getListCopy();
							serverListArray = new ArrayList<String>();
							for (int i = 0; i < siList.size(); i++) {
								BroadcastReceiver.ServerInfo si = siList.get(i);
								serverListArray.add(si.address + ":" + si.port);
							}

							serverList.post(new Runnable() {

								@Override
								public void run() {
									serverList
											.setAdapter(new ArrayAdapter<String>(
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
