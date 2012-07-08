package marino39.agamepad.net;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import marino39.agamepad.protocol.Packet;

/**
 * 
 * 
 * @author Marcin Gorzynski
 *
 */
public class AndroidGamepadClient {
	
	private Socket server = null;
	private boolean connected = false;
	

	/**
	 * Checks if Client is connected to the server.
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return connected;
	}
	
	/**
	 * Connects Client to the server.
	 * 
	 * @param si server information.
	 */
	public void connect(final ServerInfo si) {	
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					server = new Socket(si.address, si.port);
					server.setTrafficClass(0x10 + 0x08); // LOW_DELAY & IPTOS_THROUGHPUT - may cause some problems with some routers 
					connected = true;
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * Sends packet to the server.
	 * 
	 * @param p packet to send.
	 */
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
