package framework;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import framework.ribbonmenu.Util;
/**
 * MDI gyermek ablak alsó pannelje, egérrel skálázható a jobb alsó saroktól
 * @author Csekme Krisztián | KSQFYZ
 */
public class BottomPanel extends JPanel {

	private static final long serialVersionUID = -5809930985199181908L;
	final int height = 32;
    final int space = 4;
    private final static String serie = "ABCDEFGHIJKLMNOPQRSTWZXYabcdefghijklmneopqrstzyxwv0123456789#&@{}*";

    public static enum color_tokens {
        bottom_panel_background, bottom_panel_foreground, bottom_panel_divider, bottom_panel_strip, bottom_panel_dot
    }
    
    private Map<color_tokens, Color> colors;
    private List<BottomPanelElement> elements;
    
    int w = 0, h = 0;
	int startX = 0;
	int startY = 0;

	private Font font;
	
    /**
     * Konstruktor
     */
    public BottomPanel() {
        colors = new HashMap<>();
        elements = new ArrayList<>();
        font = Util.loadFont("/resources/Cabin-Regular.ttf", 12f);
        colors.put(color_tokens.bottom_panel_background, new Color(240, 240, 240));
        colors.put(color_tokens.bottom_panel_foreground, new Color(80, 80, 80));
        colors.put(color_tokens.bottom_panel_strip, new Color(215, 215, 215));
        colors.put(color_tokens.bottom_panel_divider, new Color(215, 215, 215));
        colors.put(color_tokens.bottom_panel_dot, new Color(191,191,191));

        setSize(new Dimension(getWidth(), height));
    }
    
    boolean canResize = false;
    
    public void setParent(JInternalFrame frame) {
    	frame.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			super.mousePressed(e);
    			startX = e.getX();
    			startY = e.getY();
    			w = frame.getWidth();
    			h = frame.getHeight();
    			if (	( e.getX() < frame.getWidth() && e.getX() > frame.getWidth()-10) 
    					&& (e.getY() < frame.getHeight() && e.getY()>frame.getHeight()-10)
    					) {
    				canResize = true;
    			}
    		}
    		@Override
    		public void mouseReleased(MouseEvent e) {
    			super.mouseReleased(e);
    			canResize = false;
    		}
    		
		});
    	
    	frame.addMouseMotionListener(new MouseAdapter() {
    		
    		@Override
    		public void mouseMoved(MouseEvent e) {
    			super.mouseMoved(e);	
    			if (	( e.getX() < frame.getWidth() && e.getX() > frame.getWidth()-10) 
    					&& (e.getY() < frame.getHeight() && e.getY()>frame.getHeight()-10)
    					) {
    				 
    				setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
    				
    			} else {
    				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    			}
    			
    		}
    		
    		@Override
    		public void mouseDragged(MouseEvent e) {
    			super.mouseDragged(e);
    			if (canResize) {
    				frame.setSize(w +  ( e.getX() - startX), h + (e.getY() - startY));
    			}
    		}
    		
    	});
    	
    	
    }
    
    @Override
    public void paint(Graphics gd) {
        Graphics2D g = (Graphics2D) gd;
        
		if (font!=null) { 
			g.setFont(font);
		}
  
        //rendering hints
        {
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
       
        g.setColor(colors.get(color_tokens.bottom_panel_background));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(colors.get(color_tokens.bottom_panel_strip));
        g.drawLine(0, 0, getWidth(), 0);
        
        //right bottom corner
        {
            g.setColor(colors.get(color_tokens.bottom_panel_dot));
            g.fillRect(getWidth() - 4, getHeight() - 10, 2, 2);
            g.fillRect(getWidth() - 4, getHeight() - 7, 2, 2);
            g.fillRect(getWidth() - 7, getHeight() - 7, 2, 2);
            g.fillRect(getWidth() - 4, getHeight() - 4, 2, 2);
            g.fillRect(getWidth() - 7, getHeight() - 4, 2, 2);
            g.fillRect(getWidth() - 10, getHeight() - 4, 2, 2);
        }
        
        int offset = 0;
        int last_divider = 0;
        for (int i = 0; i < elements.size(); i++) {
            BottomPanelElement e = elements.get(i);
            switch (e.getType()) {
                case Label:
                    offset += space;
                    g.setColor(colors.get(color_tokens.bottom_panel_foreground));
                    int w = g.getFontMetrics().stringWidth(e.getCaption());
                    g.drawString(e.getCaption(), offset, 16);
                    offset += w;
                    break;
                case Button:
                    break;
                case Divider:
                    if (!((offset - last_divider) <= space)) {
                        offset += space;
                        g.setColor(colors.get(color_tokens.bottom_panel_divider));
                        g.drawLine(offset, 5, offset, height - 12);
                        g.setColor(new Color(250, 250, 250));
                        offset += 1;
                        g.drawLine(offset, 5, offset, height - 12);
                        last_divider = offset;
                    }
                    break;
            }
        }

    }

    public static String generateToken(int length) {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(serie.charAt(rnd.nextInt(serie.length() - 1)));
        }
        return sb.toString();
    }

    public BottomPanelElement addLabel(String caption) {
        BottomPanelElement e = new BottomPanelElement(generateToken(10), BottomPanelElement.Type.Label);
        e.parent = this;
        e.setCaption(caption);
       
        elements.add(e);
        return e;
    }

    public BottomPanelElement addButton(String caption) {
        BottomPanelElement e = new BottomPanelElement(generateToken(10), BottomPanelElement.Type.Button);
        e.parent = this;
        e.setCaption(caption);
        elements.add(e);
        return e;
    }

    public void addDivider() {
        elements.add(new BottomPanelElement(generateToken(10), BottomPanelElement.Type.Divider));
    }

}
