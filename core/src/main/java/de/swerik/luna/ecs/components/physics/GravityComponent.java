package de.swerik.luna.ecs.components.physics;

import com.badlogic.ashley.core.Component;

public class GravityComponent implements Component {
    public float gravity = 0;

    public GravityComponent(float gravity) {
        this.gravity = gravity;
    }
}
