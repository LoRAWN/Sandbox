package entity;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

public abstract class Entity {
    
    private int id;
    private String name;
    private String modelName;
    protected Vector3f location;
    protected Vector3f rotation;
    
    public Entity(int id, String name, String modelName, Vector3f location, Vector3f rotation) {
        this.id = id;
        this.name = name;
        this.modelName = modelName;
        this.location = location;
        this.rotation = rotation;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location = location;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
    
    public void setPitch(float pitch) {
        rotation.setX(pitch);
    }

    public void setYaw(float yaw) {
        rotation.setY(yaw);
    }
    
    public void setRoll(float roll) {
        rotation.setZ(roll);
    }
    
    public Spatial loadModel(AssetManager assetManager) {
        Spatial model = assetManager.loadModel(modelName);
        model.setLocalTranslation(location);
        model.setLocalTranslation(rotation);
        return model;
    }
   
    public Control loadControl(PhysicsSpace pspace, Spatial model) {
        CollisionShape shape = CollisionShapeFactory.createMeshShape(model);
        RigidBodyControl control = new RigidBodyControl(shape,0);
        return control;
    }
    
    public Node loadEntity(AssetManager assetManager, PhysicsSpace pspace) {
        Node n = new Node(name);
        Spatial model = loadModel(assetManager);
        Control control = loadControl(pspace,model);
        model.addControl(control);
        pspace.add(model);
        n.attachChild(model);
        return n;
    }
    
    public abstract void update();
    
}
