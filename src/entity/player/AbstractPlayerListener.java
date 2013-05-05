package entity.player;

import com.jme3.renderer.Camera;
import listeners.ActListener;
import listeners.AnaListener;

/**
 *
 * @author Laurent
 */
public class AbstractPlayerListener extends AbstractPlayerInput {
    
    private final ActListener keyListener;
    private final AnaListener mouseListener;
    
    public AbstractPlayerListener(int id, String name, float sensitivity, Camera cam) {
        super(id, name, sensitivity, cam);
        keyListener = new ActListener(this);
        mouseListener = new AnaListener(this);  
    }
    
    @Override
    public void update() {
        super.update();
    }
    
    public ActListener getKeyListener() {
        return keyListener;
    }

    public AnaListener getMouseListener() {
        return mouseListener;
    }
    
}
