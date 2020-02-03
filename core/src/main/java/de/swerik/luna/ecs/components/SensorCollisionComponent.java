package de.swerik.luna.ecs.components;

import com.badlogic.ashley.core.Component;

public class SensorCollisionComponent implements Component {
    public short numFoot = 0;
    public short numRightWall = 0;
    public short numLeftWall = 0;
    public short numRight = 0;
    public short numRightUpper = 0;
    public short numLeft = 0;
    public short numLeftUpper = 0;
}
