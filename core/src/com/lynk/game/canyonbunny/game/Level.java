package com.lynk.game.canyonbunny.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.lynk.game.canyonbunny.game.objects.Clouds;
import com.lynk.game.canyonbunny.game.objects.Mountains;
import com.lynk.game.canyonbunny.game.objects.Rock;
import com.lynk.game.canyonbunny.game.objects.WaterOverlay;

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

    }

    public void render(SpriteBatch batch) {

    }
}
