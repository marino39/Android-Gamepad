package marino39.agamepad.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.util.Log;

/**
 * Client that receives info about Adroid Gamepad Servers.
 * Informations about serwer are broadcasted in local network.
 * 
 * @author Marcin Gorzynski
 *
 */
public class BroadcastReceiver implements Runnable {

	private final static int BROADCAST_PORT = 25078;
	
	private DatagramSocket bSocket = null;
	private int port = -1;
	private List<ServerInfo> serverList = new ArrayList<ServerInfo>();
	
	
	private boolean running = false;
	private boolean stop = false;
	
	/**
	 * Class designed to hold info about servers.
	 * 
	 * @author Marcin Gorzynski
	 *
	 */
	public class ServerInfo {
		public String name = "";
		public String address = "";
		public Date lastUpdate = null;
		public int port = -1;
	}
	
	@Override
	public void run() {
		if (!running) {
			try {
				if (port != -1) {
					bSocket = new DatagramSocket(port);
				} else {
					bSocket = new DatagramSocket(BROADCAST_PORT);
				}
				bSocket.setBroadcast(true);
				bSocket.setSoTimeout(5000);
			} catch (SocketException e) {
				stop = true;
				e.printStackTrace();
			}

			running = true;
			while (!stop) {				
                try {
                	byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
					bSocket.receive(packet);
					int len = packet.getLength();
					String server = new String(packet.getData());
					server = server.substring(0, len);
					boolean found = false;
					
					synchronized (serverList) {
						for (int i = 0; i < getServerListSize(); i++) {
							ServerInfo si = getServerInfo(i);
							if ((si.address + ":" + si.port).equalsIgnoreCase(server)) {
								found = true;
								si.lastUpdate = Calendar.getInstance().getTime();
							} 
						}
					}
					
					if (found == false) {
						ServerInfo si = new ServerInfo();
						String[] serverData = server.split(":");
						
						if (serverData.length == 2) {
							si.address = serverData[0];
							si.port = Integer.parseInt(serverData[1]);
							si.lastUpdate = Calendar.getInstance().getTime();
							synchronized (serverList) {
								addServerInfo(si);
							}
						}
					}
					
					removeOldRecords();
				} catch (SocketTimeoutException e) {
					removeOldRecords();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			stop = false;
			running = false;
		}
	}
	
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}
	
	public void stop() {
		stop = true;
	}
	
	public boolean isRunning() {
		return running;
	}

	/**
	 * Gets size of server list.
	 * 
	 * @return size of list.
	 */
	public int getServerListSize() {
		synchronized (serverList) {
			return serverList.size();
		}	
	}
	
	/**
	 * Gets info about server.
	 * 
	 * @param index position in the list.
	 * @return information about server.
	 */
	public ServerInfo getServerInfo(int index) {
		synchronized (serverList) {
			return serverList.get(index);
		}
	}
	
	/**
	 * Adds ServerInfo to server list.
	 * @param si server information.
	 */
	private void addServerInfo(ServerInfo si) {
		synchronized (serverList) {
			serverList.add(si);
		}
	}
	
	/**
	 * Adds ServerInfo to server list.
	 * @param si server information.
	 */
	private void removeServerInfo(ServerInfo si) {
		synchronized (serverList) {
			serverList.remove(si);
		}
	}
	
	/**
	 * Get's copy of the server list.
	 * @return copy of server list.
	 */
	public List<ServerInfo> getListCopy() {
		synchronized (serverList) {
			return new ArrayList<BroadcastReceiver.ServerInfo>(serverList);
		}
	}
	
	/**
	 * Removes servers that are no longer valid.
	 */
	private void removeOldRecords() {
		synchronized (serverList) {
			for (int i = 0; i < getServerListSize(); i++) {
				ServerInfo si = getServerInfo(i);
				Date date = new Date(si.lastUpdate.getTime() + 4000);
				
				if (date.before(Calendar.getInstance().getTime())) {
					removeServerInfo(si);
				}
			}
		}
	}

}
