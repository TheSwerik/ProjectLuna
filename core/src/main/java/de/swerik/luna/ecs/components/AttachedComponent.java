package de.swerik.luna.ecs.components;

import com.artemis.Component;
import com.artemis.Entity;

public class AttachedComponent extends Component {
    public Entity attachedTo;

    public AttachedComponent() {
    }

    public AttachedComponent(Entity attachedTo) {
        this.attachedTo = attachedTo;
    }
}
