package factory;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Laurent
 */
public class MapFactory {
    
    private AssetManager assetManager;
    private PhysicsSpace physicsSpace;

    public MapFactory(AssetManager assetManager, PhysicsSpace physicsSpace) {
        this.assetManager = assetManager;
        this.physicsSpace = physicsSpace;
    }

    public Spatial getMap(String mapName) {
	Node mapNode = new Node(mapName);
	Spatial map = assetManager.loadModel(mapName);
        //
        CollisionShape collisionShape = CollisionShapeFactory.createMeshShape(map);
        RigidBodyControl control = new RigidBodyControl(collisionShape,0);
        map.addControl(control);
        physicsSpace.add(map);
        mapNode.attachChild(map);
        //
        /*for(Node dynamic : map.getDynamicSolids(assetManager)) {
            CollisionShape cShape = CollisionShapeFactory.createDynamicMeshShape(dynamic);
            RigidBodyControl cControl = new RigidBodyControl(cShape,10);
            dynamic.addControl(cControl);
            physicsSpace.add(dynamic);
            mapNode.attachChild(dynamic);
        }*/
	return mapNode;
    }
    
}
