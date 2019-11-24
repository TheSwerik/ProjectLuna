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

    public boolean contains(float x, float y) {
        boolean b = false;
        for (int i = 0, j = shapeX.length - 1; i < shapeX.length; j = i++) {
            if ((shapeY[i] > y) != (shapeY[j] > y)
                    && (x < (shapeX[j] - shapeX[i])
                    * (y - shapeY[i]) / (shapeY[j] - shapeY[i])
                    + shapeX[i])) {
                b = !b;
            }
        }
        return b;
    }

    public boolean intersects(SpaceObject other) {
        float[] sx = other.getShapeX();
        float[] sy = other.getShapeY();
        for (int i = 0; i < sx.length; i++) {
            if (contains(sx[i], sy[i])) {
                return true;
            }
        }
        return false;
    }

    public abstract void update(float delta);

    public abstract void draw(ShapeRenderer sr);

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }
}
