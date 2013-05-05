package factories;

import application.Configuration;
import application.client.Gui;
import entity.player.Player;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;

/**
 *
 * @author Laurent
 */
public class PlayerFactory {
    
    private int uid;
    private Configuration configuration;
    private InputManager inputManager;
    private AssetManager assetManager;
    private PhysicsSpace physicsSpace;
    private Camera cam;
    
    public PlayerFactory(Configuration configuration, InputManager inputManager, AssetManager assetManager, PhysicsSpace physicsSpace, Camera cam) {
        this.uid = 0;
        this.configuration = configuration;
        this.inputManager = inputManager;
        this.assetManager = assetManager;
        this.physicsSpace = physicsSpace;
        this.cam = cam;
    }
    
    public Player producePlayer(Node gui) {
        Player player = new Player(uid++,configuration.getPlayerName(), configuration.getMouseSensitivity(), cam);
         // load controls
        configuration.addMapping(inputManager);
        inputManager.setCursorVisible(false);
        inputManager.addListener(player.getKeyListener(), configuration.getActionMappingNames());
        inputManager.addListener(player.getMouseListener(), configuration.getAnalogMappingNames());
        // load gui
        AppSettings settings = configuration.getSettings();
        Gui g = new Gui(player,assetManager,settings);
        gui.setQueueBucket(RenderQueue.Bucket.Gui);
        gui.setCullHint(Spatial.CullHint.Never);
        gui.attachChild(g.getGuiRoot());
        // assign camera
        player.setGui(g);
        physicsSpace.add(player.getControl());
        return player;
    }
    
    public Player producePlayer() {
        Player player = new Player(uid++,configuration.getPlayerName(),configuration.getMouseSensitivity(), cam.clone());
        physicsSpace.add(player.getControl());
        return player;
    }
    
}
