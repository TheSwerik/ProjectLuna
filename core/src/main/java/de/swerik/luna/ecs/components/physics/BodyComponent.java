package de.swerik.luna.ecs.components.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

import static de.swerik.luna.utils.Variables.PIXELS_TO_METERS;

public class BodyComponent implements Component {
    public Body body;

    public BodyComponent(PositionComponent positionComponent, Body body) {
        setBody(positionComponent, body);
    }

    public void setBody(PositionComponent positionComponent, Body body) {
        this.body = body;
        this.body.setTransform(positionComponent.x * PIXELS_TO_METERS, positionComponent.y * PIXELS_TO_METERS, 0);
    }
}
