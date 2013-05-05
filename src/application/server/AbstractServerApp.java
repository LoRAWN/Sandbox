/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.server;

import application.AbstractPhysicsApp;
import application.Configuration;
import com.jme3.network.HostedConnection;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.system.JmeContext;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.MessageManager;

/**
 *
 * @author Laurent
 */
public abstract class AbstractServerApp extends AbstractPhysicsApp {
    
    private static final Logger logger = Logger.getLogger(AbstractServerApp.class.getName());
    
    private Server server;
    private final int port;
    private final ServerSecretary secretary;
    
    public AbstractServerApp(Configuration c) {
        super(c);
        port = c.getPort();
        secretary = new ServerSecretary();
    }
    
    @Override
    public void start() {
        setSettings(settings);
        doStart();
        super.start(JmeContext.Type.Headless);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        MessageManager.registerMessages();
        try {
            // set up network
            MessageManager.registerMessages();
            server = Network.createServer(port);
            server.addConnectionListener(secretary);
            server.addMessageListener(secretary);
            server.start();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Server could not be created.", ex);
        }
        doInit();
    }
    
    @Override
    public void update() {
        super.update();
        float tpf = timer.getTimePerFrame() * speed;
        doUpdate(tpf);
    }
    
    protected Collection<HostedConnection> getClients() {
        return server.getConnections();
    }
    
    @Override
    public void stop() {
        server.close();
        doStop();
        super.stop();
    }
    
}
