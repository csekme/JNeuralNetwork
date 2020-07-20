package framework;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
/**
 * Egészszám bevitelre kialakított felületi kontroll
 * @author Csekme Krisztián | KSQFYZ
 */
public class IntegerField extends JTextField {
 
	private static final long serialVersionUID = 6582673179883349890L;

	public IntegerField() {
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9')
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE)
                        )) {
         
                    e.consume();
                }
            }

        });
    }
    
	/**
	 * Visszaadja a felületen a kontrollba gépelt számot átalakított integerként
	 * @return a begépelt szám, ellenkező esetben null
	 */
    public Integer getIntegerValue() {
        if (getText().length()>0) {
            return Integer.parseInt(getText());
        }
        return null;
    }
    
    /**
     * Az átadott integer átalakításra kerül szöveges formába és a kontrollban
     * megjelenítésre kerül
     * @param value Az átadott érték
     */
    public void setInteger(Integer value) {
        if (value==null) {
            setText(null);
        } else {
            setText(value.toString());
        }
                
    }
}
