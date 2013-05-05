/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.system.JmeContext;

/**
 *
 * @author Laurent
 */
public abstract class AbstractPhysicsApp extends AbstractApp {
    
    private BulletAppState bulletAppState;
    
    public AbstractPhysicsApp(Configuration c) {
        super(c);
    }
    
    @Override
    public void start(JmeContext.Type type) {
        super.start(type);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        // set up physics
        bulletAppState = new BulletAppState();
        bulletAppState.setThreadingType(BulletAppState.ThreadingType.PARALLEL);
        stateManager.attach(bulletAppState);
    }
    
    @Override
    public void update() {
        super.update(); 
    }
    
    @Override
    public void stop() {
        super.stop();
    }
    
    protected PhysicsSpace getPhysicsSpace() {
        return bulletAppState.getPhysicsSpace();
    }
    
}
