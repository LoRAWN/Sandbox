package application.server;

import application.Attribute;
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
            logger.log(Level.INFO, "Received a message of invalid type.",e);
            return;
        }
    }

    public void connectionAdded(Server server, HostedConnection conn) {
        conn.setAttribute(Attribute.CONNECTED, true);
        conn.setAttribute(Attribute.INIT, false);
        conn.setAttribute(Attribute.NAME,conn.getAddress());
        conn.setAttribute(Attribute.PITCH, 0f);
        conn.setAttribute(Attribute.YAW, 0f);
        conn.setAttribute(Attribute.KEYSPRESSED,0);
        logger.log(Level.INFO, "New Player: {0}", conn.getAddress());
    }

    public void connectionRemoved(Server server, HostedConnection conn) {
        conn.setAttribute(Attribute.CONNECTED, false);
        logger.log(Level.INFO, "Remove Player: {0}", conn.getAddress());
    }
    
    
}
