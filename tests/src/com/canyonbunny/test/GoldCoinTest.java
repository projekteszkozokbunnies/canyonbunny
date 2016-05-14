package com.canyonbunny.test;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.canyonbunny.game.Assets;
import com.canyonbunny.game.CanyonBunny;
import com.canyonbunny.game.actor.GoldCoin;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class GoldCoinTest {

	@Before
	public void setUp() {
		HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

		HeadlessApplication app = new HeadlessApplication(new CanyonBunny(), config);
		Gdx.gl = mock(GL20.class);
		Gdx.gl20 = mock(GL20.class);
		Gdx.gl30 = mock(GL30.class);
		Gdx.files = mock(Files.class);
		Gdx.graphics = mock(Graphics.class);
	}

	@Test
	public void isCoin100() {
		int expected = 100;
		int real = (new GoldCoin().getScore());
	}

}
