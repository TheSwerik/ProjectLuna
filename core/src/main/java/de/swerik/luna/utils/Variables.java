package de.swerik.luna.utils;

public class Variables {
    public static final float PIXELS_TO_METERS              = 0.01f; // 100 pixels = 1 Meter
    public static final int METERS_TO_PIXELS                = 100; // 1 Meter = 100 pixels

    // Filters:
    public static final short COLLISION_PLAYER              = -1;
    public static final short COLLISION_LEVEL               = -2;
    public static final short COLLISION_ENEMY               = -3;

    public static final short FRIENDLY_BITS                 = 0x0001;
    public static final short ENEMY_BITS                    = 0x0002;
    public static final short LEVEL_BITS                    = 0x0003;
    public static final short NEUTRAL_BITS                  = 0x0004;

    public static final short FRIENDLY_FOOT_SENSOR          = 0x0010;
    public static final short ENEMY_FOOT_SENSOR             = 0x0020;
    public static final short FRIENDLY_RIGHT_SENSOR         = 0x0030;
    public static final short FRIENDLY_RIGHT_UPPER_SENSOR   = 0x0040;

    public static final short FRIENDLY_LEFT_SENSOR          = 0x0100;
    public static final short FRIENDLY_LEFT_UPPER_SENSOR    = 0x0200;
    public static final short FRIENDLY_RIGHT_WALL_SENSOR    = 0x0300;
    public static final short FRIENDLY_LEFT_WALL_SENSOR     = 0x0400;


}
