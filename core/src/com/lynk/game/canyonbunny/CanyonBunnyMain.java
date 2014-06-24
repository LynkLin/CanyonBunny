package com.lynk.game.canyonbunny;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.lynk.game.canyonbunny.game.WorldController;
import com.lynk.game.canyonbunny.game.WorldRenderer;

public class CanyonBunnyMain extends ApplicationAdapter {
//	SpriteBatch batch;
//	Texture img;

    private static final String TAG = CanyonBunnyMain.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    //pause game
    private boolean paused;

	@Override
	public void create () {
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");

        //Set log level
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        //Initalize controller and renderer
        worldController = new WorldController();
        worldRenderer = new WorldRenderer(worldController);

        //Game world is active on start
        paused = false;
	}

	@Override
	public void render () {
        //Update when not paused
        if(!paused) {
            //Update game world by the time that has passed
            //since last rendered frame.
            worldController.update(Gdx.graphics.getDeltaTime());
        }

        //Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        //Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Render game world to screen
        worldRenderer.render();
	}

    @Override
    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void dispose() {
       worldRenderer.dispose();
    }
}