/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.messages;

import com.jme3.math.Vector3f;
import com.jme3.network.serializing.Serializable;
import networking.Visitor;

/**
 *
 * @author Laurent
 */
@Serializable
public class UpdatePlayer extends AbstrMsg {
    
    private int id;
    private Vector3f loc;
    private Vector3f rot;
    
    public UpdatePlayer() {
        id = 0;
        loc = Vector3f.ZERO.clone();
        rot = Vector3f.ZERO.clone();
    }
    
    public UpdatePlayer(int identifier, Vector3f rotation, Vector3f location) {
        id = identifier;
        loc = location;
        rot = rotation;
    }

    @Override
    public void accept(Visitor s) {
        s.visit(this);
    }
    
    public int getId() {
        return id;
    }
    
    public Vector3f getRotation() {
        return rot;
    }
    
    public Vector3f getLocation() {
        return loc;
    }
    
    @Override
    public String toString() {
        return "[rotation="+rot.toString()+",location="+loc.toString()+"]";
    }
    
}
