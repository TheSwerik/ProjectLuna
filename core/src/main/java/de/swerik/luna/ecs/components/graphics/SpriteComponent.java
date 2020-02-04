package de.swerik.luna.ecs.components.graphics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class SpriteComponent implements Component {
    public Array<Sprite> sprites = new Array<>();

    public SpriteComponent(Texture... textures) {
        addTextures(textures);
    }

    public void addTextures(Texture... textures) {
        for (Texture texture : textures) {
            sprites.add(new Sprite(texture));
        }
    }
}
