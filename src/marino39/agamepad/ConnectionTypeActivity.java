package marino39.agamepad;

import java.util.ArrayList;
import java.util.List;

import marino39.agamepad.net.AndroidGamepadClient;
import marino39.agamepad.net.ServerInfo;
import marino39.utils.IBinderWrapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ConnectionTypeActivity extends Activity {

	private ListView connectionTypeList = null;
	private List<String> connectionTypeArray = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        connectionTypeList = (ListView) findViewById(R.id.listView1);
        connectionTypeArray = new ArrayList<String>();
        connectionTypeArray.add("WiFi");
        connectionTypeArray.add("Bluetooth");
        
        if (connectionTypeList != null) {
        	connectionTypeList.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
        			R.layout.list, connectionTypeArray));
        	connectionTypeList.setTextFilterEnabled(true);
        	
        	connectionTypeList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					TextView tv = (TextView) arg1;
					String label = tv.getText().toString();
					if (label.equalsIgnoreCase("WiFi")) {
						Intent i = new Intent(getApplicationContext(), ServerListActivity.class);
						startActivity(i);
					} else if(label.equalsIgnoreCase("Bluetooth")) {
						AlertDialog alertDialog = new AlertDialog.Builder(ConnectionTypeActivity.this).create();
						alertDialog.setTitle("Alert");
						alertDialog.setMessage("Bluetooth connection is not supported yet.");
						alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
						   public void onClick(DialogInterface dialog, int which) {
						      // here you can add functions
						   }
						});
						alertDialog.setIcon(R.drawable.icon);
						alertDialog.show();
					}
				}
        		
        	});		
        }
	}
	
}
