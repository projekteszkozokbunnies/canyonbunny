package com.canyonbunny.game.gameobject;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.canyonbunny.game.Assets;

/**
 * Víz osztálya.
 */
public class WaterOverlay extends AbstractGameObject {
    private TextureRegion regWaterOverlay;
    private float length;

    /**
     * Konstruktor, mely beállítja a víz méretét és meghívja az inicializálást végző metódust.
     * @param length Méret.
     */
    public WaterOverlay (float length) {
        this.length = length;
        init();
    }

    /**
     * Inicializáló metódus, mely beállítja a kezdeti értékeket.
     */
    private void init () {
        dimension.set(length * 10, 3);
        regWaterOverlay = Assets.instance.levelDecoration.waterOverlay;
        origin.x = -dimension.x / 2;
    }

    /**
     * Renderelő metódus, mely kirajzolja a vizet.
     * @param batch Kirajzolandó objektumok kötege.
     */
    @Override
    public void render (SpriteBatch batch) {
        TextureRegion reg = null;
        reg = regWaterOverlay;
        batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
                scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }
}