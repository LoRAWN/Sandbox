package application.client;

import application.AbstractApp;
import application.Configuration;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messages.AbstrMsg;

/**
 *
 * @author Laurent
 */
public abstract class AbstractClientApp extends AbstractApp {
    
    private static final Logger logger = Logger.getLogger(AbstractClientApp.class.getName());
    
    private String hostName;
    private int hostPort;
    private Client client;
    private final LinkedList<AbstrMsg> messages;
    private ClientSecretary secretary;
    
    public AbstractClientApp(Configuration c) {
        super(c);
        hostPort = c.getPort();
        hostName = c.getHostName();
        messages = new LinkedList<AbstrMsg>();
    }
    
    @Override
    public void start() {
        setSettings(settings);
        doStart();
        super.start(JmeContext.Type.Display);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        AbstrMsg.registerMessages();
        secretary = new ClientSecretary(this);
        try {
            client = Network.connectToServer(hostName, hostPort);
            client.addClientStateListener(secretary);
            client.addMessageListener(secretary);
            client.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Client could not be created.", e);
            //stop();
        }
        doInit();
    }
    
    @Override
    public void update() {
        super.update();
        float tpf = timer.getTimePerFrame() * speed;
        doUpdate(tpf);
        synchronized(messages) {
            messages.clear();
        }
    }
    
    @Override
    public void stop() {
        if(client!=null) {
            client.close();
        }
        doStop();
        super.stop();
    }
    
    protected boolean isConnected() {
        if(client==null) {
            return false;
        }
        return client.isConnected();
    }
    
    protected void sendMessage(AbstrMsg msg) {
        if(client!=null) {
            client.send(msg);
        }
    }
    
    protected AbstrMsg pollMessage() {
        synchronized(messages) {
            return messages.poll();
        }
    }
    
    public void addMessage(AbstrMsg m) {
        synchronized(messages) {
            messages.add(m);
        }
    }
    
    protected abstract void doStart();
    
    protected abstract void doInit();
    
    protected abstract void doUpdate(float timePerFrame);

    protected abstract void doStop();
    
}
