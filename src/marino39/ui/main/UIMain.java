package marino39.ui.main;

import java.util.ArrayList;

import marino39.ui.UITouchEventListener;
import marino39.ui.components.MouseController;
import marino39.ui.components.Touchable;
import marino39.ui.components.UIComponent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Class used as a main View.
 * @author Marcin Gorzynski
 */
public class UIMain extends View {

	/**
	 * Tag for Android Logger
	 */
	private final String LOG_TAG = "[UIMain]";
	
	/**
	 * Contains component list.
	 */
	private ArrayList<UIComponent> components = new ArrayList<UIComponent>();
	
	/**
	 * Connects Pointer with Component
	 */
	private int[] ptcMapper = {-1, -1, -1, -1};
	
	public UIMain(Context context) {
		super(context);
	}
	
	/**
	 * Adds UIComponent to component list.
	 * @param c component that will be added to the list.
	 */
	public void addUIComponent(UIComponent c) {
		synchronized (components) {
			int currentComponentZ = c.getzindex();
			
			boolean added = false;
			for (int i = 0; i < components.size(); i++) {
				if (currentComponentZ < components.get(i).getzindex()) {
					components.add(i, c);
					//Log.e("x", "Adding at " + i + " pos");
					break;
				}
			}
			
			if (!added) {
				components.add(c);
				//Log.e("x", "Adding at the end");
			}
		}
		invalidate();
	}
	
	/**
	 * Removes UIComponent from component list based on component Object.
	 * @param c component that will be removed from the list.
	 */
	public void removeUIComponent(UIComponent c) {
		synchronized(components) {
			components.remove(c);
		}
		invalidate();
	}
	
	/**
	 * Removes UIComponent from component list based on index in component list.
	 * @param index index of component that will be removed from the list.
	 */
	public void removeUIComponent(int index) {
		synchronized(components) {
			components.remove(index);
		}
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		synchronized(components) {
			for (int i = 0; i < components.size(); i++) {
				UIComponent c = components.get(i);
				if (c.isVisible()) {
					c.draw(canvas);
				}
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN
				|| event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN
				|| event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN) {
			for (int i = 0; i < components.size(); i++) {
				UIComponent c = components.get(i);
				if (c instanceof Touchable) {
					Point pos = c.getPosition();
					Point size = c.getSize();
					int arrayIndex = translatePointerToArrayIndex(event);
					float x = event.getAxisValue(MotionEvent.AXIS_X,
							event.getActionIndex());
					float y = event.getAxisValue(MotionEvent.AXIS_Y,
							event.getActionIndex());

					if (x > pos.x && x < (pos.x + size.x) && y > pos.y
							&& y < (pos.y + size.y)) {
						ptcMapper[arrayIndex] = i;
						((Touchable) c).onDown(event);
						invalidate();
					}
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP
				|| event.getAction() == MotionEvent.ACTION_POINTER_1_UP
				|| event.getAction() == MotionEvent.ACTION_POINTER_2_UP
				|| event.getAction() == MotionEvent.ACTION_POINTER_3_UP) {
			int arrayIndex = translatePointerToArrayIndex(event);
			if (arrayIndex != -1 && ptcMapper[arrayIndex] != -1) {	
				UIComponent c = components.get(ptcMapper[arrayIndex]);
				ptcMapper[arrayIndex] = -1;
				if (c instanceof Touchable) {
					((Touchable) c).onUp(event);
					invalidate();
				}
			}
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			for (int i = 0; i < event.getPointerCount(); i++) {
				if (i != -1 && ptcMapper[i] != -1) {	
					UIComponent c = components.get(ptcMapper[i]);
					Point pos = c.getPosition();
					Point size = c.getSize();
					float x = event.getAxisValue(MotionEvent.AXIS_X,
							event.getActionIndex());
					float y = event.getAxisValue(MotionEvent.AXIS_Y,
							event.getActionIndex());

					if (x > pos.x && x < (pos.x + size.x) && y > pos.y
							&& y < (pos.y + size.y)) {
						if (c instanceof Touchable) {
							if (c instanceof MouseController) {
								((MouseController) c).setPointerID(i);
							}
							((Touchable) c).onMove(event);
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Translates event id numbers to array indexes.
	 * @param event
	 * @return
	 */
	private int translatePointerToArrayIndex(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
			return 0;
		} else if (event.getAction() == MotionEvent.ACTION_POINTER_1_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_1_UP) {
			return 0;
		} else if (event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_2_UP) {
			return 1;
		} else if (event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_3_UP) {
			return 2;
		}
		
		return -1;
	}

}
