package internalwindows;

import java.awt.Color;
import java.awt.Container;
import javax.swing.*;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicInternalFrameUI;

public class Win10InternalFrameUI extends BasicInternalFrameUI {

    
	public Win10InternalFrameUI(JInternalFrame b) {
	    super(b);
	 
        	
		
	}

    @Override
	public void installComponents(){
        setNorthPane(createNorthPane(frame));
        setSouthPane(createSouthPane(frame));
        setEastPane(createEastPane(frame));
        setWestPane(createWestPane(frame));
        
    }
    
    @Override
    public void installDefaults(){
        Icon frameIcon = frame.getFrameIcon();
        if (frameIcon == null || frameIcon instanceof UIResource) {
            //frame.setFrameIcon(UIManager.getIcon("InternalFrame.icon"));
        }

        // Enable the content pane to inherit background color from its
        // parent by setting its background color to null.
        Container contentPane = frame.getContentPane();
        if (contentPane != null) {
          Color bg = contentPane.getBackground();
          if (bg instanceof UIResource)
            contentPane.setBackground(null);
        }
        frame.setLayout(internalFrameLayout = createLayoutManager());
        frame.setBackground(UIManager.getLookAndFeelDefaults().getColor("control"));
        LookAndFeel.installBorder(frame, "InternalFrame.border");
    }

    @Override
	public JComponent createNorthPane(JInternalFrame w) {
        titlePane = new Win10InternalTitlePane(w);
        return titlePane;
     }
    
    @Override
    public DesktopManager createDesktopManager(){
        return new Win10DesktopManager();
      }


}