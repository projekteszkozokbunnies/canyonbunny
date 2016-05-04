package com.canyonbunny.game.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.canyonbunny.game.gameobject.AbstractGameObject;

/**
 * A kamera pozícionálását és a zoomolást végző segédosztály.
 */
public class CameraHelper {
    private static final String TAG = CameraHelper.class.getName();

    private final float MAX_ZOOM_IN = 0.25f;
    private final float MAX_ZOOM_OUT = 10.0f;

    private Vector2 position;
    private float zoom;
    private AbstractGameObject target;

    public CameraHelper () {
        position = new Vector2();
        zoom = 1.0f;
    }

    /**
     * Képkockánként mozgatja a kamerát.
     * @param deltaTime A frissítések között eltelt idő
     */
    public void update (float deltaTime) {
        if (!hasTarget()) return;

        position.x = target.position.x + target.origin.x;
        position.y = target.position.y + target.origin.y;
        // Prevent camera from moving down too far
        position.y = Math.max(-1f, position.y);
    }

    /**
     * Pozíció setter metódus.
     * @param x X koordináta
     * @param y Y koordináta
     */
    public void setPosition (float x, float y) {
        this.position.set(x, y);
    }

    /**
     * Pozíció getter metódus.
     * @return A pozíció
     */
    public Vector2 getPosition () { return position; }

    /**
     * Zoomolás növelése.
     * @param amount Mennyiség, amivel növelni kell a zoomot
     */
    public void addZoom (float amount) { setZoom(zoom + amount); }

    /**
     * Zoom setter metódus, lekorlátozva a min-max intervallumra.
     * @param zoom A beállítandó zoom érték
     */
    public void setZoom (float zoom) {
        this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
    }

    /**
     * Zoom getter metódus.
     * @return A zoom jelenlegi értéke
     */
    public float getZoom () { return zoom; }

    /**
     * Kamera célpont beállítása.
     * @param target A célpont objektum
     */
    public void setTarget (AbstractGameObject target) {
        this.target = target;
    }

    /**
     * Célpont getter metódus.
     * @return A célpont objektum
     */
    public AbstractGameObject getTarget () {
        return target;
    }

    /**
     * Van-e célpont ellenőrzés?
     * @return Van beállítva célpont?
     */
    public boolean hasTarget () { return target != null; }

    public boolean hasTarget (AbstractGameObject target) {
        return hasTarget() && this.target.equals(target);
    }

    /**
     * A beállítások alkalmazása egy kamerára.
     * @param camera A beállítandó kamera
     */
    public void applyTo (OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }
}