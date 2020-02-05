package de.swerik.luna.ecs.components;

import com.artemis.Component;

public class TypeComponent extends Component {
    public short type;

    public TypeComponent() {
    }

    public TypeComponent(short type) {
        this.type = type;
    }
}
