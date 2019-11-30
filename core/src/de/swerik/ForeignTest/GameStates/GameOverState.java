package de.swerik.ForeignTest.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.ForeignTest.Managers.GameKeys;
import de.swerik.ForeignTest.Managers.GameStateManager;
import de.swerik.ForeignTest.Managers.Save;

public class GameOverState extends GameState {

    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont font;

    private final String title = "Game Over";

    private boolean newHighScore;
    private char[] newName;
    private int currentChar;

    private ShapeRenderer sr;

    public GameOverState(GameStateManager gsm) {
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

        newHighScore = Save.gd.isHighscore(Save.gd.getTentScore());
        if (newHighScore) {
            newName = new char[]{'A', 'A', 'A'};
            currentChar = 0;
        }

        sr = new ShapeRenderer();
    }

    @Override
    public void update(float delta) {
        handleInput();
    }

    @Override
    public void draw() {
        batch.setProjectionMatrix(ForeignGame.cam.combined);
        batch.begin();

        //Draw Title
        GlyphLayout titleLayout = new GlyphLayout();
        titleLayout.setText(titleFont, title);
        titleFont.draw(batch, titleLayout, (ForeignGame.WIDTH - titleLayout.width) / 2, ForeignGame.HEIGHT - 40);

        if (!newHighScore) {
            batch.end();
            return;
        }

        //Draw Score
        GlyphLayout highscoreLayout = new GlyphLayout();
        highscoreLayout.setText(font, "New High Score:" + Save.gd.getTentScore());
        font.draw(batch, highscoreLayout, (ForeignGame.WIDTH - highscoreLayout.width) / 2 - 150, (ForeignGame.HEIGHT - highscoreLayout.height) / 2);

        //Enter Name
        GlyphLayout nameLayout = new GlyphLayout();
        for (int i = 0; i < newName.length; i++) {
            nameLayout.setText(font, Character.toString(newName[i]));
            font.draw(batch, nameLayout, (ForeignGame.WIDTH - nameLayout.width) / 2 + 100 + (14 * i), (ForeignGame.HEIGHT - nameLayout.height) / 2);
        }

        batch.end();

        sr.setProjectionMatrix(ForeignGame.cam.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);

        sr.line(
                (ForeignGame.WIDTH - nameLayout.width) / 2 + 100 + (14 * currentChar) - 3,
                (ForeignGame.HEIGHT - nameLayout.height) / 2 - nameLayout.height,
                (ForeignGame.WIDTH - nameLayout.width) / 2 + 100 + (14 * (currentChar + 1)) - 3,
                (ForeignGame.HEIGHT - nameLayout.height) / 2 - nameLayout.height
        );

        sr.end();
    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.ESCAPE) || GameKeys.isPressed(GameKeys.ENTER)) {
            if (newHighScore) {
                Save.gd.addHighscore(Save.gd.getTentScore(), new String(newName));
                Save.save();
            }
            gsm.setState(GameStateManager.MENU);
        }
        if (GameKeys.isPressed(GameKeys.UP)) {
            if (newName[currentChar] == ' ') {
                newName[currentChar] = 'Z';
            } else {
                newName[currentChar]--;
                if (newName[currentChar] < 'A') {
                    newName[currentChar] = ' ';
                }
            }
        }
        if (GameKeys.isPressed(GameKeys.DOWN)) {
            if (newName[currentChar] == ' ') {
                newName[currentChar] = 'A';
            } else {
                newName[currentChar]++;
                if (newName[currentChar] > 'Z') {
                    newName[currentChar] = ' ';
                }
            }
        }
        if (GameKeys.isPressed(GameKeys.RIGHT)) {
            if (currentChar < newName.length - 1) {
                currentChar++;
            }
        }
        if (GameKeys.isPressed(GameKeys.LEFT)) {
            if (currentChar > 0) {
                currentChar--;
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        font.dispose();
        sr.dispose();
    }
}
