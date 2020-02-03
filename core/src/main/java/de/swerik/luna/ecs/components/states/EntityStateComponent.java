package de.swerik.luna.ecs.components.states;

import com.badlogic.ashley.core.Component;

public class EntityStateComponent implements Component {
    public static enum State {
        AIRBORN,
        GROUNDED
    }
    public State state;

    public EntityStateComponent() {
        this.state = State.GROUNDED;
    }
}
