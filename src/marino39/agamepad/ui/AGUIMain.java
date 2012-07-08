package marino39.agamepad.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import marino39.agamepad.conf.Configuration;
import marino39.agamepad.theme.Theme;
import marino39.ui.UITouchEventListener;
import marino39.ui.components.Button;
import marino39.ui.main.UIMain;

/**
 * Class that Extends UIMain with UI Editor.
 * 
 * @author Marcin Gorzynski
 *
 */
public class AGUIMain extends UIMain {
	
	private boolean editorMode = false;
	private float settingsVisibility = 0;
	private float baseVisibility = 0.5f;
	private Button settingsButton;
	private Theme theme;

	public AGUIMain(Context context) {
		super(context);
		Configuration c = Configuration.getCurrentInstance(null, false);
		
		if (c != null) {
			theme = c.getTheme();
			
			Bitmap settingsBitmap = theme.getSettingsIcon();
			settingsButton = new Button(new Point(0, 0), settingsBitmap, settingsBitmap, "");
			settingsButton.setSize(new Point(64, 64));
			settingsButton.setListener(new UITouchEventListener() {
				
				@Override
				public void onUp(MotionEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onMove(MotionEvent e) {

				}
				
				@Override
				public void onDown(MotionEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		settingsButton.setAlpha((int) (255 * baseVisibility));
		settingsButton.draw(canvas);
		
		if (!editorMode) {
			
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!editorMode) {
			super.onTouchEvent(event);
		}
		
		return true;
	}
	
	

}
