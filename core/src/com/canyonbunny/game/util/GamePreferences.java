package com.canyonbunny.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;


/**
 * A beállítások mentésére és betöltésére használt osztály.
 */
public class GamePreferences {
    public static final String TAG = GamePreferences.class.getName();

    public static final GamePreferences instance = new GamePreferences();

    public int charSkin;
    public boolean showFpsCounter;

    private Preferences prefs;



    // singleton: prevent instantiation from other classes
	/**
	* Konstruktor.
	*/
    private GamePreferences () {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }


	/**
	* Konfigurációs paraméterek betöltése.
	*/
    public void load () {
        charSkin = MathUtils.clamp(prefs.getInteger("charSkin", 0), 0, 2);
        showFpsCounter = prefs.getBoolean("showFpsCounter", false);
    }



	/**
	* Konfigurációs paraméterek mentése. 
	*/
    public void save () {
        prefs.putInteger("charSkin", charSkin);
        prefs.putBoolean("showFpsCounter", showFpsCounter);
        prefs.flush();
    }
}
