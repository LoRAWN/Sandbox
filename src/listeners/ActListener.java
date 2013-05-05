package listeners;

import com.jme3.input.controls.ActionListener;
import entity.player.AbstractPlayerInput;

/**
 *
 * @author Laurent
 */
public class ActListener implements ActionListener {
    
    private AbstractPlayerInput player;
    
    public ActListener(AbstractPlayerInput p) {
        player = p;
    }

    public void onAction(String name, boolean isPressed, float tpf) {
        if(isPressed) {
            player.keyPressed(name);
            return;
        }
        player.keyReleased(name);
    }
    
}
