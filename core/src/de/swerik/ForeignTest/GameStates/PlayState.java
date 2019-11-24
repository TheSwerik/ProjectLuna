package de.swerik.ForeignTest.GameStates;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.ForeignTest.Entities.Asteroid;
import de.swerik.ForeignTest.Entities.Bullet;
import de.swerik.ForeignTest.Entities.Particle;
import de.swerik.ForeignTest.Entities.Player;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.ForeignTest.Managers.GameKeys;
import de.swerik.ForeignTest.Managers.GameStateManager;

import java.util.ArrayList;

public class PlayState extends GameState {

    private ShapeRenderer sr;

    private Player player;
    private ArrayList<Bullet> bullets;
    private ArrayList<Asteroid> asteroids;
    private ArrayList<Particle> particles;

    private int level;
    private int totalAsteroids;
    private int numAsteroidsLeft;

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        sr = new ShapeRenderer();
        bullets = new ArrayList<>();
        player = new Player(bullets);

        asteroids = new ArrayList<>();
        level = 1;
        spawnAsteroids();

        particles = new ArrayList<>();
    }

    @Override
    public void update(float delta) {
        //get User-input
        handleInput();

        //update Player
        player.update(delta);
        if (player.isDead()) {
            player.reset();
            return;
        }

        //update Bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(delta);
            if (bullets.get(i).shouldRemove()) {
                bullets.remove(i);
                i--;
            }
        }

        //update Asteroids
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(delta);
            if (asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                i--;
            }
        }

        //update Particles
        for (int i = 0; i < particles.size(); i++) {
            particles.get(i).update(delta);
            if (particles.get(i).shouldRemove()) {
                particles.remove(i);
                i--;
            }
        }

        //check collision
        checkCollisions();
    }

    @Override
    public void draw() {
        //draw Player
        player.draw(sr);

        //draw Bullets
        for (Bullet bullet : bullets) {
            bullet.draw(sr);
        }

        //draw Asteroids
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(sr);
        }

        //draw Particles
        for (Particle particle : particles) {
            particle.draw(sr);
        }
    }

    @Override
    public void handleInput() {
        player.setLeft(GameKeys.isDown(GameKeys.LEFT));
        player.setRight(GameKeys.isDown(GameKeys.RIGHT));
        player.setUp(GameKeys.isDown(GameKeys.UP));
        if (GameKeys.isPressed(GameKeys.SPACE)) {
            player.shoot();
        }
    }

    @Override
    public void dispose() {

    }

    private void createParticles(float x, float y) {
        for (int i = 0; i < 6; i++) {
            particles.add(new Particle(x, y));
        }
    }

    private void spawnAsteroids() {
        asteroids.clear();

        int numToSpawn = 4 + level - 1;
        totalAsteroids = numToSpawn * 7;
        numAsteroidsLeft = totalAsteroids;

        System.out.println(numToSpawn);
        System.out.println(totalAsteroids);

        for (int i = 0; i < numToSpawn; i++) {
            float dist;
            float x;
            float y;
            do {
                x = MathUtils.random(ForeignGame.WIDTH);
                y = MathUtils.random(ForeignGame.HEIGHT);

                float dx = x - player.getX();
                float dy = y - player.getY();
                dist = (float) Math.sqrt(dx * dx + dy * dy);
            } while (dist < 100);

            asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
        }
    }

    private void checkCollisions() {
        // Player-Asteroid collision
        if (!player.isHit()) {
            for (int i = 0; i < asteroids.size(); i++) {
                Asteroid a = asteroids.get(i);
                // if Player intersects asteroid
                if (a.intersects(player)) {
                    player.hit();
                    asteroids.remove(i);
                    splitAsteroids(a);
                    break;
                }
            }
        }

        // Bullet-Asteroid collision
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            for (int j = 0; j < asteroids.size(); j++) {
                Asteroid a = asteroids.get(j);
                //if asteroid contains bullet b
                if (a.contains(b.getX(), b.getY())) {
                    bullets.remove(i--);
                    asteroids.remove(j);
                    splitAsteroids(a);
                    break;
                }
            }
        }
    }

    private void splitAsteroids(Asteroid a) {
        createParticles(a.getX(), a.getY());
        numAsteroidsLeft--;
        if (a.getType() == Asteroid.LARGE) {
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
        } else if (a.getType() == Asteroid.MEDIUM) {
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
        }
    }
}
