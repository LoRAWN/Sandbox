/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import com.jme3.network.serializing.Serializer;
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
public class MessageManager {
    
    public static void registerMessages() {
        Serializer.registerClass(AddPlayer.class);
        Serializer.registerClass(RemovePlayer.class);
        Serializer.registerClass(PlayerInput.class);
        Serializer.registerClass(UpdatePlayer.class);
        Serializer.registerClass(InitPlayer.class);
        Serializer.registerClass(AnimatePlayer.class);
    }
    
}
