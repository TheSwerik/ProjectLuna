package de.swerik.luna.ecs.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;

import static de.swerik.luna.utils.Variables.PPM;

public class PositionSystem extends IteratingSystem {
    private final ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private final ComponentMapper<SpriteComponent> sm = ComponentMapper.getFor(SpriteComponent.class);
    private final ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

    public PositionSystem() {
        super(Family.all(PositionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float delta) {
        PositionComponent pCom = pm.get(entity);
        SpriteComponent sCom = sm.get(entity);
        BodyComponent bCom = bm.get(entity);

        if (bCom != null) {
            pCom.x = bCom.body.getPosition().x * PPM - sCom.sprites.get(0).getWidth() / 2;
            pCom.y = bCom.body.getPosition().y * PPM - sCom.sprites.get(0).getHeight() / 2;
        }

        if (sCom != null) {
            for (Sprite s : sCom.sprites) {
                s.setX(pCom.x);
                s.setY(pCom.y);
            }
        }
    }
}
