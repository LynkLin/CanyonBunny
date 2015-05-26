package com.lynk.game.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.lynk.game.canyonbunny.util.CameraHelper;
import com.lynk.game.canyonbunny.util.Constants;

/**
 * Created by Lynk on 14-6-24.
 */
public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();

    private CameraHelper cameraHelper;

    public Level level;
    public int lives;
    public int score;

    public CameraHelper getCameraHelper() {
        return cameraHelper;
    }

    public WorldController() {
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        lives = Constants.LIVES_START;
        initLevel();
    }

    private void initLevel () {
        score = 0;
        level = new Level(Constants.LEVEL_01);
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    @Override
    public boolean keyUp(int keyCode) {
        if(keyCode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }
        return false;
    }



    private void handleDebugInput(float deltaTime) {
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }
        //Camera move
        float cameraMoveSpeed = 2 * deltaTime;
        float cameraMoveSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            cameraMoveSpeed *= cameraMoveSpeedAccelerationFactor;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            moveCamera(-cameraMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            moveCamera(cameraMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            moveCamera(0, cameraMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            moveCamera(0, -cameraMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) {
            cameraHelper.setPosition(0, 0);
        }
        //Camera zoom
        float cameraZoomSpeed = 1 * deltaTime;
        float cameraZoomSpeedAccelerationFactor = 5;
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            cameraZoomSpeed *= cameraZoomSpeedAccelerationFactor;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            cameraHelper.addZoom(cameraZoomSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.EQUALS)) {
            cameraHelper.addZoom(-cameraZoomSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            cameraHelper.setZoom(1);
        }
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }
}