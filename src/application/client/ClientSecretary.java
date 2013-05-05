/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.client;

import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import networking.messages.AbstrMsg;

/**
 *
 * @author Laurent
 */
public class ClientSecretary implements ClientStateListener, MessageListener<Client> {
    
    private static final Logger logger = Logger.getLogger(ClientSecretary.class.getName());
    
    private final AbstractClientApp client;
    
    public ClientSecretary(AbstractClientApp app) {
        client = app;
    }

    public void clientConnected(Client c) {
        logger.log(Level.INFO, "Client connected.");
    }

    public void clientDisconnected(Client c, DisconnectInfo info) {
        logger.log(Level.INFO, "Client disconnected.");
    }

    public void messageReceived(Client source, Message m) {
        try {
            AbstrMsg message = ((AbstrMsg)m);
            client.addMessage(message);
        } catch (ClassCastException e) {
            logger.log(Level.INFO, "Received an unknown message.",e);
        }
    }
    
}
