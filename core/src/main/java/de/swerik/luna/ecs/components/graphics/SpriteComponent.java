package de.swerik.luna.ecs.components.graphics;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;

public class SpriteComponent extends Component {
    public Array<Sprite> sprites = new Array<>();

    public SpriteComponent() {
    }

    public SpriteComponent(Texture... textures) {
        addTextures(textures);
    }

    public void addTextures(Texture... textures) {
        for (Texture texture : textures) {
            sprites.add(new Sprite(texture));
        }
    }
}
