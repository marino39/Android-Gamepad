package marino39.agamepad.net;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerInfo implements Parcelable {

	public String name = "";
	public String address = "";
	public Date lastUpdate = null;
	public int port = -1;
	
	/**
	 * Default constructor.
	 */
	public ServerInfo() {
		
	}
	
	/**
	 * Constructor to rebuild from pracel. 
	 * @param in
	 */
	public ServerInfo(Parcel in) {
		name = in.readString();
		address = in.readString();
		port = in.readInt();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(address);
		dest.writeInt(port);
	}

}
