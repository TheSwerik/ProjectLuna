package de.swerik.Box2D_Tutorial.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import de.swerik.Box2D_Tutorial.states.GameState;

public class Player extends B2DSprite {

    private int numCrystals;
    private int totalCrystals;

    public Player(Body body) {
        super(body);

        //create textures;
        Texture texture = GameState.res.getTexture("runningSheet");
//        TextureRegion[] sprites = TextureRegion.split(texture, 363, 458)[0];
        int rows = 0;
        for (int i = 0; i < TextureRegion.split(texture, 363, 458).length; i++) {
            rows += TextureRegion.split(texture, 363, 458)[i].length;
        }
        TextureRegion[] sprites = new TextureRegion[rows];
        int iterator = 0;
        for (TextureRegion[] regArr : TextureRegion.split(texture, 363, 458)) {
            for (TextureRegion reg : regArr) {
                sprites[iterator++] = reg;
            }
        }

        //create animation
        animation.setFrames(sprites, 1 / 20f);

        setAnimation(sprites, 1 / 20f);
    }

    public void collectCrystal() {
        numCrystals++;
    }

    public int getNumCrystals() {
        return numCrystals;
    }

    public int getTotalCrystals() {
        return totalCrystals;
    }

    public void setTotalCrystals(int totalCrystals) {
        this.totalCrystals = totalCrystals;
    }
}
