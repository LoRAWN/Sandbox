package networking.messages;

import entity.player.Player;
import com.jme3.network.serializing.Serializable;
import networking.Visitor;

/**
 *
 * @author Laurent
 */
@Serializable
public class PlayerInput extends AbstrMsg {
    
    private static final String DEF_NAME = "UnknownPlayer";
    
    private float pitch;
    private float yaw;
    private int keysPressed;
    private String n;
    
    
    public PlayerInput() {
        pitch = 0;
        yaw = 0;
        keysPressed = 0;
        n = DEF_NAME;
    }
    
    public PlayerInput(Player p) {
        pitch = p.getRotation().getX();
        yaw = p.getRotation().getY();
        keysPressed = p.getKeysPressed();
        n = p.getName();
    }
    
    public String getName() {
        return n;
    }
    
    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public int getKeysPressed() {
        return keysPressed;
    }
    
    @Override
    public String toString() {
        return "[name="+n+",keysPressed="+keysPressed+",pitch="+pitch+",yaw="+yaw+"]";
    }

    @Override
    public void accept(Visitor s) {
        s.visit(this);
    }
    
}
