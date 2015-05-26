package com.lynk.game.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lynk.game.canyonbunny.game.Assets;
import com.lynk.game.canyonbunny.util.Constants;

/**
 * Created by IT on 2015-05-26.
 */
public class BunnyHead extends AbstractGameObject {
    public static final String TAG = BunnyHead.class.getName();

    private final float JUMP_TIME_MAX = 0.3f;
    private final float JUMP_TIME_MIN = 0.1f;
    private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;

    public enum  VIEW_DIRECTION {LEFT, RIGHT}

    public enum  JUMP_STATE {GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING}

    private TextureRegion regHead;

    public VIEW_DIRECTION viewDirection;
    public float timeJumping;
    public JUMP_STATE jumpState;
    public boolean hasFeatherPowerup;
    public float timeLeftFeatherPowerup;

    public BunnyHead() {
        init();
    }

    public void init() {
        dimension.set(1, 1);
        regHead = Assets.instance.bunny.head;
        //Center image on game object
        origin.set(dimension.x / 2, dimension.y / 2);
        //Bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        //Set physics values
        terminalVelocity.set(3f, 4f);
        friction.set(12f, -25f);
        //View direction
        viewDirection = VIEW_DIRECTION.RIGHT;
        //Jump state
        jumpState = JUMP_STATE.FALLING;
        timeJumping = 0;
        //Power-ups
        hasFeatherPowerup = false;
        timeLeftFeatherPowerup = 0;
    }

    public void setJumping(boolean jumpKeyPressed) {
        switch (jumpState) {
            case GROUNDED:
                if (jumpKeyPressed) {
                    timeJumping = 0;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }

                break;
            case JUMP_RISING:
                if (!jumpKeyPressed) {
                    jumpState = JUMP_STATE.JUMP_FALLING;
                }
                break;
            case FALLING:
            case JUMP_FALLING:
                if (jumpKeyPressed && hasFeatherPowerup) {
                    timeJumping = JUMP_TIME_OFFSET_FLYING;
                    jumpState = JUMP_STATE.JUMP_RISING;
                }
                break;
        }
    }

    public void setFeatherPowerup(boolean pickedUp) {
        hasFeatherPowerup = pickedUp;
        if (pickedUp) {
            timeLeftFeatherPowerup = Constants.ITEM_FEATHER_POWER_DURATION;
        }
    }

    public boolean hasFeatherPowerup() {
        return hasFeatherPowerup && timeLeftFeatherPowerup > 0;
    }


    @Override
    public void render(SpriteBatch batch) {
        TextureRegion reg = null;
        if (hasFeatherPowerup) {
            batch.setColor(1f, 0.8f, 0f, 1f);
        }
        reg = regHead;
        batch.draw(reg.getTexture(), position.x, position.y, origin.x,
                origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
                reg.getRegionX(),reg.getRegionY(), reg.getRegionWidth(),
                reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);
        batch.setColor(1, 1, 1, 1);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (velocity.x != 0) {
            viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT: VIEW_DIRECTION.RIGHT;
        }
        if (timeLeftFeatherPowerup > 0) {
            timeLeftFeatherPowerup -= deltaTime;
            if (timeLeftFeatherPowerup < 0) {
                //disable power-up
                timeLeftFeatherPowerup = 0;
                setFeatherPowerup(false);
            }
        }
    }

    @Override
    protected void updateMotionY(float deltaTime) {
        switch (jumpState) {
            case GROUNDED:
                jumpState = JUMP_STATE.JUMP_FALLING;
                break;
            case JUMP_RISING:
                timeJumping+= deltaTime;
                if (timeJumping <= JUMP_TIME_MAX) {
                    velocity.y = terminalVelocity.y;
                }
                break;
            case FALLING:
                break;
            case JUMP_FALLING:
                timeJumping += deltaTime;
                if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
                    velocity.y = terminalVelocity.y;
                }
        }
        if (jumpState != JUMP_STATE.GROUNDED) {
            super.updateMotionY(deltaTime);
        }
    }
}
