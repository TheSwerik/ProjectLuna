package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.ForeignTest.ForeignGame;

public class Player extends SpaceObject {

    private boolean left;
    private boolean right;
    private boolean up;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    public Player() {
        x = ForeignGame.WIDTH / 2f;
        y = ForeignGame.HEIGHT / 2f;

        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapeX = new float[4];
        shapeY = new float[4];

        radians = (float) Math.PI / 2;
        rotationSpeed = 3;
    }

    private void setShape() {
        shapeX[0] = x + MathUtils.cos(radians) * 8;
        shapeY[0] = y + MathUtils.sin(radians) * 8;

        shapeX[1] = x + MathUtils.cos(radians - 4f * (float) Math.PI / 5f) * 8;
        shapeY[1] = y + MathUtils.sin(radians - 4f * (float) Math.PI / 5f) * 8;

        shapeX[2] = x + MathUtils.cos(radians + (float) Math.PI) * 5;
        shapeY[2] = y + MathUtils.sin(radians + (float) Math.PI) * 5;

        shapeX[3] = x + MathUtils.cos(radians + 4f * (float) Math.PI / 5f) * 8;
        shapeY[3] = y + MathUtils.sin(radians + 4f * (float) Math.PI / 5f) * 8;
    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void update(float delta) {
        //turning
        if (left) {
            radians += rotationSpeed * delta;
        } else if (right) {
            radians -= rotationSpeed * delta;
        }

        //accelerating
        if (up) {
            dx += MathUtils.cos(radians) * acceleration * delta;
            dy += MathUtils.sin(radians) * acceleration * delta;
        }

        //deceleration
        float vec = (float) Math.sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * delta;
            dy -= (dy / vec) * deceleration * delta;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        //set position
        x += dx * delta;
        y += dy * delta;

        //set Shape
        setShape();

        //screen wrap
        wrap();
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapeX.length - 1;
             i < shapeX.length;
             j = i++) {
            sr.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
        }

        sr.end();
    }
}
