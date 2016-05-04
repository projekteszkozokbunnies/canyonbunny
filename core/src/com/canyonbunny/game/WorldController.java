package com.canyonbunny.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.canyonbunny.game.screen.MenuScreen;
import com.canyonbunny.game.util.*;

/**
 * A fő kontroller osztály, inicilaizálja a játékot, kezeli az inputot és az ütközési eseményeket.
 * Nyilvántartja a pontok és az életek számát.
 */
public class WorldController extends InputAdapter {
    private static final String TAG = WorldController.class.getName();

    private Game game;

    public CameraHelper cameraHelper;

    public int lives;
    public int score;
    private float timeLeftGameOverDelay;

    public WorldController (Game game) {
        this.game = game;
        init();
    }

    /**
     * Inicializáló metódus, beállítja a kezdeti változóértékeket, meghívja a pálya inicializálást.
     */
    private void init () {
        //libgdx needs to be told where its going to receive input events
        Gdx.input.setInputProcessor(this);
        lives = com.canyonbunny.game.util.Constants.LIVES_START;
        timeLeftGameOverDelay = 0;
        initLevel();
    }

    /**
     * A szintet inicializáló metódus, kinullázza a ponzokat is.
     */
    private void initLevel () {
        score = 0;
    }

    /**
     * Minden frissítéskor meghívódó metódus, ha vége a játéknak, kilép a menübe. Meghívja az inputkezelést
     * @param deltaTime A frissítések között eltelt idő
     */
    public void update (float deltaTime) {
        handleDebugInput(deltaTime);

        if (isGameOver()) {
            timeLeftGameOverDelay -= deltaTime;
            if (timeLeftGameOverDelay < 0) backToMenu();
        } else {
            //handleInputGame(deltaTime);
        }
    }

    //input
    //input handling method 1 - query every frame

    /**
     * Debug bemenetet kezelő metódus.
     * @param deltaTime Frissítések között eltelt idő az update() metódusból
     */
    private void handleDebugInput (float deltaTime) {
        if (Gdx.app.getType() != ApplicationType.Desktop) return;
    }

    /**
     * A kamerát elmozdító metódus.
     * @param x Kamera x koordináta
     * @param y Kamera y koordináta
     */
    private void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    //input handling method 2 - this is a "callback"

    /**
     * Keyboard bemenetet észlelő metódus (billentyű felengedése).
     * @param keycode A felengedett billentyű kódja
     * @return Ha nem tudja értelmezni a felengedett billentyűt, akkor false
     */
    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Keys.R) {
            init();
            Gdx.app.debug(TAG, "CanyonBunnyGame world reset");
        }

        // Back to Menu
        else if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
            backToMenu();
        }
        return false;
    }

    // Collision
    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    // CanyonBunnyGame over

    /**
     * Játék vége, ha az életek száma <= 0.
     * @return Vége van-e a játéknak?
     */
    public boolean isGameOver () {
        return lives <= 0;
    }


    // Menu

    /**
     * Visszalép a főmenübe.
     */
    private void backToMenu () {
        // switch to menu screen
        game.setScreen(new MenuScreen(game));
    }
}
