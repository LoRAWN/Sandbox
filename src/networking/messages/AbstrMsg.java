package networking.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;
import networking.Visitor;

/**
 *
 * @author Laurent
 */
@Serializable
public abstract class AbstrMsg extends AbstractMessage {
    
    public static void registerMessages() {
        Serializer.registerClass(AddPlayer.class);
        Serializer.registerClass(RemovePlayer.class);
        Serializer.registerClass(PlayerInput.class);
        Serializer.registerClass(UpdatePlayer.class);
        Serializer.registerClass(InitPlayer.class);
        Serializer.registerClass(AnimatePlayer.class);
    }
    
    public abstract void accept(Visitor s);
    
}
