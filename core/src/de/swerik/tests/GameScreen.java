package de.swerik.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen extends AbstractScreen {
    SpriteBatch batch;

    BitmapFont font;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            game.setScreen(new MenuScreen(game));
        }
        batch = new SpriteBatch();
        font = new BitmapFont();
        batch.begin();
        font.draw(batch, "Hello, this works.", 200, 200);
        batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void hide() {

    }
}
