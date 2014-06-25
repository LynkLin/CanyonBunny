package com.lynk.game.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.lynk.game.canyonbunny.util.CameraHelper;
import javafx.scene.input.KeyCode;

/**
 * Created by Lynk on 14-6-24.
 */
public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();

    private Sprite[] testSprites;

    private int selectedSprite;

    private CameraHelper cameraHelper;

    public Sprite[] getTestSprites() {
        return testSprites;
    }

    public CameraHelper getCameraHelper() {
        return cameraHelper;
    }

    public WorldController() {
        init();
    }

    private void init() {
        Gdx.input.setInputProcessor(this);
        cameraHelper = new CameraHelper();
        initTestObjects();
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        updateTestObjects(deltaTime);
        cameraHelper.update(deltaTime);
    }

    @Override
    public boolean keyUp(int keyCode) {
        if(keyCode == Input.Keys.R) {
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        } else if(keyCode == Input.Keys.SPACE) {
            selectedSprite = (selectedSprite + 1) % testSprites.length;
            //Update camera's target to follow the currently selected sprite
            if(cameraHelper.hasTarget()) {
                cameraHelper.setTarget(testSprites[selectedSprite]);
            }
            Gdx.app.debug(TAG, "Sprite #" + selectedSprite + " selected");
        } else if(keyCode == Input.Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget()? null: testSprites[selectedSprite]);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        return false;
    }

    private void initTestObjects() {
        //Create 5 sprites
        testSprites = new Sprite[5];
        //Create empty POT-sized Pixmap with 8 bit RGBA pixel data
        int width = 32;
        int height = 32;
        Pixmap pixmap = createProceduralPixmap(width, height);
        //Create texture from pixmap data
        Texture texture = new Texture(pixmap);
        //Create new sprites
        for(int i = 0; i < testSprites.length; i++) {
            Sprite spriteTmp = new Sprite(texture);
            //Set sprite size: 1m * 1m in game world
            spriteTmp.setSize(1, 1);
            //Set origin to sprite's center
            spriteTmp.setOrigin(spriteTmp.getWidth() / 2.0f, spriteTmp.getHeight() / 2.0f);
            //Calculate random position for sprite
            //Let the rectangle in the screen
            float randomX = MathUtils.random(-2f + spriteTmp.getOriginX() / MathUtils.sin(45f), 2f - spriteTmp.getOriginX() / MathUtils.sin(45f));
            float randomY = MathUtils.random(-2f + spriteTmp.getOriginY() / MathUtils.sin(45f), 2f - spriteTmp.getOriginY() / MathUtils.sin(45f));
//            randomX = i;
//            randomY = 0;
            spriteTmp.setPosition(randomX, randomY);
            //Put into array
            testSprites[i] = spriteTmp;
        }
        selectedSprite = 0;
    }

    private Pixmap createProceduralPixmap(int width, int height) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        //Fill red color with 50% opacity
        pixmap.setColor(1, 0, 0, 0.5f);
        pixmap.fill();
        //Draw a yellow-colored X shape on square
        pixmap.setColor(1, 1, 0, 1);
        pixmap.drawLine(0, 0, width, height);
        pixmap.drawLine(width, 0, 0, height);
        //Draw a cyan-colored border
        pixmap.setColor(0, 1, 1, 1);
        pixmap.drawRectangle(0, 0, width, height);
        return pixmap;
    }

    private void updateTestObjects(float deltaTime) {
        //Get current rotation from selected sprite
        float rotation = testSprites[selectedSprite].getRotation();
        //Rotate sprite by 90 degrees per second
        rotation += 90 * deltaTime;
        //Wrap around at 360 degrees
        rotation %= 360;
        //Set new rotation value
        testSprites[selectedSprite].setRotation(rotation);
    }

    private void handleDebugInput(float deltaTime) {
        if(Gdx.app.getType() != Application.ApplicationType.Desktop) {
            return;
        }
        float spriteMoveSpeed = 2 * deltaTime;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveSelectedSprite(-spriteMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveSelectedSprite(spriteMoveSpeed, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveSelectedSprite(0, spriteMoveSpeed);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveSelectedSprite(0, -spriteMoveSpeed);
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

    private void moveSelectedSprite(float x, float y) {
        testSprites[selectedSprite].translate(x, y);
        Gdx.app.debug(TAG, "Point: (" + testSprites[selectedSprite].getX() + ", " + testSprites[selectedSprite].getY() + ")");
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }
}