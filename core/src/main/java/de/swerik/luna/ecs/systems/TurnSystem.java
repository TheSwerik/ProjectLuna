package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.swerik.luna.ecs.components.graphics.RenderableComponent;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;

public class TurnSystem extends IteratingSystem {
    private final ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

    public TurnSystem(SpriteBatch batch) {
        super(Family.all(SpriteComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Sprite sprite = sm.get(entity).sprites.first();
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            sprite.setFlip(!sprite.isFlipX(), false);
        }
    }
}
