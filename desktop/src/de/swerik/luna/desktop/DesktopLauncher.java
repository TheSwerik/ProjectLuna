package de.swerik.luna.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.swerik.luna.Luna;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Project Luna";
        config.width = 1920;
        config.height = 1080;
//        config.resizable = false;
        new LwjglApplication(new Luna(), config);

//        new LwjglApplication(new Main(), config);
//        new LwjglApplication(new ForeignGame(), config);
    }
}
