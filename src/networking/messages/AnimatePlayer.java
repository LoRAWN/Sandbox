/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking.messages;

import application.Animation;
import com.jme3.network.serializing.Serializable;
import networking.Visitor;

/**
 *
 * @author Laurent
 */
@Serializable
public class AnimatePlayer extends AbstrMsg {
    
    private int id;
    
    private String animName;
    
    public AnimatePlayer(int id, String animName) {
        this.id = id;
        this.animName = animName;
    }
    
    public AnimatePlayer() {
        id = 0;
        animName = Animation.IDLE;
    }

    @Override
    public void accept(Visitor s) {
        s.visit(this);
    }
    
    public int getId() {
        return id;
    }
    
    public String getAnimation() {
        return animName;
    }
    
}
