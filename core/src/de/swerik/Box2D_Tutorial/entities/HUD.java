package de.swerik.Box2D_Tutorial.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import de.swerik.Box2D_Tutorial.handlers.Variables;
import de.swerik.Box2D_Tutorial.states.PlayState;

public class HUD {

    private Player player;
    private TextureRegion[] blocks;

    public HUD(Player player) {
        this.player = player;

        Texture texture = PlayState.res.getTexture("hud");

        blocks = new TextureRegion[3];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new TextureRegion(texture, 32 + i * 16, 0, 16, 16);
        }
    }

    public void render(SpriteBatch sb) {
        short bits = player.getBody().getFixtureList().first().getFilterData().maskBits;
        sb.begin();
        if ((bits & Variables.BIT_RED) != 0) {
            sb.draw(blocks[0], 40, 1000);
        }
        if ((bits & Variables.BIT_GREEN) != 0) {
            sb.draw(blocks[1], 40, 1000);
        }
        if ((bits & Variables.BIT_BLUE) != 0) {
            sb.draw(blocks[2], 40, 1000);
        }
        sb.end();
    }

}
