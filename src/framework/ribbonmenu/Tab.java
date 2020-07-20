package framework.ribbonmenu;

import java.util.ArrayList;
import java.util.List;

 

public class Tab extends VirtualObject{

	List<Button> buttons;
	List<String> groups;
	int separators;
	
	public Tab(String token) {
		super(token);
		this.groups = new ArrayList<>();
		this.buttons = new ArrayList<>();
		this.separators = 0;
	}
	
	/***
	 * Button is a mid level menu item
	 * @param title
	 * @return currently created Button
	 */
	public Button addButton(String title) {
		String gen = RibbonBar.generateToken(8);
		Button button = new Button(gen);
		button.setTitle(title);
		buttons.add(button);
		return button;
	}
	
	/***
	 * Button is a mid level menu item
	 * @param title
	 * @return currently created Button
	 */
	public Button addSlimButton(String title) {
		String gen = RibbonBar.generateToken(8);
		Button button = new Button(gen);
		button.setSlim(true);
		button.setTitle(title);
		buttons.add(button);
		return button;
	}
	
	/**
	 * Separator hozzáadása
	 */
	public void addSeperator() {
		String gen = RibbonBar.generateToken(8);
		Button button = new Button(gen);
		button.createSeparator();
		buttons.add(button);
		separators++;
	}
	
	public Button getSeparator(int index) {
		if (separators==0 && separators>index-1) {
			return null;
		}
		int ind=0;
		for (int i=0; i<buttons.size(); i++) {
			Button b = buttons.get(i);
			if (b.isSeparator()) {
				if (ind==index)return b;
				ind++;
			} 
		}
		return null;
	}
	
	public void setGroupName( String name ) {
		groups.add(name);
	}

	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}

	public List<String> getGroups() {
		return groups;
	}
  
	public int getSeparators() {
		return separators;
	}
 
}
