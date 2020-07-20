package plotter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

/**
 * 2 dimenziós adatpontok kirajzolására kialakított kirajzoló 
 * descartes koordináta rendszer-en
 * @author Csekme Krisztián |KSQFYZ
 */
public class Plotter extends Component {

	private static final long serialVersionUID = -4311245785877561793L;
	
	public static enum PoligonForm { CIRCLE, CROSS, SQUARE, TRIANGLE, DOT }
	
	// érték tartományok
    public Range horizontal;
    public Range vertical;

    //margók
    private int mrTop = 30;
    private int mrLeft = 30;
    private int mrRight = 30;
    private int mrBottom = 30;
    
    private int polygonSize = 1;
    private float polygonThin = 1.0f;

    //kijelző megjelenítendő tulajdonneve
    private String title = null;
    private String yTitle = null;
    private String xTitle = null;
    
    //Színbeállítások
    private Color clYTitleColor	= new Color(50, 50, 50);    
    private Color clXTitleColor	= new Color(50, 50, 50);      
    private Color clBackground	= new Color(50, 50, 50);
    private Color clPlotterBorder = new Color(50, 50, 50);
    private Color clPlotterShadow = new Color(50, 50, 50); 
    private Color clPlotterArea = new Color(130, 130, 130);
    private Color clPlotterForeground = Color.LIGHT_GRAY;
    private Color clFontColor = Color.LIGHT_GRAY;
    
    private float yTitleFontSize = 12.0f;
    private float xTitleFontSize = 12.0f;
    private float TitleFontSize = 12.0f;
    
    
    private boolean showLegend = false;
    private boolean showDiv = false;
    
    
    
    //Adat szériák (Layer)
    public List<Series> series = new ArrayList<>();

    /**
     * Konstruktor
     */
    public Plotter() {
        horizontal = new Range(-100, 100);
        vertical = new Range(-5, 5);
    }

    /**
     * Rétegek törlése (adat sorozatok)
     */
    public void removeAllLayer() {
        series.clear();
    }

    /**
     * Adatsorozat hozzáadása
     * @return 
     */
    public Series addLayer() {
        Series l = new Series();
        series.add(l);
        return l;
    }
    
	/**
	 * Rotate text
	 * @param g2d
	 * @param x
	 * @param y
	 * @param angle
	 * @param text
	 */
	public static void drawRotate(Graphics2D g2d, int x, int y, int angle, String text) {
		g2d.translate((float) x, (float) y);
		g2d.rotate(Math.toRadians(angle));
		g2d.drawString(text, 0, 0);
		g2d.rotate(-Math.toRadians(angle));
		g2d.translate(-(float) x, -(float) y);
	}
    
    /**
     * Seria hozzáadása ez esetben csak egy lehet érvényben amit aktuálisan hozzáadtunk
     * @param series
     */
    public void setSeries(Series serie) {
    	if (series.size()==0) {
    		series.add(serie);
    	} else {
    		series.clear();
    		series.add(serie);  	    		
    	}
    	repaint();
    }

