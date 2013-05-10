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
    private String map = "";
    
    public InitPlayer() {
        id = 0;
    }
    
    public InitPlayer(int identifier, String mapPath) {
        id = identifier;
	map = mapPath;
    }
    
    public int getId() {
        return id;
    }
    
    public String getMap() {
	return map;
    }

    @Override
    public void accept(Visitor s) {
        s.visit(this);
    }
    
}
