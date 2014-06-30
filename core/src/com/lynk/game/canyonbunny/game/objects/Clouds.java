package com.lynk.game.canyonbunny.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lynk.game.canyonbunny.game.Assets;

/**
 * Created by Lynk on 14-6-30.
 */
public class Clouds extends AbstractGameObject {
    private Array<TextureRegion> regClouds;
    private Array<Cloud> clouds;
    private float length;

    public Clouds(float length) {
        this.length = length;
        init();
    }

    private void init() {
        dimension.set(3f, 1.5f);
        regClouds = new Array<TextureRegion>();
        regClouds.add(Assets.instance.levelDecoration.cloud01);
        regClouds.add(Assets.instance.levelDecoration.cloud02);
        regClouds.add(Assets.instance.levelDecoration.cloud03);
        int distFac = 5;
        int numClouds = (int) (length / distFac);
        clouds = new Array<Cloud>(2 * numClouds);
        for(int i = 0; i < numClouds; i++) {
            Cloud cloud = spawnCloud();
            cloud.position.x = i * distFac;
            clouds.add(cloud);
        }
    }

    private Cloud spawnCloud() {
        Cloud cloud = new Cloud();
        cloud.dimension.set(dimension);
        //select random cloud image
        cloud.setRegCloud(regClouds.random());
        //position
        Vector2 pos = new Vector2();
        //position after end of level
        pos.x = length + 10;
        //base position
        pos.y += 1.75;
        //random additional position
        pos.y += MathUtils.random(0f, 0.3f) * (MathUtils.randomBoolean() ? 1: -1);
        cloud.position.set(pos);
        return cloud;
    }

    @Override
    public void render(SpriteBatch batch) {
        for(Cloud cloud: clouds) {
            cloud.render(batch);
        }
    }

    private class Cloud extends AbstractGameObject {
        private TextureRegion regCloud;

        private Cloud() {
        }

        public void setRegCloud(TextureRegion regCloud) {
            this.regCloud = regCloud;
        }

        @Override
        public void render(SpriteBatch batch) {
            TextureRegion reg = regCloud;
            batch.draw(reg.getTexture(),
                    position.x + origin.x, position.y + origin.y,
                    origin.x, origin.y,
                    dimension.x, dimension.y,
                    scale.x, scale.y,
                    rotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
        }
    }
}
