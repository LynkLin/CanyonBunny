package com.lynk.game.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lynk on 14-6-30.
 */
public abstract class AbstractGameObject {
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;

    /**
     * Object's current speed in m/s
     */
    public Vector2 velocity;
    /**
     * Object's positive and negative maximum speed in m/s
     */
    public Vector2 terminalVelocity;
    /**
     * This is an opposing force that slows down the object until
     * its velocity equals zero. This value is given as a coefficient
     * that is dimensionless. A value of zero means no friction, and
     * the object's velocity will not decrease.
     */
    public Vector2 friction;
    /**
     * Object's constant acceleration in m/s2
     */
    public Vector2 acceleration;
    /**
     * The object's bounding box describes the physical body that will
     * be used for collision detection with other objects. The bounding
     * box can be set to any size and completely independent of the actual
     * dimension of the object in the game world.
     */
    public Rectangle bounds;

    protected AbstractGameObject() {
        position = new Vector2();
        dimension = new Vector2();
        origin = new Vector2();
        scale = new Vector2();
        rotation = 0;
        velocity = new Vector2();
        terminalVelocity = new Vector2(1, 1);
        friction = new Vector2();
        acceleration = new Vector2();
        bounds = new Rectangle();
    }

    public void update(float deltaTime) {
        updateMotionX(deltaTime);
        updateMotionY(deltaTime);
        //Move to new position
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
    }

    public abstract void render(SpriteBatch batch);

    protected void updateMotionX (float deltaTime) {
        if (velocity.x != 0) {
            //Apply friction
            if (velocity.x > 0) {
                velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
            } else {
                velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
            }
        }
        //Apply acceleration
        velocity.x += acceleration.x * deltaTime;
        velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
    }

    protected void updateMotionY (float deltaTime) {
        if (velocity.y != 0) {
            //Apply friction
            if (velocity.y > 0) {
                velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
            } else {
                velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
            }
        }
        //Apply acceleration
        velocity.y += acceleration.y * deltaTime;
        velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
    }
}
