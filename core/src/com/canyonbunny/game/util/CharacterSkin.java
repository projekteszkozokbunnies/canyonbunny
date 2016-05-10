package com.canyonbunny.game.util;

import com.badlogic.gdx.graphics.Color;

/**
 * Karakter kinézet kezelő osztály.
 */
public enum CharacterSkin {
	
	
    WHITE("White", 1.0f, 1.0f, 1.0f),
    GRAY("Gray", 0.7f, 0.7f, 0.7f),
    BROWN("Brown", 0.7f, 0.5f, 0.3f);

    private String name;
    private Color color = new Color();


	/**
	* Konstruktor beállítja az RGB színt.
     * @param name Név.
     * @param r Piros szín.
     * @param g Zöld szín.
     * @param b Kék szín
     */
    private CharacterSkin (String name, float r, float g, float b) {
        this.name = name;
        color.set(r, g, b, 1.0f);
    }

	/**
	* Név visszaadása.
	*/
    @Override
    public String toString () {
        return name;
    }

	/**
	* Szín objektum lekérdezése. 
	*/
    public Color getColor () {
        return color;
    }
}
