package de.swerik.luna.manager;

import com.badlogic.gdx.InputProcessor;

import static com.badlogic.gdx.Input.Keys;

public class Input implements InputProcessor {
    public static byte xMovement = 0;

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.A) {
            xMovement = -1;
            return true;
        } else if (keycode == Keys.D) {
            xMovement = 1;
            return true;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A) {
            xMovement = 0;
            return true;
        } else if (keycode == Keys.D) {
            xMovement = 0;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
