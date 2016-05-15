package com.canyonbunny.test;

import com.canyonbunny.game.actor.Feather;
import com.canyonbunny.game.actor.GoldCoin;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ActorTest {

	@Test
	public void isCoin100() {
		int expected = 100;
		int real = GoldCoin.getScore();
		assertEquals(expected, real);
	}

	@Test
	public void isFeather250() {
		int expected = 250;
		int real = Feather.getScore();
		assertEquals(expected, real);
	}

}
