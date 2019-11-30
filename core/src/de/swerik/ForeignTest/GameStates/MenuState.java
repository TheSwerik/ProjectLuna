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

public class MenuState extends GameState {

    private SpriteBatch batch;
    private BitmapFont titleFont;
    private BitmapFont font;

    private final String title = "Asteroids";

    private int currentItem;
    private String[] menuItems;

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

    }
}
