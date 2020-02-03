package de.swerik.luna.ecs;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import de.swerik.luna.ecs.systems.RenderSystem;

public class LunaEngine extends Engine {
    public void render() {
        for (EntitySystem system : super.getSystems()) {
            if (system instanceof RenderSystem) {
                RenderSystem renderSystem = (RenderSystem) system;
                if (renderSystem.checkProcessing()) {
                    for (Entity entity : renderSystem.getEntities()) {
                        renderSystem.render(entity);
                    }
                }
            }
        }
    }
}
