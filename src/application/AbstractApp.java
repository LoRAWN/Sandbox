package application;

import com.jme3.app.Application;
import com.jme3.scene.Node;
import com.jme3.system.JmeContext.Type;

/**
 *
 * @author Laurent
 */
public abstract class AbstractApp extends Application {
    
    private static String ROOT_NAME = "Root";
    private static String GUI_NAME = "Gui";
    
    protected Node root;
    protected Node gui;
    
    protected Configuration configuration;
    
    public AbstractApp(Configuration c) {
        super();
        root = new Node(ROOT_NAME);
        gui = new Node(GUI_NAME);
        configuration = c;
        settings = configuration.getSettings();
    }
    
    @Override
    public void start(Type type) {
        setSettings(settings);
        super.start(type);
    }
    
    @Override
    public void initialize() {
        super.initialize();
        // attach nodes to viewports
        viewPort.attachScene(root);
        guiViewPort.attachScene(gui);
    }
    
    @Override
    public void update() {
        super.update();
        float tpf = timer.getTimePerFrame() * speed;
        stateManager.update(tpf);
        root.updateLogicalState(tpf);
        gui.updateLogicalState(tpf);
        root.updateGeometricState();
        gui.updateGeometricState();
        // render states
        stateManager.render(renderManager);
        renderManager.render(tpf, context.isRenderable());
        stateManager.postRender();   
    }
    
    @Override
    public void stop() {
        super.stop();
    }

    protected abstract void doStart();
    
    protected abstract void doInit();
    
    protected abstract void doUpdate(float timePerFrame);

    protected abstract void doStop();
    
}
