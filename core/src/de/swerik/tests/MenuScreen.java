package de.swerik.tests;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen extends AbstractScreen {
    static SpriteBatch batch;

    static BitmapFont font;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,1f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        batch = new SpriteBatch();
        batch.begin();
        font.draw(batch,"press to start",200,200);
        batch.end();

        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            game.setScreen(new GameScreen(game));
        }
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
