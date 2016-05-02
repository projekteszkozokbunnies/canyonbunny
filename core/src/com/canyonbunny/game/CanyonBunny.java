package com.canyonbunny.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.canyonbunny.game.screen.MenuScreen;

/**
 * A főosztály, ahonnan a program indul, minden platformon.
 */
public class CanyonBunny extends Game {

	/**
	 *Beállítja a log szintjét.
	 *<ul>
	 *<li>Application.LOG_NONE: mutes all logging.</li>
	 *<li>Application.LOG_DEBUG: logs all messages.</li>
	 *<li>Application.LOG_ERROR: logs only error messages.</li>
	 *<li>Application.LOG_INFO: logs error and normal messages.</li>
	 *</ul>
	 *Beállítja az első Screent: Menu.
	 */
	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new MenuScreen(this));
	}

}