package application.server;

import application.AttributeKey;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.ServerMessageVisitor;
import networking.messages.AbstrMsg;

/**
 *
 * @author Laurent
 */
public class ServerSecretary implements ConnectionListener, MessageListener<HostedConnection> {
    
    private static final Logger logger = Logger.getLogger(ServerSecretary.class.getName());

    public void messageReceived(HostedConnection source, Message m) {
        try {
            ((AbstrMsg)m).accept(new ServerMessageVisitor(source));
        } catch (ClassCastException e) {
            logger.log(Level.INFO, "Received an invalid message.",e);
        }
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        conn.setAttribute(AttributeKey.CONNECTED, true);
        conn.setAttribute(AttributeKey.INIT, false);
        conn.setAttribute(AttributeKey.NAME,conn.getAddress());
        conn.setAttribute(AttributeKey.PITCH, 0f);
        conn.setAttribute(AttributeKey.YAW, 0f);
        conn.setAttribute(AttributeKey.KEYSPRESSED,0);
        logger.log(Level.INFO, "New Player: {0}", conn.getAddress());
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        conn.setAttribute(AttributeKey.CONNECTED, false);
        logger.log(Level.INFO, "Remove Player: {0}", conn.getAddress());
    }
    
    
}
