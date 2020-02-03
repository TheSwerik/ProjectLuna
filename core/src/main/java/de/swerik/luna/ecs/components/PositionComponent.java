package de.swerik.luna.ecs.components;

import com.badlogic.ashley.core.Component;

public class PositionComponent implements Component {
    public float x = 0f;
    public float y = 0f;

    public PositionComponent(float x, float y) {
        this.x = x;
        this.y = y;
    }
}