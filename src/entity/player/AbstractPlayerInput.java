package entity.player;

import application.Animation;
import application.InputBitArray;
import com.jme3.math.FastMath;
import com.jme3.renderer.Camera;

/**
 *
 * @author Laurent
 */
public abstract class AbstractPlayerInput extends AbstractPlayer {
    
    public static final float DEFAULT_SPEED = 0.25f;
    public static final float DEFAULT_SPRINT_MULT = 4.0f;
    
    private boolean stopRequested;
    private int keysPressed;
    private float msens;
    private String animation;
    
    public AbstractPlayerInput(int identifier, String playerName, float sensitivity, Camera camera) {
        super(identifier, playerName, camera);   
        animation = Animation.IDLE;
        stopRequested = false;
        keysPressed = 0;
        msens = sensitivity;
    }
    
    @Override
    public void update() {
        super.update();
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
    
    public void pitch(float amount) {
        rotation.setX(rotation.getX() + amount);
        if (rotation.getX() <= Math.PI * (-0.5f)) {
            rotation.setX((float) Math.PI * (-0.499f));
        }
        if (rotation.getX() >= Math.PI * (0.5f)) {
            rotation.setX((float) Math.PI * (0.499f));
        }
    }

    public void yaw(float amount) {
        rotation.setY(rotation.getY() + amount);
        if (rotation.getY() >= FastMath.TWO_PI) {
            rotation.setY(rotation.getY() - FastMath.TWO_PI);
        }
        if (rotation.getY() <= -FastMath.TWO_PI) {
            rotation.setY(rotation.getY() + FastMath.TWO_PI);
        }
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
    
}
