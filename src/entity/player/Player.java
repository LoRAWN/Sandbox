package entity.player;

import application.Animation;
import application.InputBitArray;
import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;
import listener.InputListener;
import networking.messages.PlayerInput;

/**
 *
 * @author Laurent
 */
public class Player extends AbstractPlayer {
    
    public static final float DEFAULT_HEALTH = 100f;
    public static final float DEFAULT_SPEED = 0.25f;
    public static final float DEFAULT_SPRINT_MULT = 4.0f;
    
    private boolean stopRequested;
    private int keysPressed;
    private float msens;
    private String animation;
    private InputListener inputListener;
    private float health;
    
    public Player(int identifier, String name,float sensitivity, Camera cam) {
        super(identifier, name, cam);
        health = DEFAULT_HEALTH;
	animation = Animation.IDLE;
        stopRequested = false;
        keysPressed = 0;
        msens = sensitivity;
        inputListener = new InputListener(this);  
    }
    
    @Override
    public void update() {
        // if not alive, we do not need to update
        if (!isAlive()) {
            return;
        }
	// update rotation
        updateCam(rotation.getX(),rotation.getY(),rotation.getZ());
        //process keysPressed
        float fSpeed = 0;
        float lSpeed = 0;
        animation = Animation.IDLE;
        float mult = 1;
        if (InputBitArray.isPressed(InputBitArray.SPRINT_KEY, keysPressed)) {
            mult = DEFAULT_SPRINT_MULT;
            animation = Animation.WALKING;
        }
        if (InputBitArray.isPressed(InputBitArray.FORWARD_KEY, keysPressed)) {
            fSpeed = DEFAULT_SPEED;
            animation = Animation.WALKING;
        }
        if (InputBitArray.isPressed(InputBitArray.BACKWARD_KEY, keysPressed)) {
            fSpeed = -DEFAULT_SPEED;
            animation = Animation.WALKING;
        }
        if (InputBitArray.isPressed(InputBitArray.LEFT_KEY, keysPressed)) {
            lSpeed = DEFAULT_SPEED;
            animation = Animation.WALKING;
        }
        if (InputBitArray.isPressed(InputBitArray.RIGHT_KEY, keysPressed)) {
            lSpeed = -DEFAULT_SPEED;
            animation = Animation.WALKING;
        }
        if (InputBitArray.isPressed(InputBitArray.JUMP_KEY, keysPressed)) {
            jump();
        }
        if (InputBitArray.isPressed(InputBitArray.ESC_KEY, keysPressed)) {
            stopRequest();
        }
        // update walkdirection
        updateControl(mult * fSpeed, lSpeed);
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
    
    public int getKeysPressed() {
        return keysPressed;
    }
    
    public void keyPressed(String name) {
        keysPressed = InputBitArray.keyPressed(name, keysPressed);
    }

    public void keyReleased(String name) {
        keysPressed = InputBitArray.keyReleased(name, keysPressed);
    }
    
    public void setKeysPressed(int keysPressed) {
        this.keysPressed = keysPressed;
    }
    
    public float getMouseSensitivity() {
        return msens;
    }
    
    public boolean isStopRequested() {
        return stopRequested;
    }
    
    public void stopRequest() {
        this.stopRequested = true;
    }
    
    public String getAnimation() {
        return animation;
    }

    public InputListener getInputListener() {
        return inputListener;
    }
    
}