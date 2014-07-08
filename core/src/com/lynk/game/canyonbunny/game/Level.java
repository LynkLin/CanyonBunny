package com.lynk.game.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.lynk.game.canyonbunny.game.objects.*;

import java.awt.*;

/**
 * Created by Lynk on 14-6-30.
 */
public class Level {
    public static final String TAG = Level.class.getName();

    public enum  BLOCK_TYPE {
        EMPTY(0, 0, 0),//black
        ROCK(0, 255, 0),//green
        PLAYER_SPAWN_POINT(255, 255, 255),//white
        ITEM_FEATHER(255, 0, 255),//purple
        ITEM_GOLD_COIN(255, 255, 0);//yellow


        private int color;

        private BLOCK_TYPE(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor(int color) {
            return this.color == color;
        }

        public int getColor() {
            return color;
        }
    }

    //Objects
    public Array<Rock> rocks;

    //decoration
    public Clouds clouds;
    public Mountains mountains;
    public WaterOverlay waterOverlay;

    public Level(String fileName) {
        init(fileName);
    }

    private void init(String fileName) {
        rocks = new Array<Rock>();

        //Load image file that represents the level data
        Pixmap pixmap = new Pixmap(Gdx.files.internal(fileName));
        //Scan pixels form top-left to bottom-right
        int lastPixel = -1;
        for(int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for(int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
                AbstractGameObject obj = null;
                float offsetHeight = 0;
                //Height grows from bottom to top
                float baseHeight = pixmap.getHeight() - pixelY;
                //Get color of current pixel as 32 bit RGBA value
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                //Find matching color value to identify block type at (x, y)
                //point and create the corresponding game object if there is a match
                //Empty space
                if(BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
                    //do nothing
                }
                //Rock
                else if(BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
                    if(lastPixel != currentPixel) {
                        obj = new Rock();
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
                        rocks.add((Rock) obj);
                    }
                }
                //Player spawn point
                else if(BLOCK_TYPE.PLAYER_SPAWN_POINT.sameColor(currentPixel)) {

                }
                //Feather
                else if(BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)) {

                }
                //gold coin
                else if(BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)) {

                }
                //Unknown
                else {
                    int r = 0xff & (currentPixel >>> 24); //Red channel
                    int g = 0xff & (currentPixel >>> 16); //Green channel
                    int b = 0xff & (currentPixel >>> 8); //Blue channel
                    int a = 0xff & currentPixel; //Alpha channel

                    Gdx.app.error(TAG, "Unknown object at x<" + pixelX
                            + "> y>" + pixelY
                    + ">: r<" + r
                    + "> g<" + g
                    + "> b<" + b
                    + "> a<" + a + ">");
                }
                lastPixel = currentPixel;
            }
        }

        //Decoration
        clouds = new Clouds(pixmap.getWidth());
        clouds.position.set(0, 2);
        mountains = new Mountains(pixmap.getWidth());
        mountains.position.set(-1, -1);
        waterOverlay = new WaterOverlay(pixmap.getWidth());
        waterOverlay.position.set(0, -3.75f);

        //Free memory
        pixmap.dispose();
        Gdx.app.debug(TAG, "level '" + fileName + "' loaded!");
    }

    public void render(SpriteBatch batch) {
        //Draw mountains
        mountains.render(batch);
        for(Rock rock: rocks) {
            rock.render(batch);
        }
        waterOverlay.render(batch);
        clouds.render(batch);
    }
}
