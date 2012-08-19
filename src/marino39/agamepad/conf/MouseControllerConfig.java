package marino39.agamepad.conf;

import marino39.agamepad.net.AndroidGamepadClient;
import marino39.agamepad.protocol.MouseMovePacket;
import marino39.agamepad.protocol.Packet;
import marino39.agamepad.theme.Theme;
import marino39.ui.UITouchEventListener;
import marino39.ui.components.Button;
import marino39.ui.components.MouseController;
import marino39.ui.components.UIComponent;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class MouseControllerConfig implements ComponentConfig {
	
	private static final String LOG_TAG = "[MouseControllerConfig]";
	private float x = -1, y = -1;
	private float scale = -1;
	private int alpha = -1;
	private int width = -1, height = -1;

	@Override
	public UIComponent getConfiguredComponent() {
		Configuration config = Configuration.getCurrentInstance(null, false);
		final AndroidGamepadClient agc = config.getAndroidGamepadClient();
		Theme t = config.getTheme();
		MouseController mc = new MouseController(new Point((int) x, (int) y), t.getMouseController());
		
		if (width != -1 && height != -1) {
			mc.setSize(new Point(width, height));
		} else {
			width = t.getMouseController().getWidth();
			height = t.getMouseController().getHeight();
		}
		
		if (scale != -1) {
			Point size = mc.getSize();
			mc.setSize(new Point((int) ((float) (size.x) * scale), (int) ((float) (size.y) * scale)));
		}
		
		if (alpha != -1) {
			mc.setAlpha(alpha);
		}
		
		mc.setListener(new UITouchEventListener() {
			
			@Override
			public void onUp(MotionEvent e) {
				process(e);
			}
			
			@Override
			public void onMove(MotionEvent e) {
				process(e);
			}
			
			@Override
			public void onDown(MotionEvent e) {
				process(e);
			}
			
			private void process(MotionEvent e) {
				float x = e.getAxisValue(MotionEvent.AXIS_X, 0
						/*mouseController.getPointerID()*/); // NEEDS FIX !!!!!!!!!!!!!!
				float y = e.getAxisValue(MotionEvent.AXIS_Y, 0
						/*mouseController.getPointerID()*/); // NEEDS FIX !!!!!!!!!!!!!!
				Point pos = new Point((int)MouseControllerConfig.this.x, (int)MouseControllerConfig.this.y)/*mouseController.getPosition()*/;
				final Point size = new Point(width, height)/*mouseController.getSize()*/;

				//Log.e(LOG_TAG, "MOVE");
				//Log.e(LOG_TAG, "x: " + x + " y: " + y + " x: " + pos.x + " y: " + pos.y + " width: " + size.x + " height: " + size.y);
				float radius = size.x/2;
				float x_s = x - (pos.x + size.x/2);
				float y_s = y - (pos.y + size.y/2);
				float radius_s = (float) Math.sqrt(x_s * x_s + y_s * y_s);
				if (radius_s < radius) {
					final float translatedX = (pos.x + size.x / 2 - x) * 2;
					final float translatedY = (pos.y + size.y / 2 - y) * 2;

					agc.sendPacket(new MouseMovePacket(Packet.OPERATION_MOUSE_MOVE,
							(byte) 0x08, (float) translatedX / (float) size.x,
							(float) translatedY / (float) size.y));
				}
			}
		});
		
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
