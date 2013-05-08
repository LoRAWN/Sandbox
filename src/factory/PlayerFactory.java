package factory;

import application.Configuration;
import application.client.Gui;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.system.AppSettings;
import entity.player.Player;

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
        Player player = producePlayer(uid++, configuration.getPlayerName(), configuration.getMouseSensitivity(), cam);
	physicsSpace.add(player.getControl());
	loadControls(player);
	loadGui(gui,player);
        return player;
    }
    
    public Player producePlayer() {
	Player player = producePlayer(uid++,configuration.getPlayerName(),configuration.getMouseSensitivity(), cam.clone());
	physicsSpace.add(player.getControl());
        return player;
    }
    
    public static Player producePlayer(int uid, String pname, float msens, Camera cam) {
	Player player = new Player(uid, pname, msens, cam);
        return player;
    }
    
    private void loadControls(Player p) {
	configuration.addMapping(inputManager);
        inputManager.setCursorVisible(false);
        inputManager.addListener(p.getInputListener(), configuration.getActionMappingNames());
        inputManager.addListener(p.getInputListener(), configuration.getAnalogMappingNames());
    }
    
    private void loadGui(Node gui, Player p) {
	AppSettings settings = configuration.getSettings();
        Gui g = new Gui(p,assetManager,settings);
        gui.setQueueBucket(RenderQueue.Bucket.Gui);
        gui.setCullHint(Spatial.CullHint.Never);
        gui.attachChild(g.getGuiRoot());
        p.setGui(g);
	
    }
    
}
