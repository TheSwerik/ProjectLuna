package de.swerik.luna.ecs.components.states;

import com.artemis.Component;

public class EntityStateComponent extends Component {
    public static enum State {
        AIRBORN,
        GROUNDED
    }
    public State state;

    public EntityStateComponent() {
        this.state = State.GROUNDED;
    }
}
