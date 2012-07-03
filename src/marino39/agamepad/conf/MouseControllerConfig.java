package marino39.agamepad.conf;

import marino39.agamepad.theme.Theme;
import marino39.ui.components.Button;
import marino39.ui.components.MouseController;
import marino39.ui.components.UIComponent;
import android.graphics.Point;

public class MouseControllerConfig implements ComponentConfig {
	
	private static final String LOG_TAG = "[MouseControllerConfig]";
	private float x = -1, y = -1;
	private float scale = -1;
	private int alpha = -1;
	private int width = -1, height = -1;

	@Override
	public UIComponent getConfiguredComponent() {
		Theme t = Configuration.getCurrentInstance(null).getTheme();
		MouseController mc = new MouseController(new Point((int) x, (int) y), t.getMouseController());
		
		if (width != -1 && height != -1) {
			mc.setSize(new Point(width, height));
		}
		
		if (scale != -1) {
			Point size = mc.getSize();
			mc.setSize(new Point((int) ((float) (size.x) * scale), (int) ((float) (size.y) * scale)));
		}
		
		if (alpha != -1) {
			mc.setAlpha(alpha);
		}
		
		return mc;
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
