package application.client;

import entity.player.Player;
import application.Animation;
import application.Configuration;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.BasicShadowRenderer;
import factories.EnvironmentFactory;
import factories.PlayerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import listeners.AnimListener;
import map.MyMap;
import networking.Visitor;
import networking.messages.*;

/**
 *
 * @author Laurent
 */
public class GameClient extends AbstractClientApp implements Visitor {
    
    private static final Logger logger = Logger.getLogger(GameClient.class.getName());
    static {
        Logger.getLogger("").setLevel(Level.INFO);
    }
    
    public static void main(String[] args) {
        GameClient g = new GameClient(new Configuration());
        g.start();
    }
    
    private Player player;
    private EnvironmentFactory ef;
    private PlayerFactory pf;
    
    private Map<Integer,Node> players;
    
    public GameClient(Configuration conf) {
        super(conf);
        players = new HashMap<Integer,Node>();
    }

    @Override
    protected void doStart() {
        logger.log(Level.INFO, "Client started...!");
    }

    @Override
    protected void doInit() {
        //
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(-0.1f, -1f, -1).normalizeLocal());
        root.addLight(dl);
        //
        setPauseOnLostFocus(false);
        // load map environment
        ef = new EnvironmentFactory(assetManager,getPhysicsSpace());
        ef.attachMapTo(root, MyMap.class.getName());
        // load player
        pf = new PlayerFactory(configuration, inputManager, assetManager, getPhysicsSpace(), cam);
        player = pf.producePlayer(gui);
        // set shadowing to on
        root.setShadowMode(RenderQueue.ShadowMode.Cast);
        BasicShadowRenderer bsr = new BasicShadowRenderer(assetManager, 256);
        bsr.setDirection(new Vector3f(-1, -1, -1).normalizeLocal());
        viewPort.addProcessor(bsr);
        logger.log(Level.INFO, "Client initialized...!");
    }

    @Override
    protected void doUpdate(float timePerFrame) {
        AbstrMsg m = pollMessage();
        while(m!=null) {
            m.accept(this);
            m = pollMessage();
        }
        // send input to server
        sendMessage(player.getPlayerInput());
        // update stuff comes here
        player.update();
        if(player.isStopRequested()) {
            stop();
        }
    }

    @Override
    protected void doStop() {
        logger.log(Level.INFO, "Client closed...!");
    }

    public void visit(AddPlayer m) {
        if(m.getId()==player.getId()) {
            return;
        }
        Node model = ef.producePlayerModel();
        Node p = new Node(""+m.getId());
        p.attachChild(model);
        players.put(m.getId(), p);
        root.attachChild(p);
        // animation stuff
        AnimControl control = model.getControl(AnimControl.class);
        control.addListener(new AnimListener());
        AnimChannel channel = control.createChannel();
        channel.setAnim(Animation.IDLE);
    }

    public void visit(RemovePlayer m) {
        players.remove(m.getId());
        root.detachChildNamed(""+m.getId());//TODO implement
    }

    public void visit(PlayerInput m) {
        //do nothing
    }

    public void visit(UpdatePlayer m) {
        if(m.getId()==player.getId()) {
            Vector3f rot = m.getRotation();
            cam.getRotation().fromAngles(rot.getX(), rot.getY(), rot.getZ());
            cam.onFrameChange();
            player.getControl().warp(m.getLocation());
            return;
        }
        if(!players.containsKey(m.getId())) {
            return;
        }
        Node p = players.get(m.getId());
        p.setLocalTranslation(m.getLocation());
        Vector3f rot = m.getRotation();
        p.setLocalRotation(Quaternion.ZERO.fromAngleAxis(rot.getY(), Vector3f.UNIT_Y));
    }

    public void visit(InitPlayer m) {
        player.setId(m.getId());
    }

    public void visit(AnimatePlayer m) {
        if(m.getId()==player.getId()) {
            // do nothing atm
            return;
        }
        Node p = players.get(m.getId());
        // animation stuff
        AnimChannel channel = p.getChildren().iterator().next().getControl(AnimControl.class).getChannel(0);
        channel.setAnim(m.getAnimation(), 0.50f);
        channel.setLoopMode(LoopMode.Loop);
    }
    
}
