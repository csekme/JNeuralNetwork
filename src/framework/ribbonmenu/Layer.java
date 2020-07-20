package framework.ribbonmenu;

import java.util.ArrayList;
import java.util.List;

public class Layer {

		List<VirtualObject> objs;
		
		public Layer() {
			objs = new ArrayList<>();
		}
		
		public void add(VirtualObject ob) {
			objs.add(ob);
		}
		
		public VirtualObject get(int number) {
			return objs.get(number);
		}
		
		public int count() {
			return objs.size();
		}
		
		public void clearHover() {
			for (int i=0; i<objs.size(); i++) {
				objs.get(i).hover = false;
			}
		}
}
