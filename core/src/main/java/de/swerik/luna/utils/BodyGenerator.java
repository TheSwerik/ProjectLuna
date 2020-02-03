package de.swerik.luna.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import static de.swerik.luna.utils.Variables.PIXELS_TO_METERS;

public class BodyGenerator {
    private static World world;

    public static void setWorld(World world) {
        BodyGenerator.world = world;
    }

    public static Body generate(Entity entity, Sprite owner, String filePath, short filterCategory) {
        // parse JSON:
        JsonValue root = new JsonReader().parse(Gdx.files.internal(filePath));

        // BodyDef:
        short maskingBits = (short) ((Variables.FRIENDLY_BITS | Variables.ENEMY_BITS | Variables.NEUTRAL_BITS | Variables.LEVEL_BITS) ^ filterCategory);

        BodyDef bdef = new BodyDef();

        String bodyType = root.get("BodyDef").getString("type");
        if (bodyType.equalsIgnoreCase("Dynamic")) {
            bdef.type = BodyDef.BodyType.DynamicBody;
        } else if (bodyType.equalsIgnoreCase("Static")) {
            bdef.type = BodyDef.BodyType.StaticBody;
        } else if (bodyType.equalsIgnoreCase("Kinematic")) {
            bdef.type = BodyDef.BodyType.KinematicBody;
        } else {
            System.out.println("Didnt work");
        }

        bdef.fixedRotation = root.get("BodyDef").getBoolean("fixedRotation");

        bdef.position.set((owner.getX() + owner.getWidth() / 2) * PIXELS_TO_METERS,
                (owner.getY() + owner.getHeight() / 2) * PIXELS_TO_METERS);

        Body body = world.createBody(bdef);

        // Fixtures:
        JsonValue fixtures = root.get("Fixtures");
        for (JsonValue fixture : fixtures) {
            String type = fixture.getString("type");

            Shape shape;
            if (type.equalsIgnoreCase("Polygon")) {
                shape = new PolygonShape();
                ((PolygonShape) shape).setAsBox(fixture.getFloat("width") * PIXELS_TO_METERS,
                        fixture.getFloat("height") * PIXELS_TO_METERS,
                        new Vector2((body.getLocalCenter().x + fixture.getFloat("x")) * PIXELS_TO_METERS,
                                (body.getLocalCenter().y + fixture.getFloat("y")) * PIXELS_TO_METERS), 0f);
            } else if (type.equalsIgnoreCase("Circle")) {
                shape = new CircleShape();
                shape.setRadius(fixture.getFloat("radius") * PIXELS_TO_METERS);
                ((CircleShape) shape).setPosition(new Vector2((body.getLocalCenter().x + fixture.getFloat("x")) * PIXELS_TO_METERS,
                        (body.getLocalCenter().y + fixture.getFloat("y")) * PIXELS_TO_METERS));
            } else {
                System.out.println("Didnt work");
                continue;
            }

            FixtureDef fdef = new FixtureDef();
            fdef.shape = shape;
            fdef.isSensor = fixture.getBoolean("isSensor");

            if (fdef.isSensor) {
//                fdef.filter.categoryBits = (short) (filterCategory << fixture.getShort("bitShifts"));
//                fdef.filter.maskBits = Variables.LEVEL_BITS;
            } else {
                fdef.friction = fixture.getFloat("friction");
                fdef.restitution = fixture.getFloat("restitution");
                fdef.density = fixture.getFloat("density");
//                fdef.filter.categoryBits = filterCategory;
//                fdef.filter.maskBits = maskingBits;
            }

            body.createFixture(fdef).setUserData(entity);
            shape.dispose();
        }

        return body;
        //TODO log errors
    }
}
