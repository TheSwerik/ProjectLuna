package de.swerik.luna.ecs.components.states;

import com.artemis.Component;

public class SensorCollisionComponent extends Component {
    public short numFoot = 0;
    public short numRightWall = 0;
    public short numLeftWall = 0;

    public SensorCollisionComponent() {
    }
}
