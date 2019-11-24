package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Asteroid extends SpaceObject {

    private int type;

    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;

    private int numPoints;
    private float[] dists;

    private boolean remove;

    public Asteroid(float x, float y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;

        if (type == SMALL) {
            numPoints = 8;
            width = height = 12;
            speed = MathUtils.random(70, 100);
        } else if (type == MEDIUM) {
            numPoints = 10;
            width = height = 20;
            speed = MathUtils.random(50, 60);
        } else if (type == LARGE) {
            numPoints = 12;
            width = height = 40;
            speed = MathUtils.random(20, 30);
        }

        rotationSpeed = MathUtils.random(-1, 1);

        radians = MathUtils.random(2f * (float) Math.PI);
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        shapeX = new float[numPoints];
        shapeY = new float[numPoints];
        dists = new float[numPoints];

        int radius = (int) (width / 2);
        for (int i = 0; i < numPoints; i++) {
            dists[i] = MathUtils.random(radius / 2, radius);
        }

        setShape();
    }

    private void setShape() {
        float angle = 0;
        for (int i = 0; i < numPoints; i++) {
            shapeX[i] = x + MathUtils.cos(angle + radians) * dists[i];
            shapeY[i] = x + MathUtils.sin(angle + radians) * dists[i];
            angle += 2 * Math.PI / numPoints;
        }
    }

    @Override
    public void update(float delta) {
        x += dx * delta;
        y += dy * delta;

        radians += rotationSpeed * delta;
        setShape();

        wrap();
    }

    @Override
    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        //draw Shape
        for (int i = 0, j = shapeX.length - 1;
             i < shapeX.length;
             j = i++) {
            sr.line(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
        }

        sr.end();
    }

    public int getType() {
        return type;
    }

    public boolean shouldRemove() {
        return remove;
    }
}
