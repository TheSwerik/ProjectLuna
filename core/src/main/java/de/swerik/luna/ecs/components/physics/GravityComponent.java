package de.swerik.luna.ecs.components.physics;

import com.artemis.Component;

public class GravityComponent extends Component {
    public float gravity = 0;

    public GravityComponent() {
    }

    public GravityComponent(float gravity) {
        this.gravity = gravity;
    }
}
