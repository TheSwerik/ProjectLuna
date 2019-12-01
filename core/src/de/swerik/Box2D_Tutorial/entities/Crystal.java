package de.swerik.Box2D_Tutorial.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import de.swerik.Box2D_Tutorial.states.GameState;

public class Crystal extends B2DSprite {

    public Crystal(Body body) {
        super(body);

        //create textures;
        Texture texture = GameState.res.getTexture("crystal");
        int spriteWidth = 97;
        int spriteHeight = 78;
        int rows = 0;
        for (int i = 0; i < TextureRegion.split(texture, spriteWidth, spriteHeight).length; i++) {
            rows += TextureRegion.split(texture, spriteWidth, spriteHeight)[i].length;
        }
        TextureRegion[] sprites = new TextureRegion[rows];
        int iterator = 0;
        for (TextureRegion[] regArr : TextureRegion.split(texture, spriteWidth, spriteHeight)) {
            for (TextureRegion reg : regArr) {
                sprites[iterator++] = reg;
            }
        }

        //create animation
        animation.setFrames(sprites, 1 / 20f);

        setAnimation(sprites, 1 / 20f);

    }
}
