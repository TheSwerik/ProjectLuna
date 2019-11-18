package de.swerik.luna;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Timer;

public class InputHandler implements InputProcessor {
    Sprite sprite;
    float movement = 0f;
    boolean movingLeft=false;
    Animation animation;

    public InputHandler(Sprite sprite, Animation animation) {
        this.sprite = sprite;
        this.animation = animation;
    }

    public float getMovement() {
        return movement;
    }
    public boolean isMovingLeft() {
        return movingLeft;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            if (sprite.getX() > -100) {
                movement = -5f;
                movingLeft=true;
            }
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            if (sprite.getX() < 1000) {
                movement = 5f;
                movingLeft=false;
            }
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            if (sprite.getX() > -100) {
                movement = 0f;
            }
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            if (sprite.getX() < 1000) {
                movement = 0f;
            }
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button== Input.Buttons.LEFT){
            animation.start();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(button== Input.Buttons.LEFT){
            animation.stop();
        }
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

    private void testInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (sprite.getX() > -100) {
                sprite.translateX(-5f);
            }
            if (!sprite.isFlipX()) {
                sprite.flip(true, false);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (sprite.getX() < 1000) {
                sprite.translateX(5f);
            }
        }
    }
}
