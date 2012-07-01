package marino39.agamepad;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import marino39.agamepad.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        serverListArray = new ArrayList<String>();
        
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
				DatagramSocket ds = null;
				try {
					ds = new DatagramSocket(25078);
					ds.setBroadcast(true);
					ds.setSoTimeout(5000);
				} catch (SocketException e) {
					e.printStackTrace();
				}
				
				while (true) {
					try {
						byte[] buf = new byte[1024];
		                DatagramPacket packet = new DatagramPacket(buf, buf.length);
						ds.receive(packet);
						int len = packet.getLength();
						String server = new String(packet.getData());
						server = server.substring(0, len);
						boolean found = false;
						for (int i = 0; i < serverListArray.size(); i++) {
							if (serverListArray.get(i).equalsIgnoreCase(server)) {
								found = true;
								break;
							}
						}
						
						if (found == false) {
							serverListArray.add(server);
							serverList.post(new Runnable() {
								
								@Override
								public void run() {
									serverList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.list, serverListArray));
									serverList.invalidate();
								}
								
							});
						}
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
    }
	
}
