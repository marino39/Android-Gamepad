package marino39.agamepad.theme;

import marino39.agamepad.AndroidGamepadActivity;
import marino39.agamepad.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Theme {
		
	private Bitmap background = null;
	private Bitmap mouseController = null;
	private Bitmap buttonPressed = null;
	private Bitmap buttonNotPressed = null;
	private Bitmap settingsIcon = null;
	
	public Theme(Resources r) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inTargetDensity = 1;
		options.inDensity = 1;
		
        background = BitmapFactory.decodeResource(r, R.drawable.bg1, options);
        mouseController = BitmapFactory.decodeResource(r, R.drawable.mouse_controller, options);
        buttonNotPressed = BitmapFactory.decodeResource(r, R.drawable.button_1, options);
        buttonPressed = BitmapFactory.decodeResource(r, R.drawable.button_2, options);
        settingsIcon = BitmapFactory.decodeResource(r, R.drawable.settings_icon, options);
	}

	public Bitmap getBackground() {
		return background;
	}

	public void setBackground(Bitmap background) {
		this.background = background;
	}

	public Bitmap getMouseController() {
		return mouseController;
	}

	public void setMouseController(Bitmap mouseController) {
		this.mouseController = mouseController;
	}

	public Bitmap getButtonPressed() {
		return buttonPressed;
	}

	public void setButtonPressed(Bitmap buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

	public Bitmap getButtonNotPressed() {
		return buttonNotPressed;
	}

	public void setButtonNotPressed(Bitmap buttonNotPressed) {
		this.buttonNotPressed = buttonNotPressed;
	}

	public Bitmap getSettingsIcon() {
		return settingsIcon;
	}

	public void setSettingsIcon(Bitmap settingsIcon) {
		this.settingsIcon = settingsIcon;
	}
	
}
