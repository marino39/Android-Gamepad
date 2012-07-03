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
	
	public Theme(Resources r) {
        background = BitmapFactory.decodeResource(r, R.drawable.bg1);
        mouseController = BitmapFactory.decodeResource(r, R.drawable.mv_ball);
        buttonNotPressed = BitmapFactory.decodeResource(r, R.drawable.button_1);
        buttonPressed = BitmapFactory.decodeResource(r, R.drawable.button_2);
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
	
}
