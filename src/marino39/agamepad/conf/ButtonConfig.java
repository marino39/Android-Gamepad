package marino39.agamepad.conf;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import marino39.agamepad.theme.Theme;
import marino39.ui.UITouchEventListener;
import marino39.ui.components.Button;
import marino39.ui.components.UIComponent;

public class ButtonConfig implements ComponentConfig {
	
	private static final String LOG_TAG = "[ButtonConfig]";
	private float x = -1, y = -1;
	private float scale = -1;
	private String label = null;
	private int key = -1;
	private int alpha = -1;
	private int width = -1, height = -1;

	@Override
	public UIComponent getConfiguredComponent() {
		Theme t = Configuration.getCurrentInstance(null).getTheme();
		Button b = new Button(new Point((int) x, (int) y), t.getButtonNotPressed(), t.getButtonPressed(), label);
		
		if (width != -1 && height != -1) {
			b.setSize(new Point(width, height));
		}
		
		if (scale != -1) {
			Point size = b.getSize();
			b.setSize(new Point((int) ((float) (size.x) * scale), (int) ((float) (size.y) * scale)));
		}
		
		if (alpha != -1) {
			b.setAlpha(alpha);
		}
		
		return b;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
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

}
