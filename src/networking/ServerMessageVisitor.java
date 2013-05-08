/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import application.AttributeKey;
import com.jme3.network.HostedConnection;
import networking.messages.AddPlayer;
import networking.messages.AnimatePlayer;
import networking.messages.InitPlayer;
import networking.messages.PlayerInput;
import networking.messages.RemovePlayer;
import networking.messages.UpdatePlayer;

/**
 *
 * @author Laurent
 */
public class ServerMessageVisitor implements Visitor {
    
    private final HostedConnection source;
    
    public ServerMessageVisitor(HostedConnection src) {
        source = src;
    }
    
    @Override
    public void visit(AddPlayer m) {
        source.setAttribute(AttributeKey.CONNECTED, true);
        source.setAttribute(AttributeKey.INIT, false);
    }

    @Override
    public void visit(RemovePlayer m) {
        source.setAttribute(AttributeKey.CONNECTED, false);
    }

    @Override
    public void visit(PlayerInput m) {
        source.setAttribute(AttributeKey.NAME, m.getName());
        source.setAttribute(AttributeKey.PITCH, m.getPitch());
        source.setAttribute(AttributeKey.YAW, m.getYaw());
        source.setAttribute(AttributeKey.KEYSPRESSED, m.getKeysPressed());
    }

    @Override
    public void visit(UpdatePlayer m) {
	//do nothing, not meant for servers
    }

    @Override
    public void visit(InitPlayer m) {
	//do nothing, not meant for servers
    }

    public void visit(AnimatePlayer m) {
	//do nothing, not meant for servers
    }
    
}
