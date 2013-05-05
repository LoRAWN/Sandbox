package entity.player;

import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;
import networking.messages.PlayerInput;

/**
 *
 * @author Laurent
 */
public class Player extends AbstractPlayerListener {
    
    public static final float DEFAULT_HEALTH = 100f;
    
    private float health;
    
    public Player(int identifier, String name,float sensitivity, Camera cam) {
        super(identifier, name, sensitivity, cam);
        health = DEFAULT_HEALTH;
    }
    
    @Override
    public void update() {
        // if not alive, we do not need to update
        if (!isAlive()) {
            return;
        }
        super.update();
    }
    
    public boolean isAlive() {
        return health > 0;
    }

    public float getHealth() {
        return health;
    }

    public void increaseHealth(float amount) {
        health += FastMath.abs(amount);
    }

    public void decreaseHealth(float amount) {
        if (health <= 0) {
            return;
        }
        health -= FastMath.abs(amount);
    }

    public PlayerInput getPlayerInput() {
        return new PlayerInput(this);
    }
    
}
