package framework.ribbonmenu;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class RibbonBar extends JComponent {

	private static final long serialVersionUID = 2504100578579649593L;
	
	final int ribbonTabHeight = 28;
	final int eastwestTabInset = 20;
	final int northTabInset = 0;
	final int ribbonButtonTopBase = ribbonTabHeight + 4;
	final int buttonWidth = 75;
	final int buttonHeight = 75;
	final int buttonPartialHeight = 34;
	final int slimButtonWidth = 155; 
	final int slimButtonHeight = 25;
	final int separatorWidth = 7;
	final int separatorHeight = 88;
	final int shadow = 10;
	final int ribbonHeight = 126 + shadow;
	
	JPopupMenu pop	= null;
	
	private Font font;
	
	public enum color_tokens {ribbon_background, ribbon_tab_container_background, ribbon_tab_container_strip, ribbon_tab_background, ribbon_tab_foreground,
	ribbon_tab_hover_background, ribbon_tab_hover_foreground, ribbon_tab_selected_strip_background, ribbon_tab_selected_foreground,
	ribbon_button_background, ribbon_button_pressed_background, ribbon_button_hover_background, ribbon_button_foreground, ribbon_separator_foreground, ribbon_group_color ,ribbon_shadow_dark, ribbon_shadow_light,
	ribbon_menuitem_hover,ribbon_menuitem_pressed, ribbon_menuitem_background}
 
	final static String series = "ABCDEFGHIJKLMNOPQRSTWZXYabcdefghijklmneopqrstzyxwv0123456789#&@{}*";
	
	Map<color_tokens,Color> colors;
	List<Tab> tabs;
	Map<String,String> tokens;
	
	boolean buildMenu = true;
	
	public void putColor(color_tokens key, Color value) {
		colors.put(key, value);
		if (key == color_tokens.ribbon_menuitem_hover) {
			RibbonMenuItem.setHoverColor(value);
		}
		if (key == color_tokens.ribbon_menuitem_pressed) {
			RibbonMenuItem.setPressedColor(value);
		}
		if(key==color_tokens.ribbon_menuitem_background) {
			RibbonMenuItem.setBackgroundColor(value);
		}
	}
	
	
	/**
	 * Constructor
	 */
	public RibbonBar() {
		
		tokens = new HashMap<>();
		
		pop = new JPopupMenu();
		pop.setOpaque(true);
		pop.setBackground(Color.white);
		add(pop);
	    
		font = Util.loadFont("/resources/Cabin-Regular.ttf", 12f);
		
		{
			setMinimumSize(new Dimension(0, ribbonHeight));
			setPreferredSize(new Dimension(100, ribbonHeight));
		}
		
		colors = new HashMap<>();
		colors.put(color_tokens.ribbon_background, new Color(243, 242, 241));
		colors.put(color_tokens.ribbon_tab_container_background, new Color(243, 242, 241));
		colors.put(color_tokens.ribbon_tab_container_strip, new Color(230,229,228));
		colors.put(color_tokens.ribbon_tab_background, new Color(243, 242, 241));
		colors.put(color_tokens.ribbon_tab_foreground, new Color(72,70,68));
		colors.put(color_tokens.ribbon_tab_hover_background, new Color(250, 249, 248));
		colors.put(color_tokens.ribbon_tab_hover_foreground, new Color(0, 0, 0));
		colors.put(color_tokens.ribbon_tab_selected_foreground, new Color(0,191,255));	
		colors.put(color_tokens.ribbon_tab_selected_strip_background, new Color(0,191,255));
		colors.put(color_tokens.ribbon_button_background, new Color(243, 242, 241));
		colors.put(color_tokens.ribbon_button_hover_background, new Color(200, 198, 196));
		colors.put(color_tokens.ribbon_separator_foreground, new Color(179,176,173));
		colors.put(color_tokens.ribbon_button_foreground, new Color(72,70,68));
		colors.put(color_tokens.ribbon_group_color, new Color(130, 130, 130));
		colors.put(color_tokens.ribbon_shadow_dark, new Color(211, 211, 211));
		colors.put(color_tokens.ribbon_shadow_light, new Color(230, 230, 230));
		colors.put(color_tokens.ribbon_button_pressed_background, new Color(179, 176, 173));
		 
	 
		tabs = new ArrayList<>();
		
		// listenerek hozz?ad?sa
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	
	private void buildMenu() {
		
		int offset_t = 0;
	
		for (int i=0; i<tabs.size(); i++) {
			Tab o = tabs.get(i);
			
			int w = getGraphics().getFontMetrics(font).stringWidth(o.getTitle()) + (eastwestTabInset * 2);
			o.setWidth(w);
			o.setHeight(ribbonTabHeight);
			o.setX(offset_t);
			o.setY(northTabInset);
			offset_t+=w;
			int offset_bx = 2;
			int offset_by = 0;
			int slim_count = 0;
			int slim_max = 0;
			for (int b=0; b<o.getButtons().size(); b++) {
				Button g = o.getButtons().get(b);
				if (g.isSlim()) {
					int sw;
					if (g.getTitle() != null && g.getTitle().length() > 0) {
						sw = getGraphics().getFontMetrics(font).stringWidth(g.title) + 26;
					} else {
						sw = 22;
					}

					if (slim_max<sw) {
						slim_max=sw;
					}
					g.setWidth( sw );
					g.setHeight(slimButtonHeight);
					g.setX(offset_bx);
					g.setY(ribbonButtonTopBase + offset_by);
					slim_count++;
					offset_by+=slimButtonHeight;
					if (slim_count==3) {
						offset_bx += slim_max;
						offset_by = 0;
						slim_max=0;
					}
				}
				if (g.isSeparator()) {
					if (slim_count>0) {
						slim_count = 0;
						offset_bx+=slim_max;
					}
					g.setHeight(separatorHeight);
					g.setX(offset_bx);
					g.setY(ribbonButtonTopBase);
					offset_by = 0;
					offset_bx+=separatorWidth;
					slim_count = 0;
				}
				if (!g.isSlim() && !g.isSeparator()) {
					if (slim_count>0) {
						slim_count = 0;
						offset_bx+=slim_max;
					}
					
					g.setWidth(buttonWidth);
					g.setHeight(buttonHeight);
					g.setX(offset_bx);
					g.setY(ribbonButtonTopBase);
					offset_bx+=buttonWidth + 2;
					slim_count = 0;
					offset_by = 0;					
				}				
			}
		}
		
		repaint();
	}
	
	/**
	 * Tab is a top level menu for the top of ribbon
	 * @param title
	 * @return currently created Tab
	 */
	public Tab addTab(String title) {
		String gen = generateToken(8);
		tokens.put(gen, title);
		Tab tab = new Tab(gen);
		tab.setTitle(title);
		tabs.add(tab);
		return tab;
	}
	
	public static String generateToken(int length) {
		StringBuilder sb = new StringBuilder();
		Random rnd = new Random();
		for (int i=0; i<length; i++) {
			sb.append(series.charAt(rnd.nextInt(series.length()-1)));
		}
		return sb.toString();
	}
	
	@Override
	public void paint(Graphics gg) {
		Graphics2D g = (Graphics2D)gg;
		
		g.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
		g.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON 
				);
		
		
		if (buildMenu) {
			buildMenu = false;
			buildMenu();
		}
		
		//Ribbon background
		g.setColor(colors.get(color_tokens.ribbon_background));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//Ribbon tab background
		g.setColor(colors.get(color_tokens.ribbon_tab_container_background));
		g.fillRect(0, 0, getWidth(), ribbonTabHeight);
		
		g.setColor(colors.get(color_tokens.ribbon_tab_container_strip));
		g.drawLine(0, ribbonTabHeight, getWidth(), ribbonTabHeight);
 
		if (font!=null) { 
			g.setFont(font);
		}
		
		//Kirajzoljuk a fenti top men?k a tabokat		
		for (int i=0; i<tabs.size(); i++) {
			Tab t = tabs.get(i);
			if (t.isHover()) {
				g.setColor(colors.get(color_tokens.ribbon_tab_hover_background));	
			} else {
				g.setColor(colors.get(color_tokens.ribbon_tab_background));
			}		
			g.fillRect(t.getX(), t.getY(), t.getWidth(), t.getHeight());
			
			//Selected tab
			if (t.isSelected()) {
				g.setColor(colors.get(color_tokens.ribbon_tab_selected_strip_background));				
				if (t.isHover()) {
					g.fillRect(t.getX(), t.getY() + t.getHeight() - 4, t.getWidth(), 4);
				} else {
					int half = (t.getWidth() - g.getFontMetrics().stringWidth(t.getTitle())) / 2;
					g.fillRect(t.getX() + half, t.getY() + t.getHeight() - 4, t.getWidth() - half * 2, 4);

				}
			}
			
			if (t.isHover()) {
				g.setColor(colors.get(color_tokens.ribbon_tab_hover_foreground));		
			} else {
				g.setColor(colors.get(color_tokens.ribbon_tab_foreground));				
			}
			if (t.isSelected()) {
				g.setColor(colors.get(color_tokens.ribbon_tab_selected_foreground));
			}
			g.drawString(t.getTitle(), t.x + t.width/2 - g.getFontMetrics().stringWidth(t.getTitle()) / 2 , t.y + 20);

			int horizontal_offset = 0;
			// Gombok
			if (t.isSelected()) {
				{ //Group title
					g.setFont(font.deriveFont(9f));
					for (int s = 0; s < t.getGroups().size(); s++) {
						String groupname = t.getGroups().get(s);
						g.setColor(colors.get(color_tokens.ribbon_group_color));

						int groupname_length = g.getFontMetrics().stringWidth(groupname);
						int west = getWidth();
						Button sep = t.getSeparator(s);
						if (sep != null) {
							west = sep.x;
						}
						g.drawString(groupname, horizontal_offset + (west - horizontal_offset) / 2 - groupname_length / 2, getHeight() - 8 - shadow);
						horizontal_offset += west;
					}
					g.setFont(font);
				}
				if (shadow>0) {
					GradientPaint shadow_paint = new GradientPaint(0, getHeight()-shadow, colors.get(color_tokens.ribbon_shadow_dark),
				            0, getHeight(), colors.get(color_tokens.ribbon_shadow_light));
					g.setPaint(shadow_paint);
			        g.fill(new Rectangle2D.Double(0, getHeight()-shadow, getWidth(), getHeight()));
				}
				for (int y = 0; y < t.getButtons().size(); y++) {
					Button b = t.getButtons().get(y);
					
					if (b.isSeparator()) {
						g.setColor(colors.get(color_tokens.ribbon_separator_foreground));
						g.drawLine(b.x + separatorWidth / 2, b.getY() + 1 , b.x + separatorWidth / 2, b.getY() + separatorHeight);
					} else {
						if (b.isHover()) {
							g.setColor(colors.get(color_tokens.ribbon_button_hover_background));				
						} else {
							g.setColor(colors.get(color_tokens.ribbon_button_background));
						}
						if (b.isPressed()) {
							g.setColor(colors.get(color_tokens.ribbon_button_pressed_background));
						}
						
						if (!b.isSlim() && b.hasDropDown() && b.isHover()) {
							if (b.isHoverTop()) {
								g.fillRect(b.getX(), b.getY(), b.getWidth()+1 , buttonPartialHeight );
								g.drawRect(b.getX(), b.getY() + buttonPartialHeight, b.getWidth() , b.getHeight()- buttonPartialHeight );
							} else {
								g.drawRect(b.getX(), b.getY(), b.getWidth(), buttonPartialHeight );
								g.fillRect(b.getX(), b.getY() + buttonPartialHeight, b.getWidth() +1, b.getHeight()- buttonPartialHeight );
								
							}
							
						} else {
							g.fillRect(b.getX(), b.getY(), b.getWidth(), b.getHeight() );								
						}
						
						g.setColor(colors.get(color_tokens.ribbon_button_foreground));
						if (b.isSlim()) {
							if (b.getImage() == null) {
								g.drawString(b.title, b.x + 4, b.y + b.height - 8);
							} else {
								g.drawImage(b.getImage().getImage(), b.x + 2, b.y + 4 , 16, 16, this);
								g.drawString(b.title, b.x + 4 + 16, b.y + b.height - 8);
							}
						} else {
							if (b.hasDropDown()) {
								if (b.getImage()!=null) {
									g.drawImage(b.getImage().getImage(), b.x + 26, b.y + 6, 24, 24, this);
								}
								String[] lines = b.title.split(" ");
								for (int l=0; l<lines.length; l++) {
									int w = g.getFontMetrics().stringWidth(lines[l]);
									g.drawString(lines[l], b.x + b.getWidth()/2 - w/2,  b.y + b.height - 18 + (l * 14) - (lines.length>1?10:0) );
								}
								g.setColor(Color.GRAY);
								g.setStroke( new BasicStroke(1.3f));
								g.drawLine(b.x + b.width/2 -3, b.y + b.height -8, b.x + b.width/2, b.y + b.height - 6);
								g.drawLine(b.x + b.width/2 +3, b.y + b.height -8, b.x + b.width/2, b.y + b.height - 6);
								
								//Normal classic button
							} else {
								if (b.getImage()!=null) {
									g.drawImage(b.getImage().getImage(), b.x + 26, b.y + 6, 24, 24, this);
								}
								String[] lines = b.title.split(" ");
								for (int l=0; l<lines.length; l++) {
									int w = g.getFontMetrics().stringWidth(lines[l]);
									g.drawString(lines[l], b.x + b.getWidth()/2 - w/2,  b.y + b.height - 18 + (l * 14) - (lines.length>1?10:0) );
								}
								
							}
						
						}
					}
				}
			}
			
		}
		super.paint(g);
	}
	
	
	public void clearFlag() {
		for (int i = 0; i < tabs.size(); i++) {
			for (int j = 0; j < tabs.get(i).getButtons().size(); j++) {
				tabs.get(i).getButtons().get(j).setSelected(false);
				tabs.get(i).getButtons().get(j).setHover(false);
			}
		}
		repaint();
	}
	
	private MouseAdapter mouse = new MouseAdapter() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			for (int i=0; i<tabs.size(); i++) {
				tabs.get(i).setHover( false );
			}
			for (int i=0; i<tabs.size(); i++) {
			    tabs.get(i).setHover( tabs.get(i).inBounds(e.getPoint(), tabs.get(i).token) );
			}
			for (int i=0; i<tabs.size(); i++) {
				Tab t = tabs.get(i);
				if (t.isSelected()) {
					for (int j = 0; j < t.getButtons().size(); j++) {
						Button b = t.getButtons().get(j);
						if (!b.isSeparator()) {
							b.setHover(b.inBounds(e.getPoint(), b.token));
							if (b.hasDropDown()) {
								b.setHoverTop(b.inBoundsPartOf(e.getPoint(), buttonPartialHeight, b.token));
							}
						}
					}
				}
			}
			
			repaint();
			
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@SuppressWarnings("static-access")
		@Override
		public void mouseReleased(MouseEvent e) {
			  
			
			if (e.getPoint().y <= ribbonTabHeight) {
				boolean found = false;
				for (int i = 0; i < tabs.size(); i++) {
					if (tabs.get(i).inBounds(e.getPoint(), tabs.get(i).token)) {
						found = true;
					}
				}
				if (found) {
					for (int i = 0; i < tabs.size(); i++) {
						tabs.get(i).setSelected(tabs.get(i).inBounds(e.getPoint(), tabs.get(i).token));
					}	
				}		
			}
			if (e.getPoint().y>ribbonTabHeight) {
				for (int t = 0; t < tabs.size(); t++) {

					Tab tab = tabs.get(t);
					if (tab.isSelected()) {
						for (int b = 0; b < tabs.get(t).getButtons().size(); b++) {
							Button but = tabs.get(t).getButtons().get(b);
							but.setPressed(false);
							if (but.inBounds(e.getPoint(), but.token)) {
								if(!but.hasDropDown() || but.hoverTop) {
									but.fireAction(new ActionEvent(RibbonBar.this, (int) ActionEvent.MOUSE_EVENT_MASK, "onClick"));
								}
								if (but.hasDropDown() && !but.hoverTop) {
									pop.removeAll();
									for (int i=0; i<but.getSubMenuList().size(); i++) {
										if (but.getSubMenuList().get(i) instanceof JMenuItem) {
											pop.add( (JMenuItem) but.getSubMenuList().get(i));
										}										 
										if (but.getSubMenuList().get(i) instanceof  JCheckBoxMenuItem) {
											pop.add( (JCheckBoxMenuItem) but.getSubMenuList().get(i));
										}
										
										
									}
									pop.show(RibbonBar.this, but.x, but.y+but.height);
								}
							}
						}
					}
				}
			}
	
			repaint();	
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getPoint().y>ribbonTabHeight) {
				for (int t = 0; t < tabs.size(); t++) {
					Tab tab = tabs.get(t);
					if (tab.isSelected()) {
						for (int b = 0; b < tabs.get(t).getButtons().size(); b++) {
							Button but = tabs.get(t).getButtons().get(b);
							but.setPressed(but.inBounds(e.getPoint(), but.token));
						}
					}
				}
			}
			repaint();
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			clearFlag();
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
		
		}
		
	};
	
}
