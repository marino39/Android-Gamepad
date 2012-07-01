package marino39.agamepad;

import marino39.agamepad.PadView.PadButton;
import android.view.MotionEvent;

public interface EventHandler {

	public void processEvent(MotionEvent event, int pointer);
	public void processEvent(MotionEvent event, PadButton pb, int pointer);
	
}
