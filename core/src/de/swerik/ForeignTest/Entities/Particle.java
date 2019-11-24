package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Particle extends SpaceObject {
    private float timer;
    private float time;
    private boolean remove;

    public Particle(float x, float y) {
        this.x = x;
        this.y = y;
        width = height = 2;

        speed = 50;
        radians = MathUtils.random(2 * (float) Math.PI);
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        timer = 0;
        time = 1;
    }

    @Override
    public void update(float delta) {
        x += dx * delta;
        y += dy * delta;

        timer += delta;
        if (timer > time) {
            remove = true;
        }
    }

    @Override
    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.circle(x - width / 2f, y - height / 2f, width / 2);
        sr.end();
    }

    public boolean shouldRemove() {
        return remove;
    }
}
