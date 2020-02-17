package de.swerik.luna.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import de.swerik.luna.manager.Logger;

import static de.swerik.luna.utils.Variables.MPP;

public class BodyGenerator {

    public static Body generate(int entity, Sprite owner, String filePath, short filterCategory, World world) {
        // parse JSON:
        JsonValue root = new JsonReader().parse(Gdx.files.internal(filePath));

        // BodyDef:
        short maskingBits = (short) ((Variables.FRIENDLY_BITS | Variables.ENEMY_BITS | Variables.NEUTRAL_BITS | Variables.LEVEL_BITS) ^ filterCategory);
        // TODO:
        if (filePath.contains("Wall"))
            maskingBits = (short) ((Variables.FRIENDLY_BITS | Variables.ENEMY_BITS | Variables.NEUTRAL_BITS | Variables.FOOT_SENSOR | Variables.RIGHT_WALL_SENSOR | Variables.LEFT_WALL_SENSOR) ^ filterCategory);


        BodyDef bdef = new BodyDef();

        String bodyType = root.get("BodyDef").getString("type");
        if (bodyType.equalsIgnoreCase("Dynamic")) {
            bdef.type = BodyDef.BodyType.DynamicBody;
        } else if (bodyType.equalsIgnoreCase("Static")) {
            bdef.type = BodyDef.BodyType.StaticBody;
        } else if (bodyType.equalsIgnoreCase("Kinematic")) {
            bdef.type = BodyDef.BodyType.KinematicBody;
        } else {
            Logger.log("Cannot parse BodyType: " + bodyType + " for: " + filePath, Logger.ERROR);
        }

        bdef.fixedRotation = root.get("BodyDef").getBoolean("fixedRotation");

        bdef.position.set((owner.getX() + owner.getWidth() / 2) * MPP,
                (owner.getY() + owner.getHeight() / 2) * MPP);

        Body body = world.createBody(bdef);

        // Fixtures:
        JsonValue fixtures = root.get("Fixtures");
        for (JsonValue fixture : fixtures) {
            String type = fixture.getString("type");

            Shape shape;
            if (type.equalsIgnoreCase("Polygon")) {
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(fixture.getFloat("width") * MPP,
                        fixture.getFloat("height") * MPP,
                        new Vector2((body.getLocalCenter().x + fixture.getFloat("x")) * MPP,
                                (body.getLocalCenter().y + fixture.getFloat("y")) * MPP), 0f);
            } else if (type.equalsIgnoreCase("Circle")) {
                shape = new CircleShape();
                shape.setRadius(fixture.getFloat("radius") * MPP);
                ((CircleShape) shape).setPosition(new Vector2((body.getLocalCenter().x + fixture.getFloat("x")) * MPP,
                        (body.getLocalCenter().y + fixture.getFloat("y")) * MPP));
            } else {
                Logger.log("Cannot parse Shape type: " + type + " for: " + filePath, Logger.ERROR);
                continue;
            }

            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            fdef.isSensor = fixture.getBoolean("isSensor");

            if (fdef.isSensor) {
                fdef.filter.categoryBits = (short) (filterCategory << fixture.getShort("bitShifts"));
                fdef.filter.maskBits = Variables.LEVEL_BITS;
            } else {
                fdef.friction = fixture.getFloat("friction");
                fdef.restitution = fixture.getFloat("restitution");
                fdef.density = fixture.getFloat("density");
                fdef.filter.categoryBits = filterCategory;
                fdef.filter.maskBits = maskingBits;
            }

            body.createFixture(fdef).setUserData(entity);
            shape.dispose();
        }

        return body;
    }
}
