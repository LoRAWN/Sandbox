package factory;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Laurent
 */
public class EntityFactory {
    
    private final AssetManager assetManager;
    
    public EntityFactory(AssetManager assetManager) {
	this.assetManager = assetManager;
    }
    
    public Geometry produceBox(String name) {
        Vector3f halfextends = Vector3f.UNIT_XYZ.clone();
        Vector3f location = Vector3f.ZERO.clone();
        location.addLocal(0f, 0.0f, 0f);
        Box b = new Box(halfextends.getX(),halfextends.getY(),halfextends.getZ());
        Geometry geom = new Geometry(name, b);
        // set up material
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        geom.setMaterial(mat);
        //
        return geom;
    }
    
    public Node producePlayerModel() {
        Node p = (Node) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        p.setLocalTranslation(0.0f, 1.0f, 0.0f);
        p.setLocalScale(0.5f);
        return p;
    }
    
}
