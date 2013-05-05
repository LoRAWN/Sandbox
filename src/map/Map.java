/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.List;

/**
 *
 * @author Laurent
 */
public interface Map {
    
    public Node getNonSolids(AssetManager assetManager);
    
    public Node getStaticSolids(AssetManager assetManager);
    
    public List<Node> getDynamicSolids(AssetManager assetManager);
    
}
