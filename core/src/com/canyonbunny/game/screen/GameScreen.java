package com.canyonbunny.game.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Ebben a screenben történik a játékmenet.
 */
public class GameScreen extends AbstractGameScreen {
    private static final String TAG = GameScreen.class.getName();

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
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void resize (int width, int height) {
    }

    @Override
    public void show () {
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void hide () {
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