package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.ForeignTest.ForeignGame;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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

    private boolean hit;
    private boolean dead;

    private float hitTimer;
    private float hitTime;

    private Line2D.Float[] hitLines;
    private Point2D.Float[] hitLinesVector;

    private long score;
    private int extraLives;
    private long requiredScore;

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

        hit = false;
        hitTimer = 0;
        hitTime = 2;

        score = 0;
        extraLives = 3;
        requiredScore = 10000;
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
        //check if hit
        if (hit) {
            hitTimer += delta;
            if (hitTimer > hitTime) {
                dead = true;
                hitTimer = 0;
            }
            for (int i = 0; i < hitLines.length; i++) {
                hitLines[i].setLine(
                        hitLines[i].x1 + hitLinesVector[i].x * 10 * delta,
                        hitLines[i].y1 + hitLinesVector[i].y * 10 * delta,
                        hitLines[i].x2 + hitLinesVector[i].x * 10 * delta,
                        hitLines[i].y2 + hitLinesVector[i].y * 10 * delta
                );
            }
            return;
        }

        //check extra Lives
        if (score >= requiredScore) {
            extraLives++;
            requiredScore += 10000;
        }

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

        //check if hit
        if (hit) {
            for (Line2D.Float hitLine : hitLines) {
                sr.line(
                        hitLine.x1,
                        hitLine.y1,
                        hitLine.x2,
                        hitLine.y2
                );
            }
            sr.end();
            return;
        }

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
        if (hit) {
            return;
        }
        hit = true;
        dx = dy = 0;
        left = right = up = false;

        hitLines = new Line2D.Float[4];
        for (int i = 0, j = hitLines.length - 1;
             i < hitLines.length;
             j = i++) {
            hitLines[i] = new Line2D.Float(shapeX[i], shapeY[i], shapeX[j], shapeY[j]);
        }

        hitLinesVector = new Point2D.Float[4];
        hitLinesVector[0] = new Point2D.Float(
                MathUtils.cos(radians + 1.5f),
                MathUtils.sin(radians + 1.5f)
        );
        hitLinesVector[1] = new Point2D.Float(
                MathUtils.cos(radians - 1.5f),
                MathUtils.sin(radians - 1.5f)
        );
        hitLinesVector[2] = new Point2D.Float(
                MathUtils.cos(radians - 2.8f),
                MathUtils.sin(radians - 2.8f)
        );
        hitLinesVector[3] = new Point2D.Float(
                MathUtils.cos(radians + 2.8f),
                MathUtils.sin(radians + 2.8f)
        );
    }

    public void reset() {
        x = ForeignGame.WIDTH / 2f;
        y = ForeignGame.HEIGHT / 2f;
        setShape();
        hit = dead = false;
    }

    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        setShape();
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isHit() {
        return hit;
    }

    public long getScore() {
        return score;
    }

    public int getExtraLives() {
        return extraLives;
    }

    public void loseLife() {
        extraLives--;
    }

    public void addScore(long l) {
        score += l;
    }
}
