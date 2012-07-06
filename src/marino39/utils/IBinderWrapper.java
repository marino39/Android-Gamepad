package marino39.utils;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

public class IBinderWrapper implements Parcelable {
	
	public IBinder binder = null;
	
	public static final Parcelable.Creator<IBinderWrapper> CREATOR = new Parcelable.Creator<IBinderWrapper>() {
		public IBinderWrapper createFromParcel(Parcel in) {
			return new IBinderWrapper(in);
		}

		public IBinderWrapper[] newArray(int size) {
			return new IBinderWrapper[size];
		}
	};
	
	public IBinderWrapper() {
		
	}
	
	public IBinderWrapper(Parcel in) {
		binder = in.readStrongBinder();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStrongBinder(binder);		
	}
	
}