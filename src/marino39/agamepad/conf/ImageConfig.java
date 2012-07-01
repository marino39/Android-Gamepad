package marino39.agamepad.conf;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import marino39.ui.components.Image;
import marino39.ui.components.UIComponent;

public class ImageConfig implements ComponentConfig {
	
	private static final String LOG_TAG = "[ImageConfig]";
	private float x = -1, y = -1;
	private int width = -1, height = -1;
	private int resourceID = -1;
	
	@Override
	public UIComponent getConfiguredComponent() {
		Configuration c = Configuration.getCurrentInstance(null);
		Log.e(LOG_TAG, "" + resourceID);
		Bitmap im = BitmapFactory.decodeResource(c.getAndroidResources(), resourceID);
		
		return im != null ? new Image(im) : null;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getResourceID() {
		return resourceID;
	}

	public void setResourceID(int resourceID) {
		this.resourceID = resourceID;
	}

}
