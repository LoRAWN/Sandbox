package networking.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import networking.Visitor;

/**
 *
 * @author Laurent
 */
@Serializable
public abstract class AbstrMsg extends AbstractMessage {
    
    public abstract void accept(Visitor s);
    
}
