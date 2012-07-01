package marino39.ui.components;

import marino39.ui.UITouchEventListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

public class MouseController implements UIComponent, UITouchEventListener {

	private boolean visible = true;
	private Point position = new Point(0, 0);
	private Point size = new Point(0, 0);
	private Paint paint = new Paint();
	private Bitmap image = null;
	private Matrix mDraw;
	private int alpha = 255;
	private int pointerID;
	
	private UITouchEventListener listener = null;
	
	public MouseController(Point position, Bitmap image) {
		this.image = image;
		this.position = position;
		size = new Point(image.getWidth(), image.getHeight());
		calculateMatrixes();
	}
	
	@Override
	public void draw(Canvas canvas) {
		paint.setAlpha(alpha);
		canvas.drawBitmap(image, mDraw, paint);
	}
	
	private void calculateMatrixes() {
		Matrix mScale = new Matrix();
		int w = image.getWidth();
		int h = image.getHeight();
		mScale.setScale((float) (size.x) / (float) (w),
				(float) (size.y) / (float) (h));
		
		Matrix mPosition = new Matrix();
		mPosition.setTranslate(position.x, position.y);
		
		mDraw = new Matrix();
		mDraw.setConcat(mPosition, mScale);
	}

	@Override
	public void setVisible(boolean visibility) {
		visible = visibility;
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

	@Override
	public void setAlpha(int a) {
		alpha = a;
	}

	@Override
	public int getAlpha() {
		return alpha;
	}

	@Override
	public void onDown(MotionEvent e) {
		if (listener != null) {
			listener.onDown(e);
		}
	}

	@Override
	public void onUp(MotionEvent e) {
		if (listener != null) {
			listener.onUp(e);
		}
	}

	@Override
	public void onMove(MotionEvent e) {
		if (listener != null) {
			listener.onMove(e);
		}
	}
	
	public UITouchEventListener getListener() {
		return listener;
	}

	public void setListener(UITouchEventListener listener) {
		this.listener = listener;
	}

	public int getPointerID() {
		return pointerID;
	}

	public void setPointerID(int pointerID) {
		this.pointerID = pointerID;
	}
	
}