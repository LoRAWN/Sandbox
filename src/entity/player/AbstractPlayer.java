package entity.player;

import application.client.Gui;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import entity.Entity;

/**
 *
 * @author Laurent
 */
public abstract class AbstractPlayer extends Entity {
    
    public static final float DEFAULT_RADIUS = 1.0f;
    public static final float DEFAULT_HEIGHT = 5.0f;
    public static final float DEFAULT_STEPHEIGHT = 0.05f;
    public static final String DEFAULT_MODELNAME = "Models/Oto/Oto.mesh.xml";
    
    public static final Vector3f DEFAULT_PLAYER_ROTATION = Vector3f.ZERO.clone();
    public static final Vector3f DEFAULT_PLAYER_LOCATION = new Vector3f(0,5*DEFAULT_HEIGHT,0);
    
    // player camera
    private Camera cam;
    // player collision control
    private CharacterControl control;
    // gui
    private Gui gui;
    
    public AbstractPlayer(int identifier, String playerName, Camera camera) {
        super(identifier, playerName, DEFAULT_MODELNAME, DEFAULT_PLAYER_LOCATION.clone(), DEFAULT_PLAYER_ROTATION.clone());
        //
        cam = camera;
        cam.getLocation().set(location);
        cam.getLocation().addLocal(0, DEFAULT_HEIGHT, 0);
        cam.getRotation().fromAngles(rotation.getX(), rotation.getY(), rotation.getZ());
        cam.onFrameChange();
        //
        control = new CharacterControl(new CapsuleCollisionShape(DEFAULT_RADIUS, DEFAULT_HEIGHT), DEFAULT_STEPHEIGHT);
        control.warp(location);
        control.setJumpSpeed(40.0f);
        control.setFallSpeed(50.0f);
        control.setGravity(100.0f);
    }
    
    protected void updateCam(float pitch, float yaw, float roll) {
        cam.getRotation().fromAngles(pitch, yaw, roll);
        cam.getLocation().set(control.getPhysicsLocation());
        cam.getLocation().addLocal(0, DEFAULT_HEIGHT, 0);
        // needed to update cam
        cam.onFrameChange();
    }

    protected void updateControl(float fSpeed, float lSpeed) {
        Vector3f wd = control.getWalkDirection();
        wd.set(cam.getDirection());
        wd.normalizeLocal();
        Vector3f sd = wd.clone();
        sd.crossLocal(Vector3f.UNIT_Y);
        sd.multLocal(-lSpeed);
        wd.multLocal(fSpeed);
        wd.addLocal(sd);
        control.setWalkDirection(wd);
    }
    
    protected void jump() {
        if(!control.onGround()) {
            return;
        }
        control.jump();
    }
    
    public void update() {
        if (gui != null) {
            gui.update();
        }
    }
    
    public CharacterControl getControl() {
        return control;
    }

    public void setGui(Gui g) {
        gui = g;
    }
    
}
