package de.swerik.luna.ecs.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public short type;

    public TypeComponent(short type) {
        this.type = type;
    }
}
