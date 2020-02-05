package de.swerik.luna.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.swerik.luna.ecs.components.physics.PositionComponent;
import de.swerik.luna.ecs.components.graphics.RenderableComponent;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;

public class RenderSystem extends IteratingSystem {
    private final SpriteBatch batch;

    private ComponentMapper<SpriteComponent> sm ;

    public RenderSystem(SpriteBatch batch) {
        super(Aspect.all(RenderableComponent.class, SpriteComponent.class, PositionComponent.class));
        this.batch = batch;
    }

    @Override
    protected void process(int entity) {
        for (Sprite sprite : sm.get(entity).sprites) {
            sprite.draw(batch);
        }
    }
}
