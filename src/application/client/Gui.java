/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application.client;

import entity.player.Player;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;

/**
 *
 * @author Laurent
 */
public class Gui {
    
    private final Node guiRoot;
    private final Rectangle box;
    
    private final Player player;
    private final BitmapFont font;
    private final BitmapText health;
    private final BitmapText crosshair;
    
    public Gui(Player p, AssetManager assetManager, AppSettings settings) {
        player = p;
        guiRoot = new Node("GuiNode");
        font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        box = new Rectangle(0, 0, settings.getWidth(), settings.getHeight());
        crosshair = new BitmapText(font, false);
        health = new BitmapText(font,false);
        // load gui - crosshair
        crosshair.setName("Crosshair");
        crosshair.setBox(box);
        crosshair.setSize(font.getCharSet().getRenderedSize() * 2);
        crosshair.setText("+");
        float w = box.width / 2f - font.getCharSet().getRenderedSize() / 3f * 2f;
        float h = box.height / 2f + crosshair.getLineHeight() / 2f;
        crosshair.setLocalTranslation(w, h, 0);
        guiRoot.attachChild(crosshair);
        // load gui - health
        health.setName("Health");
        health.setBox(box);
        health.setSize(font.getCharSet().getRenderedSize() * 2);
        health.setText("100");
        w = box.width - font.getCharSet().getRenderedSize()*5;
        h = health.getLineHeight();
        health.setLocalTranslation(w, h, 0);
        guiRoot.attachChild(health);
    }

    public void update() {
        health.setText(""+player.getHealth());
    }

    public Node getGuiRoot() {
        return guiRoot;
    }
    
}
