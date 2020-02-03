package de.swerik.luna.ecs.components.graphics;

import com.badlogic.ashley.core.Component;

public class OpacityComponent implements Component {
    public float opacity = 1;

    public OpacityComponent(float opacity) {
        this.opacity = opacity;
    }
}
