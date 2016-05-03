package com.canyonbunny.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private SpriteBatch batch;

    private WorldController worldController;

    public WorldRenderer (WorldController worldController) {
        this.worldController = worldController;
        init();
    }

    private OrthographicCamera cameraGUI;

    private void init () {
        batch = new SpriteBatch();

        camera = new OrthographicCamera(com.canyonbunny.game.util.Constants.VIEWPORT_WIDTH, com.canyonbunny.game.util.Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        camera.update();

        cameraGUI = new OrthographicCamera(com.canyonbunny.game.util.Constants.VIEWPORT_GUI_WIDTH, com.canyonbunny.game.util.Constants.VIEWPORT_GUI_HEIGHT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();
    }

    public void render () {
        renderWorld(batch);
        renderGui(batch);
    }

    private void renderWorld (SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.end();
    }

    private void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        // draw collected gold coins icon + text
        // (anchored to top left edge)
        renderGuiScore(batch);
        // draw extra lives icon + text (anchored to top right edge)
        renderGuiExtraLive(batch);
        // draw game over text
        renderGuiGameOverMessage(batch);
        batch.end();
    }

    private void renderGuiScore(SpriteBatch batch) {
        float x = -15;
        float y = -15;
        batch.draw(Assets.instance.goldCoin.goldCoin,
                x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
        Assets.instance.fonts.defaultBig.draw(batch,
                "" + worldController.score,
                x + 75, y + 37);
    }

    private void renderGuiExtraLive (SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 50 -
                com.canyonbunny.game.util.Constants.LIVES_START * 50;
        float y = -15;
        for (int i = 0; i < com.canyonbunny.game.util.Constants.LIVES_START; i++) {
            if (worldController.lives <= i)
                batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
            batch.draw(Assets.instance.bunny.head,
                    x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
            batch.setColor(1, 1, 1, 1);
        }
    }

    private void renderGuiFpsCounter (SpriteBatch batch) {
        float x = cameraGUI.viewportWidth - 55;
        float y = cameraGUI.viewportHeight - 15;
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
        if (fps >= 45) {
        // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
        // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
        // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }
        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }


    private void renderGuiGameOverMessage (SpriteBatch batch) {
        float x = cameraGUI.viewportWidth / 2;
        float y = cameraGUI.viewportHeight / 2;
        if (worldController.isGameOver()) {
            BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
            fontGameOver.setColor(1, 0.75f, 0.25f, 1);
            fontGameOver.draw(batch, "Game Over", x, y, 0, Align.center, false);
            fontGameOver.setColor(1, 1, 1, 1);
        }
    }

    public void resize (int width, int height) {
        camera.viewportWidth = (com.canyonbunny.game.util.Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();

        cameraGUI.viewportHeight = com.canyonbunny.game.util.Constants.VIEWPORT_GUI_HEIGHT;
        cameraGUI.viewportWidth = (com.canyonbunny.game.util.Constants.VIEWPORT_GUI_HEIGHT
                / (float)height) * (float)width;
        cameraGUI.position.set(cameraGUI.viewportWidth / 2,
                cameraGUI.viewportHeight / 2, 0);
        cameraGUI.update();
    }

    @Override public void dispose () {
        batch.dispose();
    }
}