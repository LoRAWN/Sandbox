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
public class InitPlayer extends AbstrMsg {
    
    private int id;
    
    public InitPlayer() {
        id = 0;
    }
    
    public InitPlayer(int identifier) {
        id = identifier;
    }
    
    public int getId() {
        return id;
    }

    @Override
    public void accept(Visitor s) {
        s.visit(this);
    }
    
}
