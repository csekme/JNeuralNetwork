package framework;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import application.Program;

/**
 * Tábla cella formázó, segítségével igazítani tudjuk a táblázatban lévő adatokat
 * @author Csekme Krisztián | KSQFYZ
 */
public class TableCell extends DefaultTableCellRenderer {
	
 
	private static final long serialVersionUID = 8412733972831867595L;
	public static Color strippedColor = new Color(0.2f,0.5f,0.6f,0.2f);
	public static Color selectedBGColor = new Color(0.2f,0.5f,0.6f,0.5f);
	public static Color foreground;
	
	static {
		strippedColor = new Color(   Program.accentColor.getRed(),Program.accentColor.getGreen(),Program.accentColor.getBlue(), 50);
		selectedBGColor = Program.accentColor;
		foreground = Color.BLACK;
	}
	
	public final static byte RIGHT = 0;
	public final static byte LEFT = 1;
	public final static byte CENTER = 2;
	
	private byte align;
	
	/**
	 * Tábla cella példányosítása az előre meghatározott igazítással
	 * @param align RIGHT, LEFT, CENTER
	 * @see #RIGHT
	 * @see #LEFT
	 * @see #CENTER
	 */
	public TableCell(byte align) {
		this.align = align;
	}
	
	@Override
	public JLabel getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {
		
		JLabel c = (JLabel)super.getTableCellRendererComponent(table, value,
	               isSelected, hasFocus, row, col);
		
		c.setOpaque(true);
				
		c.setForeground(Color.black);
		
		if (isSelected) {
			c.setBackground(selectedBGColor);
			c.setForeground(foreground);
			 
		} else {
					
			if ( row%2 == 0) {				
				c.setBackground(null);
			} else {
				c.setBackground(strippedColor);
			}
		}
		
		switch (align) {
		case LEFT:
			c.setHorizontalAlignment(SwingConstants.LEFT);			
			break;
		case CENTER:
			c.setHorizontalAlignment(SwingConstants.CENTER);
			break;
		case RIGHT:
			c.setHorizontalAlignment(SwingConstants.RIGHT);
			break;
		}
		return c;	
	}
	
}
