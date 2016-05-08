package com.canyonbunny.game.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.canyonbunny.game.Assets;

/**
 * Felhők főosztály, mely tartalmaz egy Felhő alosztályt.
 */
public class Clouds extends AbstractGameObject {
    private float length;
    private Array<TextureRegion> regClouds;
    private Array<Cloud> clouds;

    /**
     * Felhő alosztály.
     */
    private class Cloud extends AbstractGameObject {
        private TextureRegion regCloud;

        public Cloud () {}

        /**
         * Textúra elhelyezésére szolgáló metódus.
         * @param region Méret és koordináta adatokat tárol.
         */
        public void setRegion (TextureRegion region) {
            regCloud = region;
        }

        /**
         * Renderelő metódus, mely kirajzolja a felhőt.
         * @param batch Kirajzolandó objektumok kötege
         */
        @Override
        public void render (SpriteBatch batch) {
            TextureRegion reg = regCloud;
            batch.draw(reg.getTexture(), position.x + origin.x,
                    position.y + origin.y, origin.x, origin.y, dimension.x,
                    dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
                    reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
        }
    }

    /**
     * Konstruktor, mely beállítja a felhők méretét.
     * @param length Méret.
     */
    public Clouds (float length) {
        this.length = length;
        init();
    }

    /**
     * Inicializáló metódus, mely beállítja a kezdeti értékeket.
     */
    private void init () {
        dimension.set(3.0f, 1.5f);
        regClouds = new Array<TextureRegion>();
        regClouds.add(Assets.instance.levelDecoration.cloud01);
        regClouds.add(Assets.instance.levelDecoration.cloud02);
        regClouds.add(Assets.instance.levelDecoration.cloud03);
        int distFac = 5;
        int numClouds = (int)(length / distFac);
        clouds = new Array<Cloud>(2 * numClouds);
        for (int i = 0; i < numClouds; i++) {
            Cloud cloud = spawnCloud();
            cloud.position.x = i * distFac;
            clouds.add(cloud);
        }
    }

    /**
     * Felhő random pozícióban való elhelyezésére szolgáló függvény.
     * @return Felhő objektum.
     */
    private Cloud spawnCloud () {
        Cloud cloud = new Cloud();
        cloud.dimension.set(dimension);
        // select random cloud image
        cloud.setRegion(regClouds.random());
        // position
        Vector2 pos = new Vector2();
        pos.x = length + 10; // position after end of level
        pos.y += 1.75; // base position
        pos.y += MathUtils.random(0.0f, 0.2f) * (MathUtils.randomBoolean() ? 1 : -1); // random additional position
        cloud.position.set(pos);
        return cloud;
    }

    /**
     * Renderelő metódus, mely kirajzolja az összes felhőt.
     * @param batch Kirajzolandó objektumok kötege
     */
    @Override
    public void render (SpriteBatch batch) {
        for (Cloud cloud : clouds)
            cloud.render(batch);
    }
}