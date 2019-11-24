package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {
    private float lifeTime;
    private float lifeTimer;

    private boolean remove;

    public Bullet(float x, float y, float radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;

        float speed = 350;
        dx = MathUtils.cos(radians) * speed;
        dy = MathUtils.sin(radians) * speed;

        width = height = 2;

        lifeTimer = 0;
        lifeTime = 1;
    }

    public boolean shouldRemove() {
        return remove;
    }

    @Override
    public void update(float delta) {
        x += dx * delta;
        y += dy * delta;

        wrap();

        lifeTimer += delta;
        if (lifeTimer > lifeTime) {
            remove = true;
        }
    }

    @Override
    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.circle(x - width / 2f, y - height / 2f, width / 2f);

        sr.end();
    }
}
