package de.swerik.Box2D_Tutorial.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

public class Content {

    private HashMap<String, Texture> textures;  //TODO Maybe Use texture packer instead

    public Content() {
        textures = new HashMap<>();
    }

    public void loadTexture(String path, String key) {
        textures.put(
                key,
                new Texture(Gdx.files.internal("placeholder/sprites/" + path))
        );
    }

    public Texture getTexture(String key) {
        return textures.get(key);
    }

    public void disposeTexture(String key) {
        Texture tex = textures.get(key);
        if (tex != null) {
            tex.dispose();
        }
    }

    public void dispose() {
        for (Texture t : textures.values()) {
            if (t != null) {
                t.dispose();
            }
        }
        textures = new HashMap<>();
    }
}
