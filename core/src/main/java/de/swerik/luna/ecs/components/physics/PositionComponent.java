package de.swerik.luna.ecs.components.physics;

import com.artemis.Component;

public class PositionComponent extends Component {
    public float x = 0f;
    public float y = 0f;

    public PositionComponent() {
    }

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
