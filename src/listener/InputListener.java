package listener;

import application.InputBitArray;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import entity.player.Player;

/**
 *
 * @author Laurent
 */
public class InputListener implements AnalogListener, ActionListener {
    
    private Player player;
    
    public InputListener(Player p) {
        player = p;
    }

    public void onAnalog(String name, float value, float tpf) {
        if(InputBitArray.AXIS_X_POS.equalsIgnoreCase(name)) {
            player.yaw(value*player.getMouseSensitivity()*tpf);
            return;
        }
        if(InputBitArray.AXIS_X_NEG.equalsIgnoreCase(name)) {
            player.yaw(-value*player.getMouseSensitivity()*tpf);
            return;
        }
        if(InputBitArray.AXIS_Y_POS.equalsIgnoreCase(name)) {
            player.pitch(value*player.getMouseSensitivity()*tpf);
            return;
        }
        if(InputBitArray.AXIS_Y_NEG.equalsIgnoreCase(name)) {
            player.pitch(-value*player.getMouseSensitivity()*tpf);
            return;
        }
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed) {
            player.keyPressed(name);
            return;
        }
        player.keyReleased(name);
    }
    
}
