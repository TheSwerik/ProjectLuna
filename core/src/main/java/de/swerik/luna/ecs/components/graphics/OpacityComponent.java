package de.swerik.luna.ecs.components.graphics;

import com.artemis.Component;

public class OpacityComponent extends Component {
    public float opacity = 1;

    public OpacityComponent() {
    }

    public OpacityComponent(float opacity) {
        this.opacity = opacity;
    }
}
