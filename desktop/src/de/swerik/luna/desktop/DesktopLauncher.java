package de.swerik.luna.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.MorpheusTest.Main;
import de.swerik.luna.Luna;

public class DesktopLauncher {
    private static final byte test = 3;

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Project Luna";
        config.width = 1920;
        config.height = 1080;
        switch (test) {
            case 1:
                new LwjglApplication(new Luna(), config);
                break;
            case 2:
                new LwjglApplication(new Main(), config);
                break;
            case 3:
                config.resizable = false;
                new LwjglApplication(new ForeignGame(), config);
                break;
        }
    }
}
