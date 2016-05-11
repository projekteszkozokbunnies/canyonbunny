package com.canyonbunny.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.canyonbunny.game.actor.BunnyHead;
import com.canyonbunny.game.actor.Feather;
import com.canyonbunny.game.actor.GoldCoin;
import com.canyonbunny.game.gameobject.Level;
import com.canyonbunny.game.gameobject.Rock;
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

    public Level level;
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
        cameraHelper = new CameraHelper();
        lives = com.canyonbunny.game.util.Constants.LIVES_START;
        timeLeftGameOverDelay = 0;
        initLevel();
    }

    /**
     * A szintet inicializáló metódus, kinullázza a ponzokat is.
     */
    private void initLevel () {
        score = 0;
        level = new Level(com.canyonbunny.game.util.Constants.LEVEL_01);
        cameraHelper.setTarget(level.bunnyHead);
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
            handleInputGame(deltaTime);
        }

        level.update(deltaTime);
        testCollisions();
        cameraHelper.update(deltaTime);

        if (!isGameOver() && isPlayerInWater()) {
            lives--;
            if (isGameOver())
                timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
            else
                initLevel();
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

        if (!cameraHelper.hasTarget(level.bunnyHead)) {
            float camMoveSpeed = 5 * deltaTime;
            float camMoveSpeedAccelerationFactor = 5;
            if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
                    camMoveSpeedAccelerationFactor;
            if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
                    0);
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
                    0);
            if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
            if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
                    -camMoveSpeed);
            if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
                cameraHelper.setPosition(0, 0);
        }
        float camZoomSpeed = 1 * deltaTime;
        float camZoomSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *=
                camZoomSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.COMMA))
            cameraHelper.addZoom(camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(
                -camZoomSpeed);
        if (Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
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
        // Toggle camera follow
        else if (keycode == Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget()
                    ? null: level.bunnyHead);
            Gdx.app.debug(TAG, "Camera follow enabled: "
                    + cameraHelper.hasTarget());
        }
        // Back to Menu
        else if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
            backToMenu();
        }
        return false;
    }

    private void handleInputGame (float deltaTime) {
        if (cameraHelper.hasTarget(level.bunnyHead)) {
            // Player Movement
            if (Gdx.input.isKeyPressed(Keys.LEFT)) {
                level.bunnyHead.velocity.x = -level.bunnyHead.terminalVelocity.x;
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
                level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
            } else {
                // Execute auto-forward movement on non-desktop platform
                if (Gdx.app.getType() != ApplicationType.Desktop) {
                    //level.bunnyHead.velocity.x = level.bunnyHead.terminalVelocity.x;
                }
            }
            // Bunny Jump
            if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
                level.bunnyHead.setJumping(true);
            } else {
                level.bunnyHead.setJumping(false);
            }
        }
    }

    // Collision
    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private void onCollisionBunnyHeadWithRock(Rock rock) {
        BunnyHead bunnyHead = level.bunnyHead;
        float heightDifference = Math.abs(bunnyHead.position.y
                - ( rock.position.y + rock.bounds.height));
        if (heightDifference > 0.25f) {
            boolean hitRightEdge = bunnyHead.position.x > (
                    rock.position.x + rock.bounds.width / 2.0f);
            if (hitRightEdge) {
                bunnyHead.position.x = rock.position.x + rock.bounds.width;
            } else {
                bunnyHead.position.x = rock.position.x -
                        bunnyHead.bounds.width;
            }
            return;
        }
        switch (bunnyHead.jumpState) {
            case GROUNDED:
                break;
            case FALLING:
            case JUMP_FALLING:
                bunnyHead.position.y = rock.position.y +
                        bunnyHead.bounds.height + bunnyHead.origin.y;
                bunnyHead.jumpState = BunnyHead.JUMP_STATE.GROUNDED;
                break;
            case JUMP_RISING:
                bunnyHead.position.y = rock.position.y +
                        bunnyHead.bounds.height + bunnyHead.origin.y;
                break;
        }
    }

    private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin) {
        goldcoin.collected = true;
        score += goldcoin.getScore();
        Gdx.app.log(TAG, "Gold coin collected");
    }

    private void onCollisionBunnyWithFeather(Feather feather) {
        feather.collected = true;
        score += feather.getScore();
        level.bunnyHead.setFeatherPowerup(true);
        Gdx.app.log(TAG, "Feather collected");
    }

    private void testCollisions () {
        r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y,
                level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
        // Test collision: Bunny Head <-> Rocks
        for (Rock rock : level.rocks) {
            r2.set(rock.position.x, rock.position.y, rock.bounds.width,
                    rock.bounds.height);
            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyHeadWithRock(rock);
            // IMPORTANT: must do all collisions for valid
            // edge testing on rocks.
        }
        // Test collision: Bunny Head <-> Gold Coins
        for (GoldCoin goldcoin : level.goldcoins) {
            if (goldcoin.collected) continue;
            r2.set(goldcoin.position.x, goldcoin.position.y,
                    goldcoin.bounds.width, goldcoin.bounds.height);
            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyWithGoldCoin(goldcoin);
            break;
        }
        // Test collision: Bunny Head <-> Feathers
        for (Feather feather : level.feathers) {
            if (feather.collected) continue;
            r2.set(feather.position.x, feather.position.y,
                    feather.bounds.width, feather.bounds.height);
            if (!r1.overlaps(r2)) continue;
            onCollisionBunnyWithFeather(feather);
            break;
        }
    }

    // CanyonBunnyGame over

    /**
     * Játék vége, ha az életek száma <= 0.
     * @return Vége van-e a játéknak?
     */
    public boolean isGameOver () {
        return lives <= 0;
    }

    public boolean isPlayerInWater () {
        return level.bunnyHead.position.y < -5;
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
