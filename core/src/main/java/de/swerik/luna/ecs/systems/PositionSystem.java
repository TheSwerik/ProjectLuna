package de.swerik.luna.ecs.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Sprite;
import de.swerik.luna.ecs.components.graphics.SpriteComponent;
import de.swerik.luna.ecs.components.physics.BodyComponent;
import de.swerik.luna.ecs.components.physics.PositionComponent;

import static de.swerik.luna.utils.Variables.PPM;

public class PositionSystem extends IteratingSystem {
    private  ComponentMapper<PositionComponent> pm ;
    private  ComponentMapper<SpriteComponent> sm ;
    private  ComponentMapper<BodyComponent> bm ;

    public PositionSystem() {
        super(Aspect.all(PositionComponent.class));
    }

    @Override
    protected void process(int entity) {
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
