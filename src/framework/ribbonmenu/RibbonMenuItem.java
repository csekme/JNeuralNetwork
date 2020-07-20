package framework.ribbonmenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;

public class RibbonMenuItem extends JMenuItem {

	private static final long serialVersionUID = -9058297717228162951L;
	final JMenuItem ref = new JMenuItem();

	boolean hover;
	boolean pressed;
	private ImageIcon icon;
	private final static ImageIcon checkedIcon = new ImageIcon(
			RibbonBar.class.getClassLoader().getResource("resources/images/checked.png"));
	private final static ImageIcon uncheckedIcon = new ImageIcon(
			RibbonBar.class.getClassLoader().getResource("resources/images/unchecked.png"));
	private boolean checkMenu = false;
	private static Color clHover = new Color(200, 198, 196);
	private static Color clPressed = new Color(179, 176, 173);
	private static Color clBackground = new Color(255,255,255);
	
	public RibbonMenuItem(String title, boolean defaultSelection) {
		super(title);
		this.checkMenu = true;
		this.setSelected(defaultSelection);
		addMouseListener(MA);
	}
	
	public RibbonMenuItem(String title) {
		super(title);
		addMouseListener(MA);
	}

	public RibbonMenuItem(String title, ImageIcon icon) {
		super(title);

		this.icon = icon;
		addMouseListener(MA);
	}

	public boolean isCheckMenu() {
		return checkMenu;
	}

	public void setCheckMenu(boolean checkMenu) {
		this.checkMenu = checkMenu;
	}
	
	public static void setHoverColor(Color color) {
		clHover = color;
	}
	
	public static void setPressedColor(Color color) {
		clPressed = color;
	}
	
	public static void setBackgroundColor(Color color) {
		clBackground = color;
	}

	@Override
	public void paint(Graphics gl) {
		Graphics2D g = (Graphics2D) gl;

		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		g.setFont(ref.getFont());
		g.setColor( clBackground );
		g.fillRect(0, 0, getWidth(), getHeight());
		if (hover) {
			g.setColor(clHover);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		if (pressed) {
			g.setColor(clPressed);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.setColor(ref.getForeground());
		g.drawString(getText(), getIconTextGap() + 27, 16);
		if (!isCheckMenu() && icon != null) {
			g.drawImage(icon.getImage(), 4, 3, 16, 16, this);
		}

		if (isCheckMenu()) {
			if (isSelected()) {
				g.drawImage(checkedIcon.getImage(), 4, 3, 16, 16, this);

			} else {
				g.drawImage(uncheckedIcon.getImage(), 4, 3, 16, 16, this);
			}
		}

		// super.paint(g);
	}

	MouseAdapter MA = new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			repaint();
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			setSelected(!isSelected());
			pressed = true;
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			hover = false;
			pressed = false;
		}

		@Override
		public void mouseExited(MouseEvent e) {
			hover = false;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			hover = true;
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			hover = true;
		}

	};

}
