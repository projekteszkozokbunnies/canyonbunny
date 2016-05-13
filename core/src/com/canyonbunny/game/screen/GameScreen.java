package com.canyonbunny.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.canyonbunny.game.WorldController;
import com.canyonbunny.game.WorldRenderer;
import com.canyonbunny.game.util.GamePreferences;

/**
 * Ebben a screenben történik a játékmenet.
 */
public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();

    private WorldController worldController;
    private WorldRenderer worldRenderer;

    /**
     * Ezzel a változóval jelezzük, hogy valamilyen esemény történt, például telefonhívás, ami miatt szünetelni kell.
     */
    private boolean paused;

    public GameScreen (Game game) {
        super(game);
    }

    /**
     * A játékciklusban minden iterációkor meghívódó metódus: frissül a logika és kirajzolódnak a játékelemek.
     */
    @Override
    public void render (float deltaTime) {
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            deltaTime = Math.min(deltaTime, 1.0f / 30.0f);
            worldController.update(deltaTime);
        }

        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
    }

    @Override
    public void resize (int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void show () {
        GamePreferences.instance.load();
        worldController = new WorldController(game);
        worldRenderer = new WorldRenderer(worldController);
        // Enable android bac key
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide () {
        // Disable android back key
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause () {
        paused = true;
    }

    @Override
    public void resume () {
        super.resume();
        paused = false;
    }
}