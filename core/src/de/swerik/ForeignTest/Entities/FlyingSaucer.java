package de.swerik.ForeignTest.Entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.ForeignTest.Managers.Jukebox;

import java.util.ArrayList;

public class FlyingSaucer extends SpaceObject {

    private ArrayList<Bullet> bullets;

    private int type;

    public static final int SMALL = 0;
    public static final int LARGE = 1;

    private float fireTimer;
    private float fireTime;

    private Player player;

    private float pathTimer;
    private float pathTime1;
    private float pathTime2;

    private int direction;

    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    private boolean remove;

    private int score;

    public FlyingSaucer(int type, int direction, Player player, ArrayList<Bullet> bullets) {
        this.type = type;
        this.player = player;
        this.direction = direction;
        this.bullets = bullets;

        speed = 70;

        if (direction == LEFT) {
            dx = -speed;
            x = ForeignGame.WIDTH;
        } else if (direction == RIGHT) {
            dx = speed;
            x = 0;
        }
        y = MathUtils.random(ForeignGame.HEIGHT);

        shapeX = new float[6];
        shapeY = new float[6];
        setShape();

        if (type == SMALL) {
            score = 100;
            Jukebox.loop("smallsaucer", 0.05f);
        } else if (type == LARGE) {
            score = 200;
            Jukebox.loop("largesaucer", 0.05f);
        }

        fireTimer = 0;
        fireTime = 1;
        pathTimer = 0;
        pathTime1 = 2;
        pathTime2 = pathTime1 + 2;
    }

    private void setShape() {
        if (type == LARGE) {
            shapeX[0] = x - 10;
            shapeY[0] = y;
            shapeX[1] = x - 3;
            shapeY[1] = y - 5;
            shapeX[2] = x + 3;
            shapeY[2] = y - 5;
            shapeX[3] = x + 10;
            shapeY[3] = y;
            shapeX[4] = x + 3;
            shapeY[4] = y + 5;
            shapeX[5] = x - 3;
            shapeY[5] = y + 5;
        } else if (type == SMALL) {
            shapeX[0] = x - 6;
            shapeY[0] = y;
            shapeX[1] = x - 2;
            shapeY[1] = y - 3;
            shapeX[2] = x + 2;
            shapeY[2] = y - 3;
            shapeX[3] = x + 6;
            shapeY[3] = y;
            shapeX[4] = x + 2;
            shapeY[4] = y + 3;
            shapeX[5] = x - 2;
            shapeY[5] = y + 3;
        }
    }

    @Override
    public void update(float delta) {
        // fire
        if (!player.isHit()) {
            fireTimer += delta;
            if (fireTimer > fireTime) {
                fireTimer = 0;
                if (type == LARGE) {
                    radians = MathUtils.random((float) (2 * Math.PI));
                } else if (type == SMALL) {
                    radians = MathUtils.atan2(player.getY() - y, player.getX() - x);
                }
                bullets.add(new Bullet(x, y, radians));
                Jukebox.play("saucershoot", 0.1f);
            }
        }

        //move
        pathTimer += delta;

        //move forward
        if (pathTimer < pathTime1) {
            dy = 0;
        }
        //move downward
        if (pathTimer > pathTime1 && pathTimer < pathTime2) {
            dy = -speed;
        }

        //move right
        if (pathTimer > pathTime1 + pathTime2) {
            dy = 0;
            pathTimer = 0;
        }

        x += dx * delta;
        y += dy * delta;
        if (y < 0) {
            y = ForeignGame.HEIGHT;
        }
        setShape();

        //check if remove
        if (direction == RIGHT && x > ForeignGame.WIDTH ||
                direction == LEFT && x < 0) {
            remove = true;
        }
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
        sr.line(shapeX[0], shapeY[0], shapeX[3], shapeY[3]);

        sr.end();
    }

    public int getType() {
        return type;
    }

    public boolean shouldRemove() {
        return remove;
    }

    public int getScore() {
        return score;
    }

}
