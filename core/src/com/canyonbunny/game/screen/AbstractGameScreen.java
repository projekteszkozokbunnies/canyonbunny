package com.canyonbunny.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.canyonbunny.game.Assets;

/**
 * Ez az abstract osztály, amiből az összes többi Screen származik.
 */
public abstract class AbstractGameScreen implements Screen {
    protected Game game;

    public AbstractGameScreen (Game game) {
        this.game = game;
    }

    /**
     * A rajzoló metódous
     * @param deltaTime Az előző rajzolás óta eltelt idő.
     */
    public abstract void render (float deltaTime);

    /**
     * Az ablak átméretezésekor meghívódó metódus.
     * @param width Az új, átméretezés utáni szélesség
     * @param height Az új, átméretezés utáni magasság
     */
    public abstract void resize (int width, int height);

    /**
     * A screen aktiválódásakor meghívódó metódus.
     */
    public abstract void show ();

    /**
     * A screen deaktiválódásakor meghívódó metódus.
     */
    public abstract void hide ();

    /**
     * Általában akkor hívódok meg, ha az alkalmazás nem látszik a képernyőn, vagy nem aktív.
     */
    public abstract void pause ();

    /**
     * Általában akkor hívódok meg, ha az alkalmazás újra fókuszba kerül.
     */
    @Override
    public void resume () {
        Assets.instance.init(new AssetManager());
    }

    /**
     * A screen lejártakor elengedi ez az erőforrásokat.
     */
    @Override
    public void dispose () {
        Assets.instance.dispose();
    }
}
