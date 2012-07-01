package marino39.ui.components;

import java.util.logging.Logger;

import marino39.ui.UITouchEventListener;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;

public class Button implements UIComponent, Touchable {

	private final String LOG_TAG = "[Button]";
	
	private boolean visible = true;
	private Point position = new Point(0, 0);
	private Point size = new Point(0, 0);
	private Paint paint = new Paint();
	private Bitmap pressedBitmap = null;
	private Bitmap notPressedBitmap = null;
	private Bitmap currentBitmap = null;
	private Matrix mDraw;
	private int alpha = 255;
	private String label = "-";
	
	private UITouchEventListener listener = null;
	
	public Button(Point position, Bitmap notPressed, Bitmap pressed, String l) {
		this.position = position;
		pressedBitmap = pressed;
		notPressedBitmap = notPressed;
		currentBitmap = notPressedBitmap;
		label = l;
		paint.setAntiAlias(true);
		size = new Point(notPressedBitmap.getWidth(), notPressedBitmap.getHeight());
		calculateMatrixes();
	}
	
	@Override
	public void draw(Canvas canvas) {	
		paint.setAlpha(alpha);
		canvas.drawBitmap(currentBitmap, mDraw, paint);
		paint.setColor(Color.WHITE);
		paint.setTextSize(size.y/2); // TODO wrong calculations for text size in button.
		Rect bounds = new Rect();
	    paint.getTextBounds(label, 0, label.length(), bounds);
		paint.setAlpha(alpha);
		canvas.drawText(label , position.x + size.x/2 - bounds.width()/2 - bounds.left, position.y + size.y/2 + bounds.height()/2, paint); // TODO wrong position calculations
	}
	
	private void calculateMatrixes() {
		Matrix mScale = new Matrix();
		int w = notPressedBitmap.getWidth();
		int h = notPressedBitmap.getHeight();
		mScale.setScale((float) (size.x) / (float) (w),
				(float) (size.y) / (float) (h));
		
		Matrix mPosition = new Matrix();
		mPosition.setTranslate(position.x, position.y);
		
		mDraw = new Matrix();
		mDraw.setConcat(mPosition, mScale);
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setPosition(Point p) {
		position = p;
		calculateMatrixes();
	}

	@Override
	public Point getPosition() {
		return position;
	}

	@Override
	public void setSize(Point p) {
		size = p;
		calculateMatrixes();
	}

	@Override
	public Point getSize() {
		return size;
	}

	public UITouchEventListener getListener() {
		return listener;
	}

	public void setListener(UITouchEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void onDown(MotionEvent e) {
		if (listener != null) {
			listener.onDown(e);
		}
		currentBitmap = pressedBitmap;
	}

	@Override
	public void onUp(MotionEvent e) {
		if (listener != null) {
			listener.onUp(e);
		}
		currentBitmap = notPressedBitmap;
	}

	@Override
	public void onMove(MotionEvent e) {
		if (listener != null) {
			listener.onMove(e);
		}
	}

	@Override
	public void setAlpha(int a) {
		alpha = a;
	}

	@Override
	public int getAlpha() {
		return alpha;
	}
	
}
