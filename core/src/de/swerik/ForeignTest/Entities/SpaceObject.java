package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.swerik.ForeignTest.ForeignGame;

public abstract class SpaceObject {

    protected float x;
    protected float y;

    protected float dx;
    protected float dy;

    protected float radians;
    protected float speed;
    protected float rotationSpeed;

    protected float width;
    protected float height;

    protected float[] shapeX;
    protected float[] shapeY;

    protected void wrap() {
        if (x < 0) {
            x = ForeignGame.WIDTH;
        }
        if (x > ForeignGame.WIDTH) {
            x = 0;
        }
        if (y < 0) {
            y = ForeignGame.HEIGHT;
        }
        if (y > ForeignGame.HEIGHT) {
            y = 0;
        }
    }

    public abstract void update(float delta);
    public abstract void draw(ShapeRenderer sr);
}