    /**
     * Kirajzolást végző függvény
     * @param gr 
     */
    @Override
    public void paint(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        //Élsimítás
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       
        g.setColor(clBackground);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(clPlotterShadow);
        g.fillRect(mrLeft + 8, mrTop + 8, getWidth() - mrLeft - mrRight, getHeight() - mrTop - mrBottom);
  
        g.setColor(clPlotterArea);
        g.fillRect(mrLeft, mrTop, getWidth() - mrLeft - mrRight, getHeight() - mrTop - mrBottom);

        g.setColor(clPlotterBorder);
        g.drawRect(mrLeft, mrTop, getWidth() - mrLeft - mrRight, getHeight() - mrTop - mrBottom);
        
        if (yTitle!=null) {
        	g.setFont(getFont().deriveFont(yTitleFontSize));
        	g.setColor(clYTitleColor);
        	drawRotate(g, mrLeft - 12, getHeight()/2 + g.getFontMetrics().stringWidth(yTitle)/2, -90, yTitle);
        }

        if (xTitle!=null) {
        	g.setFont(getFont().deriveFont(xTitleFontSize));
        	g.setColor(clXTitleColor);
            g.drawString(xTitle, mrLeft + (getWidth() - mrLeft - mrRight)/2 - g.getFontMetrics().stringWidth(xTitle)/2,  getHeight()  - mrBottom +30);
        }
        
        if (title != null) {
            g.setColor(clFontColor);
            g.setFont(getFont().deriveFont(TitleFontSize));
            g.drawString(title, mrLeft, 30);
        }

        horizontal.calculateDiv(getWidth() - mrLeft - mrRight);
        vertical.calculateDiv(getHeight() - mrTop - mrBottom);

        //origo
        if (horizontal.hasOrigo()) {
            g.setColor(clPlotterForeground);
			if (horizontal.pixelFrom(0) != null) {
				g.drawLine(mrLeft + horizontal.pixelFrom(0).intValue(), mrTop,
						mrLeft + horizontal.pixelFrom(0).intValue(), getHeight() - mrBottom);
			}
		}
		if (vertical.hasOrigo()) {
			g.setColor(clPlotterForeground);
			if (vertical.pixelFrom(0) != null) {
				g.drawLine(mrLeft, getHeight() - vertical.pixelFrom(0).intValue() - mrBottom, getWidth() - mrRight,
						getHeight() - vertical.pixelFrom(0).intValue() - mrBottom);
			}
		}
		if (showDiv) {
			for (double i = vertical.getFrom(); i < vertical.getTo(); i++) {
				float fraction = (float) (i - (int) i);
				if (fraction == 0) {
					g.setColor(clPlotterForeground);
					g.drawLine(mrLeft + horizontal.pixelFrom(0).intValue() - 5,
							getHeight() - vertical.pixelFrom(i).intValue() - mrBottom,
							mrLeft + horizontal.pixelFrom(0).intValue() + 5,
							getHeight() - vertical.pixelFrom(i).intValue() - mrBottom);
					g.setColor(new Color(clPlotterForeground.getRed(), clPlotterForeground.getGreen(),
							clPlotterForeground.getBlue(), 50));
					g.drawLine(mrLeft, getHeight() - vertical.pixelFrom(i).intValue() - mrBottom, getWidth() - mrRight,
							getHeight() - vertical.pixelFrom(i).intValue() - mrBottom);

				}
			}

			for (double i = horizontal.getFrom(); i < horizontal.getTo(); i++) {
				float fraction = (float) (i - (int) i);
				if (fraction == 0) {
					g.setColor(clPlotterForeground);
					g.drawLine(mrLeft + horizontal.pixelFrom(i).intValue(),
							getHeight() - vertical.pixelFrom(0).intValue() - mrBottom - 5,
							mrLeft + horizontal.pixelFrom(i).intValue(),
							getHeight() - vertical.pixelFrom(0).intValue() - mrBottom + 5);
					g.setColor(new Color(clPlotterForeground.getRed(), clPlotterForeground.getGreen(),
							clPlotterForeground.getBlue(), 50));
					g.drawLine(mrLeft + horizontal.pixelFrom(i).intValue(), mrTop,
							mrLeft + horizontal.pixelFrom(i).intValue(), getHeight() - mrBottom);

				}
			}
		}
        g.setColor(Color.RED);
        for (int l = 0; l < series.size(); l++) {
            Series layer = series.get(l);
            g.setColor(layer.getClPoligons());
            for (int i = 0; i < layer.polygons.size(); i++) {

                if (i < layer.polygons.size()) {
                	Point poligon = layer.polygons.get(i);
                	if (horizontal.pixelFrom(poligon.x)!=null && vertical.pixelFrom(poligon.y)!=null ) {
                		
                		//Ha nem átlátszó akkor rárajzolunk egy téglalapot a plotter háttér színével
                		if (!poligon.opaque) {
                			g.setColor(clPlotterArea);
                			g.setStroke(new BasicStroke(1.0f));
                			g.fillRect(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 - polygonSize/2 - 1, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 - polygonSize/2 - 1,	
                					polygonSize + 2 + (int)polygonThin,
                					polygonSize + 2 + (int)polygonThin) ;
                        
                		}
                		if (poligon.hasColor()) {
                			g.setColor(poligon.getColor());
                		} else {
                			g.setColor(layer.getClPoligons());
                		}
                		
                		int psyze = polygonSize;
                		if (poligon.getSize()!=null) {
                			psyze = poligon.getSize().intValue();
                		}
                		
                		switch (poligon.form) {
                		
                		
                		
                		case DOT: {
                			g.setStroke( new BasicStroke(polygonThin) );
                			g.fillOval(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 - psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 - psyze/2,	
                					psyze,
                					psyze);
                            break;	
                		}
                		case CIRCLE:
                			g.setStroke( new BasicStroke(polygonThin) );
                			g.drawOval(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 - psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 - psyze/2,	
                					psyze,
                					psyze);
                            			
                			break;
                		case CROSS:
                			g.setStroke( new BasicStroke(polygonThin) );
                			g.drawLine(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 - psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 ,	
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 + psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1	
                				    );
                			g.drawLine(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 - psyze/2,	
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 + psyze/2	
                				    );
                		    break;
                		case TRIANGLE:
                			g.setStroke( new BasicStroke(polygonThin) );
                			g.drawLine(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 - psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 + psyze/2,	
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 + psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 + psyze/2	
                				    );
                			g.drawLine(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 - psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 + psyze/2,	
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 - psyze/2
                				    );
                			g.drawLine(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 , 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 - psyze/2,	
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 + psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 + psyze/2	
                				    );
                			
                			break;
                		case SQUARE:
                			g.setStroke( new BasicStroke(polygonThin) );
                			g.drawRect(
                					mrLeft + horizontal.pixelFrom(poligon.x).intValue() - 1 - psyze/2, 
                					getHeight() - vertical.pixelFrom(poligon.y).intValue() - mrBottom - 1 - psyze/2,	
                					psyze,
                					psyze);
                        
                			break;
                		}
                		if (poligon.showValue) {
                			Color c = g.getColor();
                			g.setColor(new Color(170,170,170, 200));
                			g.fillRoundRect( 
                					mrLeft 
                					+ horizontal.pixelFrom(poligon.x).intValue() 
                					- g.getFontMetrics().stringWidth(poligon.toString())/2 
                					- polygonSize/2 - 4,
                					
                					getHeight() 
                					- vertical.pixelFrom(poligon.y).intValue() 
                					- mrBottom 
                					- 8 
                					- polygonSize/2
                					-12
                					, 
                					
                					g.getFontMetrics().stringWidth(poligon.toString()) + 2*4, 
                					16, 
                					4, 
                					4);
                			g.setColor(c);
                			g.drawString( poligon.toString() , 
                					
                					mrLeft 
                					+ horizontal.pixelFrom(poligon.x).intValue() 
                					- g.getFontMetrics().stringWidth(poligon.toString())/2 
                					- polygonSize/2, 
                					
                					getHeight() 
                					- vertical.pixelFrom(poligon.y).intValue() 
                					- mrBottom 
                					- 8 
                					- polygonSize/2
                					
                			);
                		}
                	}
                }
            }
        }

        if (showLegend) {
            float font_size = 10f;
            g.setFont(getFont().deriveFont(font_size));
            int s = series.size();
            int s_l = 0;
            for (int l = 0; l < s; l++) {
                if (series.get(l).getTitle() != null && g.getFontMetrics().stringWidth(series.get(l).getTitle()) > s_l) {
                    s_l = g.getFontMetrics().stringWidth(series.get(l).getTitle());
                }
            }
            g.setColor(new Color(clPlotterForeground.getRed(), clPlotterForeground.getGreen(), clPlotterForeground.getBlue(), 150));
            int _border = 15;
            int _left = getWidth() - mrRight - s_l - 80 -15;
            int _top = getHeight() - mrBottom - s * 24 - 60 -15;
            
            g.fillRect(_left, _top, s_l + 40 + _border*2, s * 28 + _border*2);
            for (int l = 0; l < s; l++) {
                if (series.get(l).getTitle() != null) {
                    g.setColor(new Color(50, 50, 50));
                    g.fillOval(_left + 4 - 1 + _border, 6 + l * 6 + l * 20 + _top - 1 + _border, 18, 18);
                    g.setColor(series.get(l).getClPoligons());
                    g.fillOval(_left + 4 + _border, 6 + l * 6 + l * 20 + _top + _border, 16, 16);
                    g.setColor(new Color(50, 50, 50));
                    g.drawString(series.get(l).getTitle(), _left + 30 + _border, 17 + l * 26 + _top + _border);
                }
            }

        }
    }

