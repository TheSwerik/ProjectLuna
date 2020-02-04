package de.swerik.luna.utils;

public class Variables {
    public static final float PIXELS_TO_METERS = 0.01f; // 100 pixels = 1 Meter
    public static final int METERS_TO_PIXELS = 100; // 1 Meter = 100 pixels

    // Filters:
    public static final short COLLISION_PLAYER = -1;
    public static final short COLLISION_LEVEL = -2;
    public static final short COLLISION_ENEMY = -3;

    public static final short FRIENDLY_BITS = 0x0001;
    public static final short ENEMY_BITS = 0x0002;
    public static final short LEVEL_BITS = 0x0004;
    public static final short NEUTRAL_BITS = 0x0008;

    public static final short FOOT_SENSOR = 0x0010;
    public static final short RIGHT_WALL_SENSOR = 0x0020;
    public static final short LEFT_WALL_SENSOR = 0x0040;


}
