package de.swerik.ForeignTest.GameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import de.swerik.ForeignTest.ForeignGame;
import de.swerik.ForeignTest.Managers.GameKeys;
import de.swerik.ForeignTest.Managers.GameStateManager;
import de.swerik.ForeignTest.Managers.Save;

public class HighscoreState extends GameState {

    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont font;

    private final String title = "Highscores";

    private long[] highscores;
    private String[] names;

    public HighscoreState(GameStateManager gsm) {
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

        Save.load();
        highscores = Save.gd.getHighscores();
        names = Save.gd.getNames();
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

        float maxOffset = highscores.length * 40;
        //Draw Scores
        for (int i = 0; i < highscores.length; i++) {
            GlyphLayout highscoreLayout = new GlyphLayout();
            highscoreLayout.setText(font, String.format(
                    "%2d. %7s %s",
                    i + 1,
                    highscores[i],
                    names[i]
            ));
            font.draw(batch, highscoreLayout, (ForeignGame.WIDTH - highscoreLayout.width) / 2, ForeignGame.HEIGHT / 2f + maxOffset / 2 - 40 * i);
        }

        batch.end();
    }

    @Override
    public void handleInput() {
        if (GameKeys.isPressed(GameKeys.ESCAPE) || GameKeys.isPressed(GameKeys.ENTER)) {
            gsm.setState(GameStateManager.MENU);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        titleFont.dispose();
        font.dispose();
    }
}
