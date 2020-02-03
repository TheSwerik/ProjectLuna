package de.swerik.luna.ecs.components;

import com.badlogic.ashley.core.Component;

public class VelocityComponent implements Component {
    public float velocity = 0f;

    public VelocityComponent(float velocity) {
        this.velocity = velocity;
    }
}
