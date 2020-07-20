package framework.ribbonmenu;
import java.awt.Point;
/**
 * Objektum határait leíró osztály x,y szlesség magasság, x2,y2
 * @author Csekme Krisztián | KSQFYZ
 */
public class Bound {

	public int x;
	public int y;
	public int width;
	public int height;
	public int x2;
	public int y2;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.x2 = x + width;
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.y2 = y+ height;
		this.height = height;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.width = x2-x;
		this.x2 = x2;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.height = y2-y;
		this.y2 = y2;
	}
	
	/**
	 * A paraméterben átadott pontról eldönti hogy
	 * az objektum határain belül vagy kivül esik
	 * @param p #{@link java.awt.Point}
	 * @return true ha határon belüli
	 */
	public boolean isBound( Point p ) {
		if ( p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height  ) {
			return true;
		}
		return false;
	}
	
	
}
