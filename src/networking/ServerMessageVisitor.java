/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import application.Attribute;
import com.jme3.network.HostedConnection;
import networking.messages.AddPlayer;
import networking.messages.AnimatePlayer;
import networking.messages.PlayerInput;
import networking.messages.RemovePlayer;
import networking.messages.UpdatePlayer;
import networking.messages.InitPlayer;

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
        source.setAttribute(Attribute.CONNECTED, true);
        source.setAttribute(Attribute.INIT, false);
    }

    @Override
    public void visit(RemovePlayer m) {
        source.setAttribute(Attribute.CONNECTED, false);
    }

    @Override
    public void visit(PlayerInput m) {
        source.setAttribute(Attribute.NAME, m.getName());
        source.setAttribute(Attribute.PITCH, m.getPitch());
        source.setAttribute(Attribute.YAW, m.getYaw());
        source.setAttribute(Attribute.KEYSPRESSED, m.getKeysPressed());
    }

    @Override
    public void visit(UpdatePlayer m) {
        return;
    }

    @Override
    public void visit(InitPlayer m) {
        return;
    }

    public void visit(AnimatePlayer m) {
        return;
    }
    
}
