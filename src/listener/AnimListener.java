/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import application.Animation;
import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;

/**
 *
 * @author Laurent
 */
public class AnimListener implements AnimEventListener {

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if(Animation.WALKING.equalsIgnoreCase(animName)) {
            //channel.setAnim(Animation.IDLE,0.5f);
            //channel.setLoopMode(LoopMode.DontLoop);
            //channel.setSpeed(1.0f);
        }
    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        // do nothing atm
    }
    
}
