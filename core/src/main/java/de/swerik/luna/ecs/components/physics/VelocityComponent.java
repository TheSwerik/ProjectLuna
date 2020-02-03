package de.swerik.luna.ecs.components.physics;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float x = 0f;
    public float y = 0f;

    public VelocityComponent(float x,float y) {
        this.x = x;
        this.y = y;
    }
}
