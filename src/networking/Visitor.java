package networking;

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
public interface Visitor {
    
    public void visit(AddPlayer m);
    public void visit(RemovePlayer m);
    public void visit(PlayerInput m);
    public void visit(UpdatePlayer m);
    public void visit(InitPlayer m);
    public void visit(AnimatePlayer m);
    
}
