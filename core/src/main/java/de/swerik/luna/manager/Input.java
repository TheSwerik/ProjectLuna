package de.swerik.luna.manager;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.Input.Keys;

public class Input implements InputProcessor {
    public static Vector2 keyForce = new Vector2(0, 0);

    @Override
    public boolean keyDown(int keycode) {
        keyForce.x = 0;
        if (keycode == Keys.A) {
            keyForce.x -= 1;
        }
        if (keycode == Keys.D) {
            keyForce.x += 1;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A) {
            keyForce.x = 0;
            return true;
        } else if (keycode == Keys.D) {
            keyForce.x = 0;
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
