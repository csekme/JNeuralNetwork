package framework.ribbonmenu;

import java.awt.Color;
import java.awt.Point;

import javax.swing.ImageIcon;

public class VirtualObject {
	
	public int x;
	public int y;
	public int width;
	public int height;
	public String token;
	public ImageIcon image;
	public Integer integerValue;
	public Color ForegroundColor;
	public Color BackgroundColor;
	public String title;
	
	public boolean hover;
	public boolean hoverTop;
	public boolean selected;
	public boolean selectedTop;
	
	public VirtualObject(String token) {
		this.ForegroundColor = new Color(0.0f,0.0f,0.0f,0.0f);
		this.BackgroundColor = new Color(0.0f,0.0f,0.0f,0.0f);
		this.token = token;
		this.hover = false;
	}
	
	public boolean inBounds(Point p, String token) {
		
		if ( ( p.x > x ) && ( p.x < ( x + width ) ) && ( p.y > y ) && ( p.y < ( y + height ) ) && this.token.equals(token) ) {
			return true;
		}
		return false;
	}
	
	
	public boolean inBoundsPartOf(Point p, int fromTheTop, String token) {
		if ( ( p.x > x ) && ( p.x < ( x + width ) ) && ( p.y > y + fromTheTop ) && ( p.y < ( y + height ) ) && this.token.equals(token) ) {
			return false;
		}
		return true;
	}
	

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Color getForegroundColor() {
		return ForegroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		ForegroundColor = foregroundColor;
	}

	public Color getBackgroundColor() {
		return BackgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		BackgroundColor = backgroundColor;
	}

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
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelectedTop() {
		return selectedTop;
	}

	public void setSelectedTop(boolean selectedTop) {
		this.selectedTop = selectedTop;
	}

	public boolean isHoverTop() {
		return hoverTop;
	}

	public void setHoverTop(boolean hoverTop) {
		this.hoverTop = hoverTop;
	}
	
	
	
	
}
