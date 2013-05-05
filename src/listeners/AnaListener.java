/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listeners;

import application.InputBitArray;
import com.jme3.input.controls.AnalogListener;
import entity.player.AbstractPlayerInput;

/**
 *
 * @author Laurent
 */
public class AnaListener implements AnalogListener {
    
    private AbstractPlayerInput player;
    
    public AnaListener(AbstractPlayerInput p) {
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
    
}
