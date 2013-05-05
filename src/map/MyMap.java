/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import com.jme3.util.SkyFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Laurent
 */
public class MyMap implements Map {
    
    private final int NUM_CUBES = 0;
    private final Random random = new Random(1337);
    private float MAX_VALUE;
    private LinkedList<Node> cubes;

    public Node getNonSolids(AssetManager assetManager) {
        Node scene = new Node("staticnonsolids");
        scene.attachChild(produceSky(assetManager));
        scene.attachChild(produceSpaceshipModel(assetManager));
        return scene;
    }

    public Node getStaticSolids(AssetManager assetManager) {
        Node scene = new Node("staticsolids");
        scene.attachChild(produceTerrain(assetManager));
        return scene;
    }

    public List<Node> getDynamicSolids(AssetManager assetManager) {
        if(cubes==null) {
            cubes = new LinkedList<Node>();
            for(int i = 0; i < NUM_CUBES; i++) {
                Node cube = produceCube(assetManager, "Cube_"+i, getRandomVec(), Vector3f.UNIT_XYZ.clone(), ColorRGBA.Blue);
                cubes.add(cube);
            }
        }
        return cubes;
    }
    
    public Node produceSky(AssetManager assetManager) {
        Texture clouds = assetManager.loadTexture("Textures/sky/clouds.png");
        Spatial sky = SkyFactory.createSky(assetManager, clouds, clouds, clouds, clouds, clouds, clouds);
        Node skyNode = new Node("Sky");
        skyNode.attachChild(sky);
        return skyNode;
    }

    public Node produceTerrain(AssetManager assetManager) {
        Spatial ground = assetManager.loadModel("Scenes/sandbox01.j3o");
        ground.scale(5.0f);
        Node terrainNode = new Node("Terrain");
        terrainNode.attachChild(ground);
        return terrainNode;
    }
    
    public Node produceCube(AssetManager assetManager, String name, Vector3f location, Vector3f halfextends, ColorRGBA color) {
        Box b = new Box(halfextends.getX(), halfextends.getY(), halfextends.getZ());
        Geometry geom = new Geometry(name, b);
        // set up material
        Material mat = new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geom.setMaterial(mat);
        Node boxNode = new Node(name);
        boxNode.attachChild(geom);
        //
        return boxNode;
    }
    
    public Vector3f getRandomVec() {
        return new Vector3f((random.nextFloat()-0.5f)*MAX_VALUE, 0f,(random.nextFloat()-0.5f)*MAX_VALUE);
    }
    
    public Node produceSpaceshipModel(AssetManager assetManager) {
        Node p = (Node) assetManager.loadModel("Models/spaceship/spaceship.j3o");
        return p;
    }
    
}
