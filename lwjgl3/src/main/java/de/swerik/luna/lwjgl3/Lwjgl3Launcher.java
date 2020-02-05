package de.swerik.luna.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.swerik.luna.Luna;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        createApplication();
    }

    @SuppressWarnings("UnusedReturnValue")
    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Luna(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle(Luna.TITLE);
        configuration.useOpenGL3(true, 3, 2);                // Use OpenGL3
        configuration.setWindowedMode((int) Luna.V_WIDTH, (int) Luna.V_HEIGHT);
//        configuration.setWindowPosition(-1, -1);                                                //Center  Screen
        configuration.setWindowPosition(-1, 25);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
//        configuration.setMaximized(true);
        configuration.useVsync(false);
//        configuration.setResizable(false);
        return configuration;
    }
}