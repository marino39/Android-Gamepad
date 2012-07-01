package marino39.ui.components;

import android.view.MotionEvent;

/**
 * Interface which have to be implemented by all UIComponents
 * that handle touch events. It's also used to generate custom
 * handlers for buttons.
 * @author Marcin Gorzynski
 *
 */
public interface Touchable {

	/**
	 * Handler for ACTION_DOWN ...
	 * @param e
	 */
	public void onDown(MotionEvent e);
	
	/**
	 * Handler for ACTION_UP ...
	 * @param e
	 */
	public void onUp(MotionEvent e);
	
	/**
	 * Handler for ACTION_MOVE
	 * @param e
	 */
	public void onMove(MotionEvent e);
	
}
