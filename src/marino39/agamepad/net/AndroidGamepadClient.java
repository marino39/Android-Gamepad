package marino39.agamepad.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import marino39.agamepad.protocol.Packet;

public class AndroidGamepadClient {
	
	private Socket server = null;
	private boolean connected = false;
	

	public boolean isConnected() {
		return connected;
	}
	
	public void connect(final ServerInfo si) {	
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					server = new Socket(si.address, si.port);
					server.setTrafficClass(0x10); // LOW_DELAY - may cause some problems with some routers 
					connected = true;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void sendPacket(final Packet p) {
		if (connected) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						OutputStream os = server.getOutputStream();						
						os.write(p.getBytes());
						os.flush();
					} catch (IOException e) {
						connected = false;
						server = null;
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
}
