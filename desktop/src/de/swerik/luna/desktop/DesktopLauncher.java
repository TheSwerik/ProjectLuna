package de.swerik.luna.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.swerik.luna.Luna;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Project Luna2";
        config.width=1280;
        config.height=720;
        new LwjglApplication(new Luna(), config);
    }
}
