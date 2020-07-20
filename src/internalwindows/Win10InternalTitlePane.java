package internalwindows;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class Win10InternalTitlePane extends BasicInternalFrameTitlePane {

    private static final long serialVersionUID = 1L;

    static final int TITLE_HEIGHT = 28;
    static final int TITLE_BUTTON_WIDTH = 46;
    static final int TITLE_BUTTON_HEIGHT = 24;

    static int BORDER_WIDTH = 1;
    
    
    static Color COLOR = new Color(255, 255, 255);
    static Color INACTIVE_COLOR = new Color(240,240,240);
    static Color TITLE_TEXT_COLOR = new Color(255, 255, 255);
    static Color TITLE_INACTIVE_TEXT_COLOR = new Color(20,20,10);
    static Color BORDER_COLOR = new Color ( 200, 200, 200 );
    static Color INACTIVE_BORDER_COLOR = new Color(150, 150, 150); 
    static Color BUTTON_COLOR = new Color(255,255,255);
    static Color BUTTON_HOVER_COLOR = new Color(30,30,30,20);  
    static Color BUTTON_TEXT_COLOR = new Color(30,30,30);
    static Color BUTTON_INACTIVE_TEXT_COLOR = new Color(30,30,30);

    static Color CLOSE_BUTTON_COLOR = new Color(232, 17, 35);

    static final BufferedImage closeBtnImg;
    final static BufferedImage closeBtnImgHover;
    final static BufferedImage closeBtnImgPressed;
    final static BufferedImage closeBtnImgInactive;

    final static BufferedImage maximizableBtnImg;
    final static BufferedImage maximizableBtnImgHover;
    final static BufferedImage maximizableBtnImgPressed;
    final static BufferedImage maximizableBtnImgInactive;

    final static BufferedImage minimizibleBtnImg;
    final static BufferedImage minimizibleBtnImgHover;
    final static BufferedImage minimizibleBtnImgPressed;
    final static BufferedImage minimizibleBtnImgInactive;

    final static BufferedImage maximizableMBtnImg;
    final static BufferedImage maximizableMBtnImgHover;
    final static BufferedImage maximizableMBtnImgPressed;
    final static BufferedImage maximizableMBtnImgInactive;

    static {
        { // Close button

            closeBtnImg = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D closeBtnImgG = closeBtnImg.createGraphics();
            closeBtnImgG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            closeBtnImgG.setColor(BUTTON_COLOR);
            closeBtnImgG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            closeBtnImgG.setColor(BUTTON_TEXT_COLOR);
            closeBtnImgG.setStroke(new BasicStroke(1f));
            closeBtnImgG.drawLine(19, 8, TITLE_BUTTON_WIDTH - 19, TITLE_BUTTON_HEIGHT - 8);
            closeBtnImgG.drawLine(TITLE_BUTTON_WIDTH - 19, 8, 19, TITLE_BUTTON_HEIGHT - 8);

            closeBtnImgHover = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D closeBtnImgHoverG = closeBtnImgHover.createGraphics();
            closeBtnImgHoverG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            closeBtnImgHoverG.setColor(CLOSE_BUTTON_COLOR);
            closeBtnImgHoverG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            closeBtnImgHoverG.setColor(Color.WHITE);
            closeBtnImgHoverG.setStroke(new BasicStroke(1f));
            closeBtnImgHoverG.drawLine(19, 8, TITLE_BUTTON_WIDTH - 19, TITLE_BUTTON_HEIGHT - 8);
            closeBtnImgHoverG.drawLine(TITLE_BUTTON_WIDTH - 19, 8, 19, TITLE_BUTTON_HEIGHT - 8);

            closeBtnImgPressed = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D closeBtnImgPressedG = closeBtnImgPressed.createGraphics();
            closeBtnImgPressedG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            closeBtnImgPressedG.setColor(CLOSE_BUTTON_COLOR.darker());
            closeBtnImgPressedG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            closeBtnImgPressedG.setColor(Color.WHITE);
            closeBtnImgPressedG.setStroke(new BasicStroke(1f));
            closeBtnImgPressedG.drawLine(19, 8, TITLE_BUTTON_WIDTH - 19, TITLE_BUTTON_HEIGHT - 8);
            closeBtnImgPressedG.drawLine(TITLE_BUTTON_WIDTH - 19, 8, 19, TITLE_BUTTON_HEIGHT - 8);

            closeBtnImgInactive = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D closeBtnImgInactiveG = closeBtnImgInactive.createGraphics();
            closeBtnImgInactiveG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            closeBtnImgInactiveG.setColor(INACTIVE_COLOR);
            closeBtnImgInactiveG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            closeBtnImgInactiveG.setColor(TITLE_INACTIVE_TEXT_COLOR);
            closeBtnImgInactiveG.setStroke(new BasicStroke(1f));
            closeBtnImgInactiveG.drawLine(19, 8, TITLE_BUTTON_WIDTH - 19, TITLE_BUTTON_HEIGHT - 8);
            closeBtnImgInactiveG.drawLine(TITLE_BUTTON_WIDTH - 19, 8, 19, TITLE_BUTTON_HEIGHT - 8);
        }
        { // Maximize button
            maximizableBtnImg = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableBtnImgG = maximizableBtnImg.createGraphics();
            maximizableBtnImgG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableBtnImgG.setColor(BUTTON_COLOR);
            maximizableBtnImgG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableBtnImgG.setColor(BUTTON_TEXT_COLOR);
            maximizableBtnImgG.setStroke(new BasicStroke(1f));
            maximizableBtnImgG.drawRect(19, 8, 8, 8);

            maximizableBtnImgHover = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableBtnImgHoverG = maximizableBtnImgHover.createGraphics();
            maximizableBtnImgHoverG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableBtnImgHoverG.setColor(BUTTON_COLOR);
            maximizableBtnImgHoverG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableBtnImgHoverG.setColor(BUTTON_TEXT_COLOR);
            maximizableBtnImgHoverG.setStroke(new BasicStroke(1f));
            maximizableBtnImgHoverG.drawRect(19, 8, 8, 8);
            maximizableBtnImgHoverG.setColor(BUTTON_HOVER_COLOR);
            maximizableBtnImgHoverG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);

            maximizableBtnImgPressed = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableBtnImgPressedG = maximizableBtnImgPressed.createGraphics();
            maximizableBtnImgPressedG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableBtnImgPressedG.setColor(BUTTON_COLOR.darker());
            maximizableBtnImgPressedG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableBtnImgPressedG.setColor(BUTTON_TEXT_COLOR);
            maximizableBtnImgPressedG.setStroke(new BasicStroke(1f));
            maximizableBtnImgPressedG.drawRect(19, 8, 8, 8);

            maximizableBtnImgInactive = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableBtnImgInactiveG = maximizableBtnImgInactive.createGraphics();
            maximizableBtnImgInactiveG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableBtnImgInactiveG.setColor(INACTIVE_COLOR);
            maximizableBtnImgInactiveG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableBtnImgInactiveG.setColor(TITLE_INACTIVE_TEXT_COLOR);
            maximizableBtnImgInactiveG.setStroke(new BasicStroke(1f));
            maximizableBtnImgInactiveG.drawRect(19, 8, 8, 8);

        }
        {
            // Minimized
            minimizibleBtnImg = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT, BufferedImage.TYPE_INT_ARGB);
            Graphics2D minimizibleBtnImgG = minimizibleBtnImg.createGraphics();
            minimizibleBtnImgG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            minimizibleBtnImgG.setColor(BUTTON_COLOR);
            minimizibleBtnImgG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            minimizibleBtnImgG.setColor(BUTTON_TEXT_COLOR);
            minimizibleBtnImgG.setStroke(new BasicStroke(1f));
            minimizibleBtnImgG.drawLine(19, TITLE_BUTTON_HEIGHT / 2, TITLE_BUTTON_WIDTH - 19, TITLE_BUTTON_HEIGHT / 2);

            minimizibleBtnImgHover = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D minimizibleBtnImgGHover = minimizibleBtnImgHover.createGraphics();
            minimizibleBtnImgGHover.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            minimizibleBtnImgGHover.setColor(BUTTON_COLOR);
            minimizibleBtnImgGHover.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            minimizibleBtnImgGHover.setColor(BUTTON_TEXT_COLOR);
            minimizibleBtnImgGHover.setStroke(new BasicStroke(1f));
            minimizibleBtnImgGHover.drawLine(19, TITLE_BUTTON_HEIGHT / 2, TITLE_BUTTON_WIDTH - 19,
                    TITLE_BUTTON_HEIGHT / 2);
            minimizibleBtnImgGHover.setColor(BUTTON_HOVER_COLOR);
            minimizibleBtnImgGHover.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);

            minimizibleBtnImgPressed = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D minimizibleBtnImgGPressed = minimizibleBtnImgPressed.createGraphics();
            minimizibleBtnImgGPressed.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            minimizibleBtnImgGPressed.setColor(BUTTON_COLOR.darker());
            minimizibleBtnImgGPressed.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            minimizibleBtnImgGPressed.setColor(BUTTON_TEXT_COLOR);
            minimizibleBtnImgGPressed.setStroke(new BasicStroke(1f));
            minimizibleBtnImgGPressed.drawLine(19, TITLE_BUTTON_HEIGHT / 2, TITLE_BUTTON_WIDTH - 19,
                    TITLE_BUTTON_HEIGHT / 2);

            minimizibleBtnImgInactive = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D minimizibleBtnImgInactiveG = minimizibleBtnImgInactive.createGraphics();
            minimizibleBtnImgInactiveG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            minimizibleBtnImgInactiveG.setColor(INACTIVE_COLOR);
            minimizibleBtnImgInactiveG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            minimizibleBtnImgInactiveG.setColor(TITLE_INACTIVE_TEXT_COLOR);
            minimizibleBtnImgInactiveG.setStroke(new BasicStroke(1f));
            minimizibleBtnImgInactiveG.drawLine(19, TITLE_BUTTON_HEIGHT / 2, TITLE_BUTTON_WIDTH - 19,
                    TITLE_BUTTON_HEIGHT / 2);
        }
        { // Maximize M button
            maximizableMBtnImg = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableMBtnImgG = maximizableMBtnImg.createGraphics();
            maximizableMBtnImgG.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableMBtnImgG.setColor(BUTTON_COLOR);
            maximizableMBtnImgG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableMBtnImgG.setColor(BUTTON_TEXT_COLOR);
            maximizableMBtnImgG.setStroke(new BasicStroke(1f));
            maximizableMBtnImgG.drawRect(20, 8, 8, 7);
            maximizableMBtnImgG.setColor(BUTTON_COLOR);
            maximizableMBtnImgG.fillRect(17, 10, 8, 7);
            maximizableMBtnImgG.setColor(BUTTON_TEXT_COLOR);
            maximizableMBtnImgG.drawRect(17, 10, 8, 7);

            maximizableMBtnImgHover = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableMBtnImgHoverG = maximizableMBtnImgHover.createGraphics();
            maximizableMBtnImgHoverG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableMBtnImgHoverG.setColor(BUTTON_COLOR);
            maximizableMBtnImgHoverG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableMBtnImgHoverG.setColor(BUTTON_TEXT_COLOR);
            maximizableMBtnImgHoverG.setStroke(new BasicStroke(1f));
            maximizableMBtnImgHoverG.drawRect(20, 8, 8, 7);
            maximizableMBtnImgHoverG.setColor(BUTTON_COLOR);
            maximizableMBtnImgHoverG.fillRect(17, 10, 8, 7);
            maximizableMBtnImgHoverG.setColor(BUTTON_TEXT_COLOR);
            maximizableMBtnImgHoverG.drawRect(17, 10, 8, 7);
            maximizableMBtnImgHoverG.setColor(BUTTON_HOVER_COLOR);
            maximizableMBtnImgHoverG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);

            maximizableMBtnImgPressed = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableMBtnImgPressedG = maximizableMBtnImgPressed.createGraphics();
            maximizableMBtnImgPressedG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableMBtnImgPressedG.setColor(BUTTON_COLOR.darker());
            maximizableMBtnImgPressedG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableMBtnImgPressedG.setColor(BUTTON_TEXT_COLOR);
            maximizableMBtnImgPressedG.setStroke(new BasicStroke(1f));
            maximizableMBtnImgPressedG.drawRect(20, 8, 8, 7);
            maximizableMBtnImgPressedG.setColor(BUTTON_COLOR.darker());
            maximizableMBtnImgPressedG.fillRect(17, 10, 8, 7);
            maximizableMBtnImgPressedG.setColor(BUTTON_TEXT_COLOR);
            maximizableMBtnImgPressedG.drawRect(17, 10, 8, 7);

            maximizableMBtnImgInactive = new BufferedImage(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D maximizableMBtnImgInactiveG = maximizableMBtnImgInactive.createGraphics();
            maximizableMBtnImgInactiveG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            maximizableMBtnImgInactiveG.setColor(INACTIVE_COLOR);
            maximizableMBtnImgInactiveG.fillRect(0, 0, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
            maximizableMBtnImgInactiveG.setColor(TITLE_INACTIVE_TEXT_COLOR);
            maximizableMBtnImgInactiveG.setStroke(new BasicStroke(1f));
            maximizableMBtnImgInactiveG.drawRect(20, 8, 8, 7);
            maximizableMBtnImgInactiveG.setColor(INACTIVE_COLOR);
            maximizableMBtnImgInactiveG.fillRect(17, 10, 8, 7);
            maximizableMBtnImgInactiveG.setColor(TITLE_INACTIVE_TEXT_COLOR);
            maximizableMBtnImgInactiveG.drawRect(17, 10, 8, 7);

        }
    }

    private Handler handler;

    public Win10InternalTitlePane(JInternalFrame f) {
        super(f);
        selectedTitleColor = COLOR;
        notSelectedTitleColor = BORDER_COLOR;
     
                
        
        closeButton.setBorder(null);
        iconButton.setBorder(null);
        maxButton.setBorder(null);
     
        closeButton.addMouseListener(new MouseAdapter() {

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseReleased(MouseEvent e) {
                if (frame.isSelected()) {
                    closeButton.setIcon(new ImageIcon(closeBtnImg));
                } else {
                    closeButton.setIcon(new ImageIcon(closeBtnImgInactive));
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mousePressed(MouseEvent e) {
                if (frame.isSelected()) {
                    closeButton.setIcon(new ImageIcon(closeBtnImgPressed));
                } else {
                    closeButton.setIcon(new ImageIcon(closeBtnImgInactive));
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseExited(MouseEvent e) {
                if (frame.isSelected()) {
                    closeButton.setIcon(new ImageIcon(closeBtnImg));
                } else {
                    closeButton.setIcon(new ImageIcon(closeBtnImgInactive));
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseEntered(MouseEvent e) {
                if (frame.isSelected()) {
                    closeButton.setIcon(new ImageIcon(closeBtnImgHover));
                } else {
                    closeButton.setIcon(new ImageIcon(closeBtnImgInactive));
                }
            }

        });

        maxButton.addMouseListener(new MouseAdapter() {

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseReleased(MouseEvent e) {
                if (frame.isSelected()) {

                    if (frame.isMaximum()) {
                        maxButton.setIcon(new ImageIcon(maximizableMBtnImg));
                    } else {
                        maxButton.setIcon(new ImageIcon(maximizableBtnImg));
                    }
                } else {
                	
                    //maxButton.setIcon(new ImageIcon(maximizableBtnImgInactive));
                }

            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mousePressed(MouseEvent e) {
                if (frame.isSelected()) {
                    if (frame.isMaximum()) {
                        maxButton.setIcon(new ImageIcon(maximizableMBtnImgPressed));
                    } else {
                        maxButton.setIcon(new ImageIcon(maximizableBtnImgPressed));
                    }
                } else {
                    maxButton.setIcon(new ImageIcon(maximizableBtnImgInactive));
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseExited(MouseEvent e) {
                if (frame.isSelected()) {
                    if (frame.isMaximum()) {
                        maxButton.setIcon(new ImageIcon(maximizableMBtnImg));
                    } else {
                        maxButton.setIcon(new ImageIcon(maximizableBtnImg));
                    }
                } else {
                	if (frame.isMaximum()) {
                		maxButton.setIcon(new ImageIcon(maximizableMBtnImgInactive));
                	} else {
                		maxButton.setIcon(new ImageIcon(maximizableBtnImgInactive));
                	}
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseEntered(MouseEvent e) {
                if (frame.isSelected()) {
                    if (frame.isMaximum()) {
                        maxButton.setIcon(new ImageIcon(maximizableMBtnImgHover));
                    } else {
                        maxButton.setIcon(new ImageIcon(maximizableBtnImgHover));
                    }
                } else {
                	if (frame.isMaximum()) {
                		maxButton.setIcon(new ImageIcon(maximizableMBtnImgInactive));
                	} else {
                		maxButton.setIcon(new ImageIcon(maximizableBtnImgInactive));
                	}
                }
            }

        });

        iconButton.addMouseListener(new MouseAdapter() {

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseReleased(MouseEvent e) {
                if (frame.isSelected()) {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImg));
                } else {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImgInactive));
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mousePressed(MouseEvent e) {
                if (frame.isSelected()) {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImgPressed));
                } else {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImgInactive));
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseExited(MouseEvent e) {
                if (frame.isSelected()) {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImg));
                } else {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImgInactive));
                }
            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void mouseEntered(MouseEvent e) {
                if (frame.isSelected()) {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImgHover));
                } else {
                    iconButton.setIcon(new ImageIcon(minimizibleBtnImgInactive));
                }
            }

        });

        frame.addInternalFrameListener(new InternalFrameListener() {

            @Override
            public void internalFrameOpened(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameIconified(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameDeiconified(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void internalFrameDeactivated(InternalFrameEvent e) {
                frame.setBorder(BorderFactory.createLineBorder(INACTIVE_BORDER_COLOR, BORDER_WIDTH));
                setButtonIcons();
            }

            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                // TODO Auto-generated method stub

            }

            @SuppressWarnings("synthetic-access")
			@Override
            public void internalFrameActivated(InternalFrameEvent e) {
                 frame.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, BORDER_WIDTH));
                setButtonIcons();
            }
        });

        frame.addComponentListener(new ComponentListener() {

            @Override
            public void componentShown(ComponentEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void componentResized(ComponentEvent e) {
                setButtonIcons();

            }

            @Override
            public void componentMoved(ComponentEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void componentHidden(ComponentEvent e) {
                // TODO Auto-generated method stub

            }
        });
    }
    
       /**
    * Invoked from paintComponent.
    * Paints the background of the titlepane.  All text and icons will
    * then be rendered on top of this background.
    * @param g the graphics to use to render the background
    * @since 1.4
    */
    @Override
    public void paintTitleBackground(Graphics g) {
        boolean isSelected = frame.isSelected();

        if(isSelected)
            g.setColor(selectedTitleColor);
        else
            g.setColor(notSelectedTitleColor);
        
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void paintComponent(Graphics ge) {
        // paintTitleBackground(g);
        Graphics2D g = (Graphics2D) ge;
        if (frame.getTitle() != null) {
            boolean isSelected = frame.isSelected();
          
            g.setFont(frame.getFont());
            g.setFont(getFont().deriveFont(12f));
            //g.setFont(getFont().deriveFont(Font.BOLD));
            
            g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB );
            
            if (isSelected) {
                g.setColor(TITLE_TEXT_COLOR);
            } else {
                g.setColor(notSelectedTextColor);
            }

            // Center text vertically.
            FontMetrics fm = g.getFontMetrics();
            int baseline = (getHeight() + fm.getAscent() - fm.getLeading() - fm.getDescent()) / 2;

            int titleX;
            Rectangle r = new Rectangle(0, 0, 0, 0);
            if (frame.isIconifiable()) {
                r = iconButton.getBounds();
            } else if (frame.isMaximizable()) {
                r = maxButton.getBounds();
            } else if (frame.isClosable()) {
                r = closeButton.getBounds();
            }
            int titleW;

            String title = frame.getTitle();
            if (isLeftToRight(frame)) {
                if (r.x == 0) {
                    r.x = frame.getWidth() - frame.getInsets().right;
                }
                titleX = /* menuBar.getX() + menuBar.getWidth() */ +2;
                titleW = r.x - titleX - 3;
                title = getTitle(frame.getTitle(), fm, titleW);
            } else {
                titleX = /* menuBar.getX() */ -2 - g.getFontMetrics().stringWidth(title);
            }
            if (frame.isSelected()) {
                g.setColor(COLOR);
            } else {
                g.setColor(INACTIVE_COLOR);
            }
            g.fillRect(0 + BORDER_WIDTH, 0 + BORDER_WIDTH, getWidth()-BORDER_WIDTH*2, getHeight()); // TODO kulcs!!!

            if (frame.isSelected()) {
                g.setColor(selectedTextColor);
            } else {
                g.setColor(notSelectedTextColor);
            }

            if (frame.getFrameIcon() != null) {

                Image img = ((ImageIcon) frame.getFrameIcon()).getImage();
                g.drawImage(img, 4, 2, 22, 22, this);
            }
        

            g.drawString(title, 32, baseline);
            //SwingUtilities2.drawString(frame, g, title, 32, baseline);
        

        }
    }

    @Override
    public void installDefaults() {
        super.installDefaults();
    }

    @Override
    public void assembleSystemMenu() {
        // menuBar = createSystemMenuBar();
        // windowMenu = createSystemMenu();
        // menuBar.add(windowMenu);
        // addSystemMenuItems(windowMenu);
        enableActions();
    }

    @Override
    protected void addSubComponents() {
        // add(menuBar);
        add(iconButton);
        add(maxButton);
        add(closeButton);
    }

    @Override
    public void addSystemMenuItems(JMenu systemMenu) {
        JMenuItem mi = systemMenu.add(restoreAction);
        mi.setMnemonic(getButtonMnemonic("restore"));
        mi = systemMenu.add(moveAction);
        mi.setMnemonic(getButtonMnemonic("move"));
        mi = systemMenu.add(sizeAction);
        mi.setMnemonic(getButtonMnemonic("size"));
        mi = systemMenu.add(iconifyAction);
        mi.setMnemonic(getButtonMnemonic("minimize"));
        mi = systemMenu.add(maximizeAction);
        mi.setMnemonic(getButtonMnemonic("maximize"));
        systemMenu.add(new JSeparator());
        mi = systemMenu.add(closeAction);
        mi.setMnemonic(getButtonMnemonic("close"));
    }

    public static int getButtonMnemonic(String button) {
        try {
            return Integer.parseInt(UIManager.getString("InternalFrameTitlePane." + button + "Button.mnemonic"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public JMenu createSystemMenu() {
        return new JMenu("    ");
    }

    @Override
    public JMenuBar createSystemMenuBar() {
        menuBar = new SystemMenuBar();
        menuBar.setBorderPainted(true);
        return menuBar;
    }

    @Override
    public void setButtonIcons() {
        super.setButtonIcons();

        // windowMenu.setVisible(false);
        // frame.setFrameIcon(null);
        if (frame.isIcon()) {

        } 
        /*
        else if (frame.isMaximum()) {
            if (frame.isSelected()) {
                maxButton.setIcon(new ImageIcon(maximizableMBtnImg));
            } else {
                maxButton.setIcon(new ImageIcon(maximizableMBtnImgInactive));
            }
        }
        */

        if (frame.isClosable()) {
            if (frame.isSelected()) {
                closeButton.setIcon(new ImageIcon(closeBtnImg));
            } else {
                closeButton.setIcon(new ImageIcon(closeBtnImgInactive));
            }
            closeButton.setPreferredSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
        }

        if (frame.isMaximizable()) {
            if (frame.isMaximum()) {
                if (frame.isSelected()) {
                    maxButton.setIcon(new ImageIcon(maximizableMBtnImg));
                } else {
                    maxButton.setIcon(new ImageIcon(maximizableMBtnImgInactive));
                }
                maxButton.setPreferredSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));

            } else {
                if (frame.isSelected()) {
                    maxButton.setIcon(new ImageIcon(maximizableBtnImg));
                } else {
                    maxButton.setIcon(new ImageIcon(maximizableBtnImgInactive));
                }
                maxButton.setPreferredSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
            }
        }
        
        if (frame.isIconifiable()) {
            if (frame.isSelected()) {
                iconButton.setIcon(new ImageIcon(minimizibleBtnImg));
            } else {
                iconButton.setIcon(new ImageIcon(minimizibleBtnImgInactive));
            }
            iconButton.setPreferredSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));

        }

    }

    @Override
    public PropertyChangeListener createPropertyChangeListener() {
        return getHandler();
    }

    @Override
    public LayoutManager createLayout() {
        return getHandler();
    }

    Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

 

    /*
	 * Convenience function for determining ComponentOrientation. Helps us avoid
	 * having Munge directives throughout the code.
     */
    private static boolean isLeftToRight(Component c) {
        return c.getComponentOrientation().isLeftToRight();
    }

    private class Handler implements LayoutManager, PropertyChangeListener {

        public Handler() {
            // TODO Auto-generated constructor stub
        }

        //
        // PropertyChangeListener
        //
        @SuppressWarnings("synthetic-access")
		@Override
        public void propertyChange(PropertyChangeEvent evt) {
            String prop = evt.getPropertyName();

            if (prop == JInternalFrame.IS_SELECTED_PROPERTY) {
                repaint();
                return;
            }

            if (prop == JInternalFrame.IS_ICON_PROPERTY || prop == JInternalFrame.IS_MAXIMUM_PROPERTY) {
                setButtonIcons();
                enableActions();
                return;
            }

            if ("closable" == prop) {
                if (evt.getNewValue() == Boolean.TRUE) {
                    add(closeButton);
                } else {
                    remove(closeButton);
                }
            } else if ("maximizable" == prop) {
                if (evt.getNewValue() == Boolean.TRUE) {
                    add(maxButton);
                } else {
                    remove(maxButton);
                }
            } else if ("iconable" == prop) {
                if (evt.getNewValue() == Boolean.TRUE) {
                    add(iconButton);
                } else {
                    remove(iconButton);
                }
            }
            enableActions();

            revalidate();
            repaint();
        }

        //
        // LayoutManager
        //
        @Override
        public void addLayoutComponent(String name, Component c) {
        }

        @Override
        public void removeLayoutComponent(Component c) {
        }

        @Override
        public Dimension preferredLayoutSize(Container c) {
            return minimumLayoutSize(c);
        }

        @SuppressWarnings("synthetic-access")
		@Override
        public Dimension minimumLayoutSize(Container c) {
            // Calculate width.
            int width = 22;

            if (frame.isClosable()) {
                width += 19;
            }
            if (frame.isMaximizable()) {
                width += 19;
            }
            if (frame.isIconifiable()) {
                width += 19;
            }

            FontMetrics fm = frame.getFontMetrics(getFont());
            String frameTitle = frame.getTitle();
            int title_w = frameTitle != null ? fm.stringWidth(frameTitle)  /*  SwingUtilities2.stringWidth(frame, fm, frameTitle) */ : 0;
            int title_length = frameTitle != null ? frameTitle.length() : 0;

            // Leave room for three characters in the title.
            if (title_length > 3) {
                @SuppressWarnings("null")
				int subtitle_w =  fm.stringWidth( frameTitle.substring(0, 3) + "..."  ) ;//   SwingUtilities2.stringWidth(frame, fm, frameTitle.substring(0, 3) + "...");
                width += (title_w < subtitle_w) ? title_w : subtitle_w;
            } else {
                width += title_w;
            }

            // Calculate height.
          //  Icon icon = frame.getFrameIcon();
            
            //int fontHeight = fm.getHeight();
            //fontHeight += 2;
            //int iconHeight = 0;
            //if (icon != null) {
                // SystemMenuBar forces the icon to be 16x16 or less.
              //  iconHeight = Math.min(icon.getIconHeight(), 16);
           // }
            //iconHeight += 2;

          //  int height = Math.max(fontHeight, iconHeight);

            Dimension dim = new Dimension(width, TITLE_HEIGHT);

            // Take into account the border insets if any.
           
            if (getBorder() != null) {
                Insets insets = getBorder().getBorderInsets(c);
                dim.height += insets.top + insets.bottom;
                dim.width += insets.left + insets.right;
            }
             
            return dim;
        }

        @SuppressWarnings("synthetic-access")
		@Override
        public void layoutContainer(Container c) {
            boolean leftToRight = Win10GraphicsUtils.isLeftToRight(frame);

            int w = getWidth();
            //int h = getHeight();
            int x;

           // int buttonHeight = TITLE_HEIGHT; // closeButton.getIcon().getIconHeight();

           // Icon icon = frame.getFrameIcon();
//            int iconHeight = 0;
//            if (icon != null) {
//                iconHeight = icon.getIconHeight();
//            }
            x = (leftToRight) ? 2 : w - TITLE_BUTTON_WIDTH - 2 -BORDER_WIDTH;
             

            x = (leftToRight) ? w - TITLE_BUTTON_WIDTH - 2 -BORDER_WIDTH: 2;

            if (frame.isClosable()) {
                closeButton.setBounds(x, BORDER_WIDTH, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT + 2);
                closeButton.setPreferredSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
                closeButton.setMinimumSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
                closeButton.setSize(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
                x += (leftToRight) ? -(TITLE_BUTTON_WIDTH + 2) : TITLE_BUTTON_WIDTH + 2;
            }

            if (frame.isMaximizable()) {
                maxButton.setBounds(x, BORDER_WIDTH, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT + 2);
                maxButton.setPreferredSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
                maxButton.setMinimumSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
                maxButton.setSize(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);
                x += (leftToRight) ? -(TITLE_BUTTON_WIDTH + 2) : TITLE_BUTTON_WIDTH + 2;
            }

            if (frame.isIconifiable()) {
                iconButton.setBounds(x, BORDER_WIDTH, TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT + 2);
                iconButton.setPreferredSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
                iconButton.setMinimumSize(new Dimension(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT));
                iconButton.setSize(TITLE_BUTTON_WIDTH, TITLE_BUTTON_HEIGHT);

            }
        }
    }

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class PropertyChangeHandler implements PropertyChangeListener {
        // NOTE: This class exists only for backward compatibility. All
        // its functionality has been moved into Handler. If you need to add
        // new functionality add it to the Handler, but make sure this
        // class calls into the Handler.

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            getHandler().propertyChange(evt);
        }
    }

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class TitlePaneLayout implements LayoutManager {
        // NOTE: This class exists only for backward compatibility. All
        // its functionality has been moved into Handler. If you need to add
        // new functionality add it to the Handler, but make sure this
        // class calls into the Handler.

        @Override
        public void addLayoutComponent(String name, Component c) {
            getHandler().addLayoutComponent(name, c);
        }

        @Override
        public void removeLayoutComponent(Component c) {
            getHandler().removeLayoutComponent(c);
        }

        @Override
        public Dimension preferredLayoutSize(Container c) {
            return getHandler().preferredLayoutSize(c);
        }

        @Override
        public Dimension minimumLayoutSize(Container c) {
            return getHandler().minimumLayoutSize(c);
        }

        @Override
        public void layoutContainer(Container c) {
            getHandler().layoutContainer(c);
        }
    }

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class CloseAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public CloseAction() {
            super(UIManager.getString("InternalFrameTitlePane.closeButtonText"));
        }

        @SuppressWarnings("synthetic-access")
		@Override
        public void actionPerformed(ActionEvent e) {
            if (frame.isClosable()) {
                frame.doDefaultCloseAction();
            }
        }
    } // end CloseAction

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class MaximizeAction extends AbstractAction {

		private static final long serialVersionUID = -5760879863067088684L;

		public MaximizeAction() {
            super(UIManager.getString("InternalFrameTitlePane.maximizeButtonText"));
        }

        @SuppressWarnings("synthetic-access")
		@Override
        public void actionPerformed(ActionEvent evt) {
            if (frame.isMaximizable()) {
                if (frame.isMaximum() && frame.isIcon()) {
                    try {
                        frame.setIcon(false);
                    } catch (PropertyVetoException e) {
                    }
                } else if (!frame.isMaximum()) {
                    try {
                        frame.setMaximum(true);
                    } catch (PropertyVetoException e) {
                    }
                } else {
                    try {
                        frame.setMaximum(false);
                    } catch (PropertyVetoException e) {
                    }
                }
            }
        }
    }

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class IconifyAction extends AbstractAction {

		private static final long serialVersionUID = 2141853332010848893L;

		public IconifyAction() {
            super(UIManager.getString("InternalFrameTitlePane.minimizeButtonText"));
        }

        @SuppressWarnings("synthetic-access")
		@Override
        public void actionPerformed(ActionEvent e) {
            if (frame.isIconifiable()) {
                if (!frame.isIcon()) {
                    try {
                        frame.setIcon(true);
                    } catch (PropertyVetoException e1) {
                    }
                } else {
                    try {
                        frame.setIcon(false);
                    } catch (PropertyVetoException e1) {
                    }
                }
            }
        }
    } // end IconifyAction

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class RestoreAction extends AbstractAction {

		private static final long serialVersionUID = -2975208479930971010L;

		public RestoreAction() {
            super(UIManager.getString("InternalFrameTitlePane.restoreButtonText"));
        }

        @SuppressWarnings("synthetic-access")
		@Override
        public void actionPerformed(ActionEvent evt) {
            if (frame.isMaximizable() && frame.isMaximum() && frame.isIcon()) {
                try {
                    frame.setIcon(false);
                } catch (PropertyVetoException e) {
                }
            } else if (frame.isMaximizable() && frame.isMaximum()) {
                try {
                    frame.setMaximum(false);
                } catch (PropertyVetoException e) {
                }
            } else if (frame.isIconifiable() && frame.isIcon()) {
                try {
                    frame.setIcon(false);
                } catch (PropertyVetoException e) {
                }
            }
        }
    }

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class MoveAction extends AbstractAction {

		private static final long serialVersionUID = -7609387671688025603L;

		public MoveAction() {
            super(UIManager.getString("InternalFrameTitlePane.moveButtonText"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // This action is currently undefined
        }
    } // end MoveAction

    /*
	 * Handles showing and hiding the system menu.
     */
    /*
    private class ShowSystemMenuAction extends AbstractAction {

        private boolean show; // whether to show the menu

        public ShowSystemMenuAction(boolean show) {
            this.show = show;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (show) {
                windowMenu.doClick();
            } else {
                windowMenu.setVisible(false);
            }
        }
    }
    */

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class SizeAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		public SizeAction() {
            super(UIManager.getString("InternalFrameTitlePane.sizeButtonText"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // This action is currently undefined
        }
    } // end SizeAction

    /**
     * This class should be treated as a &quot;protected&quot; inner class.
     * Instantiate it only within subclasses of <code>Foo</code>.
     */
    public class SystemMenuBar extends JMenuBar {

		private static final long serialVersionUID = -5575440950618164152L;
/*
		@Override
        public boolean isFocusTraversable() {
            return false;
        }
*/
        @Override
        public void requestFocus() {
        }

        @Override
        public boolean isOpaque() {
            return true;
        }
    } // end SystemMenuBar

 
}
