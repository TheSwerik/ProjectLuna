package de.swerik.ForeignTest.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import de.swerik.ForeignTest.Entities.Asteroid;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.ForeignTest.Managers.GameKeys;
import de.swerik.ForeignTest.Managers.GameStateManager;
import de.swerik.ForeignTest.Managers.Save;

import java.util.ArrayList;

public class MenuState extends GameState {

    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont font;

    private final String title = "Asteroids";

    private int currentItem;
    private String[] menuItems;

    private ArrayList<Asteroid> asteroids;
    private ShapeRenderer sr;

    public MenuState(GameStateManager gsm) {
        super(gsm);
    }

    @Override
    public void init() {
        batch = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/lunchds.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        font = gen.generateFont(parameter);
        parameter.size = 56;
        titleFont = gen.generateFont(parameter);
        titleFont.setColor(Color.WHITE);

        currentItem = 0;
        menuItems = new String[]{
                "Play",
                "Highscores",
                "Quit"
        };

        Save.load();

        asteroids = new ArrayList<>();
        spawnAsteroids();
        sr = new ShapeRenderer();
    }

    @Override
    public void update(float delta) {
        handleInput();

        //update Asteroids
        for (int i = 0; i < asteroids.size(); i++) {
            asteroids.get(i).update(delta);
            if (asteroids.get(i).shouldRemove()) {
                asteroids.remove(i);
                i--;
            }
        }
    }

    @Override
    public void draw() {
        //draw Asteroids
        sr.setProjectionMatrix(ForeignGame.cam.combined);
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(sr);
        }

        batch.setProjectionMatrix(ForeignGame.cam.combined);
        batch.begin();

        //Draw Title
        GlyphLayout titleLayout = new GlyphLayout();
        titleLayout.setText(titleFont, title);
        titleFont.draw(batch, titleLayout, (ForeignGame.WIDTH - titleLayout.width) / 2, ForeignGame.HEIGHT - 40);

        //Draw Menu
        for (int i = 0; i < menuItems.length; i++) {
            GlyphLayout menuLayout = new GlyphLayout();
            if (currentItem == i) {
                font.setColor(Color.RED);
            } else {
                font.setColor(Color.WHITE);
            }
            menuLayout.setText(font, menuItems[i]);
            font.draw(batch, menuLayout, (ForeignGame.WIDTH - menuLayout.width) / 2, ForeignGame.HEIGHT / 2f - (menuLayout.height + 10) * (i - 1));
        }

        batch.end();
    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.UP)) {
//            if (--currentItem < 0) {
//                currentItem = 0;
//            }
            if (currentItem > 0) {
                currentItem--;
            }
        }
        if (GameKeys.isPressed(GameKeys.DOWN)) {
//            if (++currentItem >= menuItems.length) {
//                currentItem = menuItems.length - 1;
//            }
            if (currentItem < menuItems.length - 1) {
                currentItem++;
            }
        }
        if (GameKeys.isPressed(GameKeys.ENTER)) {
            select();
        }
    }

    private void select() {
        switch (currentItem) {
            case 0:
                gsm.setState(GameStateManager.PLAY);
                break;
            case 1:
                gsm.setState(GameStateManager.HIGHSCORE);
                break;
            case 2:
                Gdx.app.exit();
                break;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        font.dispose();
        sr.dispose();
    }

    private void spawnAsteroids() {
        asteroids.clear();

        int numToSpawn = 10;

        for (int i = 0; i < numToSpawn; i++) {
            float dist;
            float x;
            float y;
            do {
                x = MathUtils.random(ForeignGame.WIDTH);
                y = MathUtils.random(ForeignGame.HEIGHT);

                float dx = x;
                float dy = y;
                dist = (float) Math.sqrt(dx * dx + dy * dy);
            } while (dist < 100);

            asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
        }
    }
}
