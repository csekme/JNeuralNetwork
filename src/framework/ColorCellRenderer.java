package framework;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Tbálázatra használatos színes cella renderelő
 * @author Csekme Krisztián | KSQFYZ
 */
public class ColorCellRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 8146774504546047913L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (table == null) {
			return this;
		}
		@SuppressWarnings("cast")
		JLabel label = (JLabel)this;
		
		if (value!=null) {
			Color v = null;
			try {
				
				v = StringToColor(value.toString());
				label.setBackground(v);
				label.setOpaque(true);
				
			} catch (Exception e) {
				
			}
		}
		
		return label;
	}
	
	/**
	 * A megadott színt RGB adatokra bekódolja a tábláztat cella értékére
	 * @param color kiválasztott szín
	 * @return Kódolt szín adatok ;-vel elválasztva R;G;B
	 * @see #StringToColor(String)
	 * @see java.awt.Color
	 */
	public static String ColorToString(Color color) {
		return Integer.toString(color.getRed()) + ";" + Integer.toString(color.getGreen()) + ";" + Integer.toString(color.getBlue());
	}
	
	/**
	 * A megadott {R;G;B} konvenciók által összállítot szín szöveges leírását
	 * visszaalakítja Color színné.
	 * @param colorString A szín szöveges leírása R;G;B formátumban
	 * @return visszatér a kódólt színnel
	 * @see #ColorToString(Color)
	 * @see java.awt.Color
	 */
	public static Color StringToColor(String colorString) {
		if (colorString==null)return null;
		String[] fragments = colorString.split(";");
		if (fragments.length==3) {
			int r = Integer.parseInt(fragments[0]);
			int g = Integer.parseInt(fragments[1]);
			int b = Integer.parseInt(fragments[2]);
			return new Color(r,g,b);
		}
		return null;
	}
}
