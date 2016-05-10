package com.canyonbunny.game.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.canyonbunny.game.Assets;
import com.canyonbunny.game.gameobject.AbstractGameObject;

/**
 * Toll osztálya.
 */
public class Feather extends AbstractGameObject {
    private TextureRegion regFeather;
    public boolean collected;

	/**
    * Konstruktor, ami meghívja az inicializálást végző metódust.
    */
    public Feather () {
        init();
    }



	/**
    * Inicializáló metódus beállítja a kezdeti értékeket.
    */
    private void init () {
        dimension.set(0.5f, 0.5f);
        regFeather = Assets.instance.feather.feather;
        // Set bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        collected = false;
    }

	/**
     * Renderelő metódus lesz ami kirajzolja az tollat.
     * @param batch Kirajzolandó elemek kötege.
     */
    public void render (SpriteBatch batch) {
        if (collected) return;
        TextureRegion reg = null;
        reg = regFeather;
        batch.draw(reg.getTexture(), position.x, position.y,
                origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
                rotation, reg.getRegionX(), reg.getRegionY(),
                reg.getRegionWidth(), reg.getRegionHeight(), false, false);
    }
	
	/**
     * Metódus ami visszaadja a toll értékét. 
     */
    public int getScore() {
        return 250;
    }
}
