package de.swerik.Box2D_Tutorial.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import de.swerik.Box2D_Tutorial.handlers.Animation;
import de.swerik.Box2D_Tutorial.handlers.Variables;

public class B2DSprite {

    protected Body body;
    protected Animation animation;
    protected float width;
    protected float height;
    protected float scale = 1;

    public B2DSprite(Body body) {
        this.body = body;
        animation = new Animation();
    }

    public void setAnimation(TextureRegion[] reg, float delay) {
        animation.setFrames(reg, delay);
        width = reg[0].getRegionWidth() * scale;
        height = reg[0].getRegionHeight() * scale;
    }

    public void update(float delta) {
        animation.update(delta);
    }

    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(
                animation.getFrame(),
                body.getPosition().x * Variables.PPM - width / 2,
                body.getPosition().y * Variables.PPM - height / 2,
                width / 2f,
                height / 2f,
                width,
                height,
                1f,
                1f,
                (float) MathUtils.radDeg * body.getAngle()
        );
        sb.end();
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
