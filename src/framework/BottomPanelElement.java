package framework;
import framework.ribbonmenu.VirtualObject;
/**
 * MDI gyermek ablak alsó pannelre helyezhető elem, ami lehet gomb elválasztó vagy csak label
 * @author Csekme Krisztián | KSQFYZ
 */
public class BottomPanelElement extends VirtualObject {
    
    public enum Type { Label, Button, Divider }
    private Type type;
    private String caption;
    public final static String EMPTY_STRING = "";
    public BottomPanel parent;
 
    public BottomPanelElement(String token, Type type) {
        super(token);
        this.type = type;    
    }
    
    public BottomPanelElement(String token) {
        super(token);
    }

    public String getCaption() {
        if (caption==null) {
            return EMPTY_STRING;                
        }
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
        parent.repaint();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    
    
}
