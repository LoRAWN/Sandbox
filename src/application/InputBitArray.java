package application;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Laurent
 */
public class InputBitArray {
    
    // set up the mapping
    public static Map<String,Integer> map = getMapping();
    public static final String MMB_KEY = "BUTTON_MIDDLE";
    public static final String JUMP_KEY = "KEY_SPACE";
    public static final String FORWARD_KEY = "KEY_W";
    public static final String AXIS_Y_NEG = "AXIS_Y_NEG";
    public static final String LEFT_KEY = "KEY_A";
    public static final String RIGHT_KEY = "KEY_D";
    public static final String LMB_KEY = "BUTTON_LEFT";
    public static final String BACKWARD_KEY = "KEY_S";
    public static final String ESC_KEY = "KEY_ESCAPE";
    public static final String AXIS_Y_POS = "AXIS_Y_POS";
    public static final String AXIS_X_POS = "AXIS_X_POS";
    public static final String AXIS_X_NEG = "AXIS_X_NEG";
    public static final String SPRINT_KEY = "KEY_LSHIFT";
    public static final String RMB_KEY = "BUTTON_RIGHT";
    
    public static Map<String, Integer> getMapping() {
        HashMap<String, Integer> map = new HashMap<String,Integer>();
        map.put(ESC_KEY, 1);
        map.put(JUMP_KEY, 2);
        map.put(SPRINT_KEY, 3);
        map.put(FORWARD_KEY, 4);
        map.put(BACKWARD_KEY, 5);
        map.put(LEFT_KEY, 6);
        map.put(RIGHT_KEY, 7);
        map.put(LMB_KEY, 8);
        map.put(MMB_KEY, 9);
        map.put(RMB_KEY, 10);
        return map;
    }
    
    public static boolean isPressed(String name, int keysPressed) {
        if(!map.containsKey(name)) {
            return false;
        }
        int pos = map.get(name);
        int temp = 1<<pos;
        if((temp&keysPressed)!=0) {
            return true;
        }
        return false;
    }
    
    public static int keyPressed(String name, int keysPressed) {
        if(!map.containsKey(name)) {
            return keysPressed;
        }
        int pos = map.get(name);
        int temp = 1<<pos;
        keysPressed |= temp;
        return keysPressed;
    }
    
    public static int keyReleased(String name, int keysPressed) {
        if(!map.containsKey(name)) {
            return keysPressed;
        }
        int pos = map.get(name);
        int temp = 1<<pos;
        if((temp&pos)!=temp) {
            keysPressed ^= temp;
        }
        return keysPressed;
    }
    
}
