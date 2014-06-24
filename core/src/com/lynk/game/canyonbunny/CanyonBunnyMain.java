package com.lynk.game.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lynk.game.canyonbunny.game.WorldController;
import com.lynk.game.canyonbunny.game.WorldRenderer;

public class CanyonBunnyMain extends ApplicationAdapter {
//	SpriteBatch batch;
//	Texture img;

    private static final String TAG = CanyonBunnyMain.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;
	
	@Override
	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");

        //Set log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Initalize controller and renderer
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);
	}

	@Override
	public void render () {
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();

        //Update game world by the time that has passed
        //since last rendered frame.
        worldController.update(Gdx.graphics.getDeltaTime());

        //Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        //Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Render game world to screen
        worldRenderer.render();
	}

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}