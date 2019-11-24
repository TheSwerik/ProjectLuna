package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.ForeignTest.ForeignGame;

import java.util.ArrayList;

public class Player extends SpaceObject {

    private boolean left;
    private boolean right;
    private boolean up;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    private float[] flameX;
    private float[] flameY;
    private float acceleratingTimer;

    private ArrayList<Bullet> bullets;
    private final int MAX_BULLETS = 4;

    public Player(ArrayList<Bullet> bullets) {
        x = ForeignGame.WIDTH / 2f;
        y = ForeignGame.HEIGHT / 2f;

        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapeX = new float[4];
        shapeY = new float[4];

        flameX = new float[3];
        flameY = new float[3];

        radians = (float) Math.PI / 2;
        rotationSpeed = 3;

        this.bullets = bullets;
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

    private void setFlame() {
        flameX[0] = x + MathUtils.cos(radians - 5 * (float) Math.PI / 6f) * 5;
        flameY[0] = y + MathUtils.sin(radians - 5 * (float) Math.PI / 6f) * 5;

        flameX[1] = x + MathUtils.cos(radians + (float) Math.PI) * (6 + acceleratingTimer * 50);
        flameY[1] = y + MathUtils.sin(radians + (float) Math.PI) * (6 + acceleratingTimer * 50);

        flameX[2] = x + MathUtils.cos(radians + 5 * (float) Math.PI / 6f) * 5;
        flameY[2] = y + MathUtils.sin(radians + 5 * (float) Math.PI / 6f) * 5;
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

    public void shoot() {
        if (MAX_BULLETS == bullets.size()) {
            return;
        }
        bullets.add(new Bullet(x, y, radians));
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
            acceleratingTimer += delta;
            if (acceleratingTimer > 0.1f) {
                acceleratingTimer = 0;
            }
        } else {
            acceleratingTimer = 0;
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

        //set Flame
        if (up) {
            setFlame();
        }


        //screen wrap
        wrap();
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        //draw Ship
        for (int i = 0, j = shapeX.length - 1;
             i < shapeX.length;
             j = i++) {
            sr.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
        }

        //draw Flames
        if (up) {
            for (int i = 0, j = flameX.length - 1;
                 i < flameX.length;
                 j = i++) {
                sr.line(flameX[i], flameY[i], flameX[j], flameY[j]);
            }
        }

        sr.end();
    }

    public void hit() {

    }
}
