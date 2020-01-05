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
        config.forceExit = false;       // Fixes Memory Leaks
        config.x = -1;                  // Put Screen to CenterX
        config.y = -1;                  // Put Screen to CenterY
        config.useGL30 = true;          // Use OpenGL3
//        config.addIcon();
//        config.fullscreen = true;
//        config.vSyncEnabled = true;
//        config.foregroundFPS = 60;
//        config.resizable = false;
        new LwjglApplication(new Luna(), config);
    }
}
