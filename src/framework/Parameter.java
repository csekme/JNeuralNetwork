package framework;
import java.util.HashMap;
import java.util.Map;

/**
 * A Parameter egy map, ahol objektumokat egy string alapú kulccsal tárolhatunk el
 * ezzel lehetőség nyílik előre nem meghatározott mennyiségű paraméter átadásra
 * @author Csekme Krisztián | KSQFYZ
 */
public class Parameter  {

    private Map<String, Object> params = new HashMap<>();
    
    /**
     * Paraméter tárolójába tehetünk kulcsok segítségével különböző értékeket
     * osztálypéldányokat
     * @param key hivatkozáshoz használatos kulcs
     * @param value érték vagy oszálypéldány
     */
    public void put(String key, Object value) {
        params.put(key, value);
    }
    
    /**
     * A paraméterbe korábban betett adatot kivehetjük a hivatkozási kulcs segítségével
     * @param key hivatkozáshoz használatos kulcs
     * @return a korábban betett érték vagy osztály példány
     */
    public Object get(String key) {
        return params.get(key);
    }
    
}  
 

 
 

 
 

 