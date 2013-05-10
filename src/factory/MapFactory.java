package factory;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HeightfieldCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.terrain.geomipmap.TerrainGrid;
import com.jme3.terrain.geomipmap.TerrainGridListener;
import com.jme3.terrain.geomipmap.TerrainGridLodControl;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.grid.FractalTileLoader;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.noise.ShaderUtils;
import com.jme3.terrain.noise.basis.FilteredBasis;
import com.jme3.terrain.noise.filter.IterativeFilter;
import com.jme3.terrain.noise.filter.OptimizedErode;
import com.jme3.terrain.noise.filter.PerturbFilter;
import com.jme3.terrain.noise.filter.SmoothFilter;
import com.jme3.terrain.noise.fractal.FractalSum;
import com.jme3.terrain.noise.modulator.NoiseModulator;
import com.jme3.texture.Texture;
import entity.player.Player;
import java.util.LinkedList;

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
    
    // Parameters to material:
    // regionXColorMap: X = 1..4 the texture that should be appliad to state X
    // regionX: a Vector3f containing the following information:
    //      regionX.x: the start height of the region
    //      regionX.y: the end height of the region
    //      regionX.z: the texture scale for the region
    //  it might not be the most elegant way for storing these 3 values, but it packs the data nicely :)
    // slopeColorMap: the texture to be used for cliffs, and steep mountain sites
    // slopeTileFactor: the texture scale for slopes
    // terrainSize: the total size of the terrain (used for scaling the texture)
    public Node getProcedularMap(LinkedList<Camera> cameras) {
	float grassScale = 64f;
	float dirtScale = 16f;
	float rockScale = 128f;
	float scaleFactor = 16f;
	// TERRAIN TEXTURE material
	Material mat_terrain = new Material(this.assetManager, "Common/MatDefs/Terrain/HeightBasedTerrain.j3md");
        // GRASS texture
        Texture grass = this.assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("region1ColorMap", grass);
        mat_terrain.setVector3("region1", new Vector3f(15, 200, grassScale));

        // DIRT texture
        Texture dirt = this.assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("region2ColorMap", dirt);
        mat_terrain.setVector3("region2", new Vector3f(0, 20, dirtScale));

        // ROCK texture
        Texture rock = this.assetManager.loadTexture("Textures/Terrain/Rock2/rock.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        mat_terrain.setTexture("region3ColorMap", rock);
        mat_terrain.setVector3("region3", new Vector3f(198, 260, rockScale));
        mat_terrain.setTexture("region4ColorMap", rock);
        mat_terrain.setVector3("region4", new Vector3f(198, 260, rockScale));

        mat_terrain.setTexture("slopeColorMap", rock);
        mat_terrain.setFloat("slopeTileFactor", 32);
        mat_terrain.setFloat("terrainSize", 513);
	
	FractalSum base = new FractalSum();
        base.setRoughness(0.7f);
        base.setFrequency(1.0f);
        base.setAmplitude(1.0f);
        base.setLacunarity(2.12f);
        base.setOctaves(8);
        base.setScale(0.1f);//base.setScale(0.02125f);
        base.addModulator(new NoiseModulator() {

            @Override
            public float value(float... in) {
                return ShaderUtils.clamp(in[0] * 0.5f + 0.5f, 0, 1);
            }
	    
        });

        FilteredBasis ground = new FilteredBasis(base);
	PerturbFilter perturb = new PerturbFilter();
        perturb.setMagnitude(0.119f);
	
	OptimizedErode therm = new OptimizedErode();
        therm.setRadius(5);
        therm.setTalus(0.011f);
	
	SmoothFilter smooth = new SmoothFilter();
        smooth.setRadius(1);
        smooth.setEffect(0.7f);
	IterativeFilter iterate = new IterativeFilter();
        iterate.addPreFilter(perturb);
        iterate.addPostFilter(smooth);
        iterate.setFilter(therm);
        iterate.setIterations(1);

        ground.addPreFilter(iterate);
	
	final TerrainGrid terrain = new TerrainGrid("terrain", 33, 129, new FractalTileLoader(ground, 256f));

        terrain.setMaterial(mat_terrain);
	//
	terrain.addLight(new AmbientLight());
	terrain.addLight(new DirectionalLight());
	//
        terrain.setLocalTranslation(0, -300, 0);
        terrain.setLocalScale(scaleFactor, 1.0f, scaleFactor);

	DistanceLodCalculator lodcalc = new DistanceLodCalculator(33, 2.7f);
	for(Camera cam : cameras) {
	    TerrainLodControl control = new TerrainGridLodControl(terrain, cam);
	    control.setLodCalculator(lodcalc); // patch size, and a multiplier
	    terrain.addControl(control);
	}

	terrain.addListener(new TerrainGridListener() {

            public void gridMoved(Vector3f newCenter) {
            }

            public void tileAttached(Vector3f cell, TerrainQuad quad) {
                while(quad.getControl(RigidBodyControl.class)!=null){
                    quad.removeControl(RigidBodyControl.class);
                }
                quad.addControl(new RigidBodyControl(new HeightfieldCollisionShape(quad.getHeightMap(), terrain.getLocalScale()), 0));
                physicsSpace.add(quad);
            }

	    public void tileDetached(Vector3f cell, TerrainQuad quad) {
                physicsSpace.remove(quad);
                quad.removeControl(RigidBodyControl.class);
	    }

        });
	physicsSpace.addAll(terrain);
	return terrain;
    }
    
}
