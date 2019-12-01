package de.swerik.Box2D_Tutorial.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class MyInput extends InputAdapter {

    public static boolean isButton1Pressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.LEFT) || Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    public static boolean isButton1JustPressed() {
        return Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) || Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE);
    }

    public static boolean isButton2Pressed() {
        return Gdx.input.isButtonPressed(Input.Buttons.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.W);
    }

    public static boolean isButton2JustPressed() {
        return Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT) || Gdx.input.isKeyJustPressed(Input.Keys.W);
    }
}
