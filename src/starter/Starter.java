/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starter;

import application.Configuration;
import application.client.GameClient;
import application.server.GameServer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Laurent
 */
public class Starter {
    
    public static void main(String[] args) {
        Configuration c = new Configuration();
        GameServer gs = new GameServer(c);
        gs.start();
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException ex) {
            Logger.getLogger(Starter.class.getName()).log(Level.SEVERE, null, ex);
        }
        GameClient gc1 = new GameClient(c);
        gc1.start();
    }
    
}
