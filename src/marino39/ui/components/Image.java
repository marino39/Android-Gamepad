package marino39.ui.components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class Image implements UIComponent {

	private boolean visible = true;
	private Point position = new Point(0, 0);
	private Point size = new Point(0, 0);
	private Paint paint = new Paint();
	private Bitmap image = null;
	private Matrix mDraw;
	private int alpha = 255;
	
	
	public Image(Bitmap image) {
		this.image = image;
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
}
