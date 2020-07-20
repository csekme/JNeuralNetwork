package framework.ribbonmenu;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
public class Button extends VirtualObject {

	private boolean slim;
	private boolean separator;
	private List<ActionListener> actions; 
	private List<Object> subMenu;
	private boolean pressed;
	
	public Button(String token) {
		super(token);
		this.slim = false;
		this.separator = false;
		this.actions = new ArrayList<>();
		this.subMenu = new ArrayList<>();
		this.pressed = false;
	}
	
	public void createSeparator() {
		this.separator = true;
	}

	public boolean isSlim() {
		return slim;
	}

	public void setSlim(boolean slim) {
		this.slim = slim;
	}

	public boolean isSeparator() {
		return separator;
	}
	
	
	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public void addActionListener(ActionListener a) {
		actions.add(a);
	}
	
	public void addSubMenu(JMenuItem a) {
		a.setForeground(Color.DARK_GRAY);
		subMenu.add(a);
	}
	
	public void addSubMenu(JCheckBoxMenuItem a) {
		subMenu.add(a);
	}
	
 
	public void addSubMenu(ActionListener a, String caption) {
		RibbonMenuItem m = new RibbonMenuItem(caption);
		m.addActionListener(a);		
		subMenu.add(m);
	}
	
	public List<Object> getSubMenuList() {
		return subMenu;
	}
	
	public boolean hasDropDown() {
		return subMenu.size()==0?false:true;
	}
	
	public void fireAction(ActionEvent e) {
		for (ActionListener a : actions) {
			a.actionPerformed(e);
		}
	}
	
}
