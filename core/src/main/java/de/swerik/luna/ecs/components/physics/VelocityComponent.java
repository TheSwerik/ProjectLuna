package de.swerik.luna.ecs.components.physics;

import com.artemis.Component;

public class VelocityComponent extends Component {
    public float x = 0f;
    public float y = 0f;

    public VelocityComponent() {
    }

    public VelocityComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
