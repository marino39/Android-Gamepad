package marino39.ui.components;

import android.graphics.Canvas;
import android.graphics.Point;

public interface UIComponent {
	
	/**
	 * Draws UIComponent on provided canvas.
	 * @param canvas
	 */
	public void draw(Canvas canvas);
	
	/**
	 * Sets visibility of UIComponent.
	 * @param visibility
	 */
	public void setVisible(boolean visibility);
	
	/**
	 * Gets visibility of UIComponent.
	 * @param canvas
	 */
	public boolean isVisible();
	
	/**
	 * Sets position of UIComponent on the screen.
	 * @param p
	 */
	public void setPosition(Point p);
	
	/**
	 * Gets position of UIComponent.
	 * @return
	 */
	public Point getPosition();
	
	/**
	 * Sets size of UIComponent.
	 * @param p
	 */
	public void setSize(Point p);
	
	/**
	 * Gets size of UIComponent.
	 * @return
	 */
	public Point getSize();
	
	public void setAlpha(int a);
	
	public int getAlpha();
	
	public void setzindex(int zindex);
	
	public int getzindex();
}
