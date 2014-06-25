package com.lynk.game.canyonbunny.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.lynk.game.canyonbunny.util.Constants;

/**
 * Created by Lynk on 14-6-24.
 */
public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    public void render() {
        renderTestObjects();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = Constants.VIEWPORT_HEIGHT / height * width;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void renderTestObjects() {
        worldController.getCameraHelper().applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for(Sprite sprite: worldController.getTestSprites()) {
            sprite.draw(batch);
        }
        batch.end();
    }
}
