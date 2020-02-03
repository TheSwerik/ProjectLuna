package de.swerik.luna.ecs.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class MomentumComponent implements Component {
    public Vector2 momentum ;

    public MomentumComponent(Vector2 momentum) {
        this.momentum = momentum;
    }
}
