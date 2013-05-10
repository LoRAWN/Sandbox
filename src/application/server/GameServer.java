package application.server;

import application.Animation;
import application.AttributeKey;
import application.Configuration;
import com.jme3.network.HostedConnection;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.Terrain;
import com.jme3.terrain.geomipmap.TerrainGrid;
import com.jme3.terrain.geomipmap.TerrainGridLodControl;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import entity.player.Player;
import factory.MapFactory;
import factory.PlayerFactory;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messages.AddPlayer;
import networking.messages.AnimatePlayer;
import networking.messages.InitPlayer;
import networking.messages.UpdatePlayer;

/**
 *
 * @author Laurent
 */
public class GameServer extends AbstractServerApp {

    private static final Logger logger = Logger.getLogger(GameServer.class.getName());
    static {
        Logger.getLogger("").setLevel(Level.ALL);
    }

    public static void main(String[] args) {
        Configuration c = new Configuration();
        GameServer gs = new GameServer(c);
        gs.start();
    }
    
    private MapFactory mf;
    private PlayerFactory pf;
    private String mapPath = "Scenes/sandbox01.j3o";
    private TerrainGrid terrain;

    public GameServer(Configuration c) {
        super(c);
    }

    @Override
    protected void doInit() {
        // load map
        mf = new MapFactory(assetManager,getPhysicsSpace());
	root.attachChild(mf.getMap(mapPath));
	LinkedList<Camera> cameras = new LinkedList<Camera>();
	for(HostedConnection each : getClients()) {
	    Player p = each.getAttribute(AttributeKey.PLAYER);
	    cameras.add(p.getCamera());
	}
	terrain = (TerrainGrid)mf.getProcedularMap(cameras);
	root.attachChild(terrain);
        // load player factory
        pf = new PlayerFactory(configuration, inputManager, assetManager, getPhysicsSpace(), cam);
    }

    @Override
    protected void doUpdate(float timePerFrame) {
        processPlayers();
        sendUpdates();
    }

    private void processPlayers() {
        for (HostedConnection each : getClients()) {
            Boolean init = each.getAttribute(AttributeKey.INIT);
            if(init==null) {
                continue;
            }
            if (!init) {
                //
                Player p = pf.producePlayer();
                each.setAttribute(AttributeKey.PLAYER, p);
                each.setAttribute(AttributeKey.ANIMATION, Animation.IDLE);
                //
                getPhysicsSpace().add(p.getControl());
                each.send(new InitPlayer(p.getId(), mapPath));
                AddPlayer ap = new AddPlayer(p.getId());
                for(HostedConnection others : getClients()) {
                    Player o = others.getAttribute(AttributeKey.PLAYER);
                    each.send(new AddPlayer(o.getId()));
                    others.send(ap);
                }
                each.setAttribute(AttributeKey.INIT, true);
		// terrain stuff
		DistanceLodCalculator lodcalc = new DistanceLodCalculator(33, 2.7f);
		TerrainLodControl control = new TerrainGridLodControl(terrain, p.getCamera());
		control.setLodCalculator(lodcalc); // patch size, and a multiplier
		terrain.addControl(control);
		// --
            }
            boolean connected = each.getAttribute(AttributeKey.CONNECTED);
            if (!connected) {
                continue;
            }
            //
            Player p = each.getAttribute(AttributeKey.PLAYER);
            float pitch = each.getAttribute(AttributeKey.PITCH);
            float yaw = each.getAttribute(AttributeKey.YAW);
            int keysPressed = each.getAttribute(AttributeKey.KEYSPRESSED);
            p.setPitch(pitch);
            p.setYaw(yaw);
            p.setKeysPressed(keysPressed);
            //
            p.update();
        }
    }

    private void sendUpdates() {
        LinkedList<UpdatePlayer> updates = new LinkedList<UpdatePlayer>();
        LinkedList<AnimatePlayer> anims = new LinkedList<AnimatePlayer>();
        for (HostedConnection each : getClients()) {
            if ((each == null) || (!each.attributeNames().contains(AttributeKey.PLAYER))) {
                continue;
            }
            Player p = each.getAttribute(AttributeKey.PLAYER);
            updates.add(new UpdatePlayer(p.getId(), p.getRotation(), p.getControl().getPhysicsLocation()));
            String lastAnim = each.getAttribute(AttributeKey.ANIMATION);
            if(!p.getAnimation().equalsIgnoreCase(lastAnim)) {
                each.setAttribute(AttributeKey.ANIMATION, p.getAnimation());
                anims.add(new AnimatePlayer(p.getId(),p.getAnimation()));
            }
        }
        for(HostedConnection client : getClients()) {
            for(UpdatePlayer update : updates) {
                client.send(update);
            }
            for(AnimatePlayer anim : anims) {
                client.send(anim);
            }
        }
    }
    
    @Override
    protected void doStart() {
        logger.log(Level.INFO, "Server started.");
    }

    @Override
    protected void doStop() {
        logger.log(Level.INFO, "Server closed.");
    }
}
