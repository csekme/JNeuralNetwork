package framework;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * Lebegőpontos sorozat (vektor) bevitelre kialakított felületi kontroll
 * @author Csekme Krisztián | KSQFYZ
 */
public class DoubleVectorField extends JTextField {

	private static final long serialVersionUID = -1172382651054973156L;

	
	public DoubleVectorField() {
	    addKeyListener(new KeyAdapter() {
	
	        @Override
	        public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
	            if (!((c >= '0') && (c <= '9')
	                    || (c == KeyEvent.VK_BACK_SPACE)
	                    || (c == KeyEvent.VK_DELETE)
	                    || (c == '.')
	                    || (c=='-')
	            		|| (c==',')
	            		)) {
	     
	                e.consume();
	            }
	        }
	
	    });
	}
	
	/**
	 * A kontrollba gépelt számsorozatot, melyeket vesszővel elválasztva adott meg a felhasználó
	 * visszaadásra kerül egy lebegőpontos tömbként
	 * @return begépelt számsor, ellenkező esetben null
	 */
	public Double[] getDoubleVector() {
		try {
			String[] f = getText().split(",");
			Double[] result = new Double[f.length];
			for (int i=0; i<f.length; i++) {
				result[i] = Double.parseDouble(f[i]);
			}
			return result;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Megadhatjunk egy lebegő pontos tömböt (vektor), melyet a függény a kontroll felületén is mutat,
	 * a számjegyeket vesszővel elválasztva
	 * @param value lebegőpontos vektor
	 */
    public void setDoubleVector(Double[] value) {
        if (value==null) {
            setText(null);
        } else {
            String v="";
            for (int i=0; i<value.length; i++) {
                v+=value[i].toString();
                if (i<value.length-1) {
                    v+=",";
                }
            }
            setText(v);
        }
    }
	
	
}
