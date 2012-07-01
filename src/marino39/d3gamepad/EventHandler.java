package marino39.d3gamepad;

import marino39.d3gamepad.PadView.PadButton;
import android.view.MotionEvent;

public interface EventHandler {

	public void processEvent(MotionEvent event, int pointer);
	public void processEvent(MotionEvent event, PadButton pb, int pointer);
	
}
