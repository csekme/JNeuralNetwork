package internalwindows;
import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;

public class Win10DesktopManager extends DefaultDesktopManager{ 
	
	private static final long serialVersionUID = -2595203868225347842L;

	
	/**
     * Removes the frame from its parent and adds its
     * <code>desktopIcon</code> to the parent.
     * @param f the <code>JInternalFrame</code> to be iconified
     */
	@Override
    public void iconifyFrame(JInternalFrame f) {
    }
	
	
	
}