    //Getter-Setter függvények
    
    public Range getHorizontal() {
        return horizontal;
    }
    
    
    public void setHorizontal(Range horizontal) {
        this.horizontal = horizontal;
    }

    public Range getVertical() {
        return vertical;
    }

    public void setVertical(Range vertical) {
        this.vertical = vertical;
    }

    public int getMrTop() {
        return mrTop;
    }

    public void setMrTop(int mrTop) {
        this.mrTop = mrTop;
    }

    public int getMrLeft() {
        return mrLeft;
    }

    public void setMrLeft(int mrLeft) {
        this.mrLeft = mrLeft;
    }

    public int getMrRight() {
        return mrRight;
    }

    public void setMrRight(int mrRight) {
        this.mrRight = mrRight;
    }

    public int getMrBottom() {
        return mrBottom;
    }

    public void setMrBottom(int mrBottom) {
        this.mrBottom = mrBottom;
    }

    public Color getClPlotterBorder() {
        return clPlotterBorder;
    }

    public void setClPlotterBorder(Color clPlotterBorder) {
        this.clPlotterBorder = clPlotterBorder;
    }

    public Color getClPlotterArea() {
        return clPlotterArea;
    }

    public void setClPlotterArea(Color clPlotterArea) {
        this.clPlotterArea = clPlotterArea;
    }

