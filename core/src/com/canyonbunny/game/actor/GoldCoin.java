package com.canyonbunny.game.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.canyonbunny.game.Assets;
import com.canyonbunny.game.gameobject.AbstractGameObject;


/**
 * Érme osztálya.
 */
public class GoldCoin extends AbstractGameObject {
    private TextureRegion regGoldCoin;
    public boolean collected;

    private static int score = 100;


	/**
    * Konstruktor, ami meghívja az inicializálást végző metódust.
    */
    public GoldCoin () {
        init();
    }

	/**
    * Inicializáló metódus beállítja a kezdeti értékeket.
    */
    private void init () {
        dimension.set(0.5f, 0.5f);
        regGoldCoin = Assets.instance.goldCoin.goldCoin;
        // Set bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

	/**
     * Renderelő metódus, ami kirajzolja az érmét.
     * @param batch Kirajzolandó elemek kötege.
     */
    public void render (SpriteBatch batch) {
        if (collected) return;

        TextureRegion reg = null;
        reg = regGoldCoin;
        batch.draw(reg.getTexture(), position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }

	/**
     * Egy érme értékét adja vissza.
     * @return Az érme értéke
     */
    public static int getScore() {
        return score;
    }
}
