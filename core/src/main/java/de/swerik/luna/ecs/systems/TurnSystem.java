package de.swerik.luna.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.swerik.luna.ecs.components.graphics.RenderableComponent;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;

public class TurnSystem extends IteratingSystem {
    private  ComponentMapper<SpriteComponent> sm;

    public TurnSystem(SpriteBatch batch) {
        super(Aspect.all(SpriteComponent.class));
    }

    @Override
    protected void process(int entity) {
        Sprite sprite = sm.get(entity).sprites.first();
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            sprite.setFlip(!sprite.isFlipX(), false);
        }
    }
}
