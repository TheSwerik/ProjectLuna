package de.swerik.luna.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.swerik.luna.Luna;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Luna.TITLE;
        config.width = Luna.V_WIDTH;
        config.height = Luna.V_HEIGHT;
        config.x = -1;
        config.y = -1;
        config.useGL30 = true;
//        config.addIcon();
//        config.fullscreen = true;
//        config.vSyncEnabled = true;
//        config.foregroundFPS = 60;
//        config.resizable = false;
        new LwjglApplication(new Luna(), config);
    }
}
