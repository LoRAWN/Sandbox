package application.server;

import application.Animation;
import application.Attribute;
import application.Configuration;
import entity.player.Player;
import com.jme3.network.HostedConnection;
import factories.EnvironmentFactory;
import factories.PlayerFactory;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.MyMap;
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
    
    private EnvironmentFactory ef;
    private PlayerFactory pf;

    public GameServer(Configuration c) {
        super(c);
    }

    @Override
    protected void doStart() {
        // do nothing
    }

    @Override
    protected void doInit() {
        // load map environment
        ef = new EnvironmentFactory(assetManager,getPhysicsSpace());
        ef.attachMapTo(root, MyMap.class.getName());
        // load player
        pf = new PlayerFactory(configuration, inputManager, assetManager, getPhysicsSpace(), cam);
    }

    @Override
    protected void doUpdate(float timePerFrame) {
        processPlayers();
        sendUpdates();
    }

    private void processPlayers() {
        for (HostedConnection each : getClients()) {
            Boolean init = each.getAttribute(Attribute.INIT);
            if(init==null) {
                continue;
            }
            if (!init) {
                //
                Player p = pf.producePlayer();
                each.setAttribute(Attribute.PLAYER, p);
                each.setAttribute(Attribute.ANIMATION, Animation.IDLE);
                //
                getPhysicsSpace().add(p.getControl());
                each.send(new InitPlayer(p.getId()));
                AddPlayer ap = new AddPlayer(p.getId());
                for(HostedConnection others : getClients()) {
                    Player o = others.getAttribute(Attribute.PLAYER);
                    each.send(new AddPlayer(o.getId()));
                    others.send(ap);
                }
                each.setAttribute(Attribute.INIT, true);
            }
            boolean connected = each.getAttribute(Attribute.CONNECTED);
            if (!connected) {
                continue;
            }
            //
            Player p = each.getAttribute(Attribute.PLAYER);
            float pitch = each.getAttribute(Attribute.PITCH);
            float yaw = each.getAttribute(Attribute.YAW);
            int keysPressed = each.getAttribute(Attribute.KEYSPRESSED);
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
            if (each == null) {
                continue;
            }
            if (!each.attributeNames().contains(Attribute.PLAYER)) {
                continue;
            }
            Player p = each.getAttribute(Attribute.PLAYER);
            updates.add(new UpdatePlayer(p.getId(), p.getRotation(), p.getControl().getPhysicsLocation()));
            String lastAnim = each.getAttribute(Attribute.ANIMATION);
            if(!p.getAnimation().equalsIgnoreCase(lastAnim)) {
                each.setAttribute(Attribute.ANIMATION, p.getAnimation());
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
    protected void doStop() {
        logger.log(Level.INFO, "Server closed.");
    }
}