    public Color getClPlotterForeground() {
        return clPlotterForeground;
    }

    public void setClPlotterForeground(Color clPlotterForeground) {
        this.clPlotterForeground = clPlotterForeground;
    }

    public List<Series> getLayers() {
        return series;
    }

    public void setLayers(List<Series> layers) {
        this.series = layers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getClFontColor() {
        return clFontColor;
    }

    public void setClFontColor(Color clFontColor) {
        this.clFontColor = clFontColor;
    }

    public boolean isShowLegend() {
        return showLegend;
    }

    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }
    
    public void setClBackground(Color color) {
    	this.clBackground = color;
    }

    public void setClPlotterShadow(Color color) {
    	this.clPlotterShadow = color;
    }
    
    
    
    public String getyTitle() {
		return yTitle;
	}

	public void setyTitle(String yTitle) {
		this.yTitle = yTitle;
	}

	public float getyTitleFontSize() {
		return yTitleFontSize;
	}

	public void setyTitleFontSize(float yTitleFontSize) {
		this.yTitleFontSize = yTitleFontSize;
	}

	public void setClYTitleColor(Color clYTitleColor) {
		this.clYTitleColor = clYTitleColor;
	}
	
	

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public Color getClXTitleColor() {
		return clXTitleColor;
	}

	public void setClXTitleColor(Color clXTitleColor) {
		this.clXTitleColor = clXTitleColor;
	}

	public float getxTitleFontSize() {
		return xTitleFontSize;
	}

	public void setxTitleFontSize(float xTitleFontSize) {
		this.xTitleFontSize = xTitleFontSize;
	}
	
	

	public float getTitleFontSize() {
		return TitleFontSize;
	}

	public void setTitleFontSize(float titleFontSize) {
		TitleFontSize = titleFontSize;
	}
	
	

	public int getPolygonSize() {
		return polygonSize;
	}

	public void setPolygonSize(int polygonSize) {
		this.polygonSize = polygonSize;
	}

	public float getPolygonThin() {
		return polygonThin;
	}

	public void setPolygonThin(float polygonThin) {
		this.polygonThin = polygonThin;
	}
	
	

	public boolean isShowDiv() {
		return showDiv;
	}

	public void setShowDiv(boolean showDiv) {
		this.showDiv = showDiv;
	}

	public void setMargin(int margin) {
        mrBottom = margin;
        mrTop = margin;
        mrLeft = margin;
        mrRight = margin;
    }
}
