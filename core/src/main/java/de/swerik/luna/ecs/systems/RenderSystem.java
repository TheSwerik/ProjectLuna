package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.swerik.luna.Luna;
import de.swerik.luna.ecs.components.PositionComponent;
import de.swerik.luna.ecs.components.RenderableComponent;
import de.swerik.luna.ecs.components.SpriteComponent;
import de.swerik.luna.manager.LogManager;

public class RenderSystem extends EntitySystem {
    private final Luna app;
    private final SpriteBatch batch;
    private ImmutableArray<Entity> entities;

    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);

    public RenderSystem(SpriteBatch batch, Luna app) {
        this.batch = batch;
        this.app = app;
    }

    @Override
    public void addedToEngine(Engine engine) {
        if (engine != null) {
            entities = engine.getEntitiesFor(Family.all(RenderableComponent.class, SpriteComponent.class, PositionComponent.class).get());
        } else {
            app.logger.log("RenderSystem: Ashley Engine is null.", LogManager.ERROR);
        }
    }

    @Override
    public void update(float delta) {
        batch.begin();
        for (Entity entity : entities) {
            SpriteComponent sprite = entity.getComponent(SpriteComponent.class);
            PositionComponent position = entity.getComponent(PositionComponent.class);
            batch.draw(sprite.sprite, position.x, position.y);
        }
        batch.end();
    }
}
