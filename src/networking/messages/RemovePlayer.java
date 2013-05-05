/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.messages;

import com.jme3.network.serializing.Serializable;
import networking.Visitor;

/**
 *
 * @author Laurent
 */
@Serializable
public class RemovePlayer extends AbstrMsg {
    
    private int id;
    
    public RemovePlayer() {
        id = 0;
    }
    
    public RemovePlayer(int identifier) {
        id = identifier;
    }

    @Override
    public void accept(Visitor s) {
        s.visit(this);
    }

    public int getId() {
        return id;
    }
    
}
