package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.swerik.luna.ecs.components.physics.PositionComponent;
import de.swerik.luna.ecs.components.graphics.RenderableComponent;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;

public class RenderSystem extends IteratingSystem {
    private final SpriteBatch batch;

    private final ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);

    public RenderSystem(SpriteBatch batch) {
        super(Family.all(RenderableComponent.class, SpriteComponent.class, PositionComponent.class).get());
        this.batch = batch;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
    }

    public void render(Entity entity) {
        for (Sprite sprite : sm.get(entity).sprites) {
            sprite.draw(batch);
        }
    }
}
