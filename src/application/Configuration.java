package application;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.system.AppSettings;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 *
 * @author Laurent
 */
public class Configuration {
    
    private String playerName = "Player 1";
    private float sensitivity = 500.0f;
    private HashMap<String, Trigger> actMapping;
    private HashMap<String, Trigger> anaMapping;
    private AppSettings settings;
    private int port = 1337;
    private String hostname = "localhost";

    public Configuration() {
        // the AppSettings
        settings = new AppSettings(true);
        settings.setTitle(AbstractApp.class.getSimpleName());
        settings.setFrameRate(125);
        settings.setHeight(720/2);
        settings.setWidth(1280/2);
        // keys that send keypressed events
        actMapping = new HashMap<String, Trigger>();
        actMapping.put(InputBitArray.ESC_KEY, new KeyTrigger(KeyInput.KEY_ESCAPE));
        actMapping.put(InputBitArray.JUMP_KEY, new KeyTrigger(KeyInput.KEY_SPACE));
        actMapping.put(InputBitArray.SPRINT_KEY, new KeyTrigger(KeyInput.KEY_LSHIFT));
        actMapping.put(InputBitArray.FORWARD_KEY, new KeyTrigger(KeyInput.KEY_W));
        actMapping.put(InputBitArray.BACKWARD_KEY, new KeyTrigger(KeyInput.KEY_S));
        actMapping.put(InputBitArray.LEFT_KEY, new KeyTrigger(KeyInput.KEY_A));
        actMapping.put(InputBitArray.RIGHT_KEY, new KeyTrigger(KeyInput.KEY_D));
        actMapping.put(InputBitArray.LMB_KEY, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        actMapping.put(InputBitArray.MMB_KEY, new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        actMapping.put(InputBitArray.RMB_KEY, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        // keys that get triggered repeatedly
        anaMapping = new HashMap<String, Trigger>();
        anaMapping.put(InputBitArray.AXIS_X_POS, new MouseAxisTrigger(MouseInput.AXIS_X, true));
        anaMapping.put(InputBitArray.AXIS_X_NEG, new MouseAxisTrigger(MouseInput.AXIS_X, false));
        anaMapping.put(InputBitArray.AXIS_Y_POS, new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        anaMapping.put(InputBitArray.AXIS_Y_NEG, new MouseAxisTrigger(MouseInput.AXIS_Y, false));
    }

    public void addMapping(InputManager m) {
        for (Entry<String, Trigger> e : actMapping.entrySet()) {
            m.addMapping(e.getKey(), e.getValue());
        }
        for (Entry<String, Trigger> e : anaMapping.entrySet()) {
            m.addMapping(e.getKey(), e.getValue());
        }
    }

    public String[] getActionMappingNames() {
        return actMapping.keySet().toArray(new String[0]);
    }

    public String[] getAnalogMappingNames() {
        return anaMapping.keySet().toArray(new String[0]);
    }

    public AppSettings getSettings() {
        return settings;
    }
    
    public String getPlayerName() {
        return playerName;
    }

    public int getPort() {
        return port;
    }

    public String getHostName() {
        return hostname;
    }
    
    public float getMouseSensitivity() {
        return sensitivity;
    }

}
