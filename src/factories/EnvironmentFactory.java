package factories;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.Map;

/**
 *
 * @author Laurent
 */
public class EnvironmentFactory {
    
    private static final Logger logger = Logger.getLogger(EnvironmentFactory.class.getName());
    
    private AssetManager assetManager;
    private PhysicsSpace physicsSpace;
    private final URLClassLoader loader;

    public EnvironmentFactory(AssetManager assetManager, PhysicsSpace physicsSpace) {
        this.assetManager = assetManager;
        this.physicsSpace = physicsSpace;
	File d = new File("./maps/");
	LinkedList<URL> urls = new LinkedList<URL>();
        if(d.exists() && d.isDirectory() && d.canRead()) {
            File[] files = d.listFiles(new FileFilter() {
		
		public boolean accept(File pathname) {
		    if(pathname.exists() && pathname.isFile() && pathname.canRead() && pathname.getName().endsWith(".jar")) {
			return true;
                    }
                    return false;
		}
		
	    });
	    for(File f : files) {
		URL u = null;
		try {
		     u = f.toURI().toURL();
		} catch (MalformedURLException ex) {
		    logger.log(Level.SEVERE, null, ex);
		}
		if(u!=null) {
		    urls.add(u);
		}
	    }   
	}
	loader = new URLClassLoader (urls.toArray(new URL[0]), this.getClass().getClassLoader());
    }
    
    public void attachMapTo(Node scene, String mapName) {
        Map map;
	try {
	    Class<Map> clazz = (Class<Map>) Class.forName(mapName, true, loader);
	    map = clazz.newInstance();
	} catch (Exception ex) {
	    logger.log(Level.SEVERE, null, ex);
	    return;
	}
        scene.attachChild(map.getNonSolids(assetManager));
        //
        Node staticSolids = map.getStaticSolids(assetManager);
        CollisionShape collisionShape = CollisionShapeFactory.createMeshShape(staticSolids);
        RigidBodyControl control = new RigidBodyControl(collisionShape,0);
        staticSolids.addControl(control);
        physicsSpace.add(staticSolids);
        scene.attachChild(staticSolids);
        //
        for(Node dynamic : map.getDynamicSolids(assetManager)) {
            CollisionShape cShape = CollisionShapeFactory.createDynamicMeshShape(dynamic);
            RigidBodyControl cControl = new RigidBodyControl(cShape,10);
            dynamic.addControl(cControl);
            physicsSpace.add(dynamic);
            scene.attachChild(dynamic);
        }
    }
    
    public Geometry produceBox( String name) {
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
