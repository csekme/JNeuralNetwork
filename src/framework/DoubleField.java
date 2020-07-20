package framework;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;
/**
 * Lebegőpontos bevitelre kialakított felületi kontroll
 * @author Csekme Krisztián | KSQFYZ
 */
public class DoubleField extends JTextField {
    
	private static final long serialVersionUID = -825339350818860483L;

	public DoubleField() {
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyTyped(KeyEvent e) {
            	
                char c = e.getKeyChar();
              
                
                if (!((c >= '0') && (c <= '9')
                        || (c == KeyEvent.VK_BACK_SPACE)
                        || (c == KeyEvent.VK_DELETE)
                        || (c == '.')
                        || (c=='-'))
                        ) {
         
                    e.consume();
                }
            }

        });
    }
    
	/**
	 * Bevitt szám double értéke
	 * Ha nem történt bevitel null értéket ad vissza 
	 * @return double egyébként null
	 */
    public Double getDoubleValue() {
    	
        if (getText()!=null && getText().length()>0) {
            return Double.parseDouble(getText());
        }
        return null;
    }
    
    /**
     * Double érték megjelenítése, értékének beállítása a kontrolban
     * @param value mint double típus
     */
    public void setDouble(Double value) {
        if (value==null) {
            setText(null);
        } else {
            setText(value.toString());
        }
                
    }
}
