package de.swerik.ForeignTest.Managers;

import com.badlogic.gdx.Gdx;

import java.io.*;

public class Save {

    public static GameData gd;

    public static void save() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saves/highscores.sav"))) {
            out.writeObject(gd);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load() {
        if (!saveFileExists()) {
            init();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("tutorial/saves/highscores.sav"))) {
            gd = (GameData) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists() {
        File f = new File("tutorial/saves/highscores.sav");
        return f.exists();
    }

    public static void init() {
        gd = new GameData();
        gd.init();
        save();
    }

}
