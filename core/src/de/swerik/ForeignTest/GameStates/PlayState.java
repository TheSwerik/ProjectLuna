package de.swerik.ForeignTest.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.ForeignTest.Entities.*;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.ForeignTest.Managers.GameKeys;
import de.swerik.ForeignTest.Managers.GameStateManager;
import de.swerik.ForeignTest.Managers.Jukebox;
import de.swerik.ForeignTest.Managers.Save;

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

    private SpriteBatch batch;
    private BitmapFont font;

    private Player hudPlayer;

    private float maxDelay;
    private float minDelay;
    private float currentDelay;
    private float bgTimer;
    private boolean playLowPulse;

    private FlyingSaucer flyingSaucer;
    private ArrayList<Bullet> enemyBullets;
    private float fsTimer;
    private float fsTime;

    private ArrayList<SpaceObject> collisionList;

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

        batch = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/lunchds.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = gen.generateFont(parameter);

        hudPlayer = new Player(null);

        maxDelay = 1f;
        minDelay = 0.25f;
        currentDelay = maxDelay;
        bgTimer = maxDelay;
        playLowPulse = true;

        enemyBullets = new ArrayList<>();
        fsTimer = 0;
        fsTime = 15;

        collisionList = new ArrayList<>();
    }

    @Override
    public void update(float delta) {
        //get User-input
        handleInput();

        //next level
        if (asteroids.size() == 0) {
            level++;
            spawnAsteroids();
        }

        //update Player
        player.update(delta);
        if (player.isDead()) {
            if (player.getExtraLives() == 0) {
                Jukebox.stopAll();
                Save.gd.setTentScore(player.getScore());
                gsm.setState(GameStateManager.GAMEOVER);
                return;
            }
            player.reset();
            player.loseLife();
            flyingSaucer = null;
            Jukebox.stop("smallsaucer");
            Jukebox.stop("largesaucer");
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

        //update FlyingSaucer
        if (flyingSaucer == null) {
            fsTimer += delta;
            if (fsTimer >= fsTime) {
                fsTimer = 0;
                flyingSaucer = new FlyingSaucer(MathUtils.random(100) % 2,
                        MathUtils.random(100) % 2,
                        player,
                        enemyBullets);
            }
        } else {
            flyingSaucer.update(delta);
            if (flyingSaucer.shouldRemove()) {
                flyingSaucer = null;
                Jukebox.stop("smallsaucer");
                Jukebox.stop("largesaucer");
            }
        }

        //update SaucerBullets
        for (int i = 0; i < enemyBullets.size(); i++) {
            enemyBullets.get(i).update(delta);
            if (enemyBullets.get(i).shouldRemove()) {
                enemyBullets.remove(i);
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

        // play bg-music
        bgTimer += delta;
        if (!player.isHit() && bgTimer >= currentDelay) {
            if (playLowPulse) {
                Jukebox.play("pulselow", 0.05f);
            } else {
                Jukebox.play("pulsehigh", 0.05f);
            }
            playLowPulse = !playLowPulse;
            bgTimer = 0;
        }
    }

    @Override
    public void draw() {
        batch.setProjectionMatrix(ForeignGame.cam.combined);
        sr.setProjectionMatrix(ForeignGame.cam.combined);
        //draw Player
        player.draw(sr);

        //draw Bullets
        for (Bullet bullet : bullets) {
            bullet.draw(sr);
        }

        //draw FlyingSaucer
        if (flyingSaucer != null) {
            flyingSaucer.draw(sr);
        }

        //draw SaucerBullets
        for (Bullet bullet : enemyBullets) {
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

        //draw score
        batch.setColor(1, 1, 1, 1);
        batch.begin();
        font.draw(batch, Long.toString(player.getScore()), 40, ForeignGame.HEIGHT - 40);
        batch.end();

        //draw lives
        for (int i = 0; i < player.getExtraLives(); i++) {
            hudPlayer.setPosition(45 + 15 * i, ForeignGame.HEIGHT - 80);
            hudPlayer.draw(sr);
        }
    }

    @Override
    public void handleInput() {
        if (!player.isHit()) {
            player.setLeft(GameKeys.isDown(GameKeys.LEFT));
            player.setRight(GameKeys.isDown(GameKeys.RIGHT));
            player.setUp(GameKeys.isDown(GameKeys.UP));
            if (GameKeys.isPressed(GameKeys.SPACE)) {
                player.shoot();
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        sr.dispose();
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
        currentDelay = maxDelay;

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
        //Fill collisionList
        collisionList = new ArrayList<>();
        collisionList.add(player);
        collisionList.add(flyingSaucer);
        collisionList.addAll(asteroids);
        collisionList.addAll(bullets);
        collisionList.addAll(enemyBullets);

        //check collisionLoop
        for (int i = 0; i < collisionList.size(); i++) {
            for (int j = i; j < collisionList.size(); j++) {
                if ((collisionList.get(i) instanceof Player && collisionList.get(j) instanceof Asteroid) ||
                        (collisionList.get(j) instanceof Player && collisionList.get(i) instanceof Asteroid)) {
                    // Player-Asteroid
                    Player player;
                    Asteroid asteroid;
                    if (collisionList.get(i) instanceof Player) {
                        player = (Player) collisionList.get(i);
                        asteroid = (Asteroid) collisionList.get(j);
                    } else {
                        player = (Player) collisionList.get(j);
                        asteroid = (Asteroid) collisionList.get(i);
                    }
                    // Player-Asteroid collision
                    if (!player.isHit()) {
                        // if Player intersects asteroid
                        if (asteroid.intersects(player)) {
                            player.hit();
                            asteroids.remove(asteroid);
                            splitAsteroids(asteroid);
                            Jukebox.play("explode", 0.3f);
                            break;
                        }
                    }
                } else if ((collisionList.get(i) instanceof Bullet && bullets.contains(collisionList.get(i)) && collisionList.get(j) instanceof Asteroid) ||
                        (collisionList.get(j) instanceof Bullet && bullets.contains(collisionList.get(j)) && collisionList.get(i) instanceof Asteroid)) {
                    // PlayerBullet-Asteroid
                    Bullet bullet;
                    Asteroid asteroid;
                    if (collisionList.get(i) instanceof Bullet) {
                        bullet = (Bullet) collisionList.get(i);
                        asteroid = (Asteroid) collisionList.get(j);
                    } else {
                        bullet = (Bullet) collisionList.get(j);
                        asteroid = (Asteroid) collisionList.get(i);
                    }
                    // Bullet-Asteroid collision
                    //if asteroid contains bullet b
                    if (asteroid.contains(bullet.getX(), bullet.getY())) {
                        bullets.remove(bullet);
                        asteroids.remove(asteroid);
                        splitAsteroids(asteroid);
                        Jukebox.play("explode", 0.3f);

                        // increment score
                        player.addScore(asteroid.getScore());
                        break;
                    }
                } else if ((collisionList.get(i) instanceof Player && collisionList.get(j) instanceof FlyingSaucer) ||
                        (collisionList.get(j) instanceof Player && collisionList.get(i) instanceof FlyingSaucer)) {
                    // Player-Saucer
                    Player player;
                    FlyingSaucer saucer;
                    if (collisionList.get(i) instanceof Player) {
                        player = (Player) collisionList.get(i);
                        saucer = (FlyingSaucer) collisionList.get(j);
                    } else {
                        player = (Player) collisionList.get(j);
                        saucer = (FlyingSaucer) collisionList.get(i);
                    }
                    // Player-Asteroid collision
                    if (!player.isHit()) {
                        // if Player intersects asteroid
                        if (saucer.intersects(player)) {
                            player.hit();
                            flyingSaucer = null;
                            Jukebox.stop("smallsaucer");
                            Jukebox.stop("largesaucer");
                            Jukebox.play("explode", 0.3f);
                            break;
                        }
                    }
                } else if ((collisionList.get(i) instanceof Bullet && bullets.contains(collisionList.get(i)) && collisionList.get(j) instanceof FlyingSaucer) ||
                        (collisionList.get(j) instanceof Bullet && bullets.contains(collisionList.get(j)) && collisionList.get(i) instanceof FlyingSaucer)) {
                    // PlayerBullet-Saucer
                    Bullet bullet;
                    FlyingSaucer saucer;
                    if (collisionList.get(i) instanceof Bullet) {
                        bullet = (Bullet) collisionList.get(i);
                        saucer = (FlyingSaucer) collisionList.get(j);
                    } else {
                        bullet = (Bullet) collisionList.get(j);
                        saucer = (FlyingSaucer) collisionList.get(i);
                    }
                    // Bullet-FlyingSaucer collision
                    //if FlyingSaucer contains bullet b
                    if (saucer.contains(bullet.getX(), bullet.getY())) {
                        bullets.remove(bullet);
                        flyingSaucer = null;
                        Jukebox.stop("smallsaucer");
                        Jukebox.stop("largesaucer");
                        Jukebox.play("explode", 0.3f);

                        // increment score
                        player.addScore(saucer.getScore());
                        break;
                    }
                } else if ((collisionList.get(i) instanceof Bullet && enemyBullets.contains(collisionList.get(i)) && collisionList.get(j) instanceof Player) ||
                        (collisionList.get(j) instanceof Bullet && enemyBullets.contains(collisionList.get(j)) && collisionList.get(i) instanceof Player)) {
                    // Player-SaucerBullet
                    Bullet bullet;
                    Player player;
                    if (collisionList.get(i) instanceof Bullet) {
                        bullet = (Bullet) collisionList.get(i);
                        player = (Player) collisionList.get(j);
                    } else {
                        bullet = (Bullet) collisionList.get(j);
                        player = (Player) collisionList.get(i);
                    }
                    // Bullet-Player collision
                    if (!player.isHit()) {
                        // if Player intersects asteroid
                        if (player.contains(bullet.getX(), bullet.getY())) {
                            player.hit();
                            enemyBullets.remove(bullet);
                            Jukebox.play("explode", 0.3f);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void splitAsteroids(Asteroid a) {
        createParticles(a.getX(), a.getY());
        numAsteroidsLeft--;
        currentDelay = ((maxDelay - minDelay) * numAsteroidsLeft / totalAsteroids) + minDelay;
        if (a.getType() == Asteroid.LARGE) {
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.MEDIUM));
        } else if (a.getType() == Asteroid.MEDIUM) {
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
            asteroids.add(new Asteroid(a.getX(), a.getY(), Asteroid.SMALL));
        }
    }
}
