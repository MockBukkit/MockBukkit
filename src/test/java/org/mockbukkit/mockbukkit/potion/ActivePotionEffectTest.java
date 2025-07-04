package org.mockbukkit.mockbukkit.potion;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ActivePotionEffectTest
{

	ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void infiniteDuration()
	{
		PlayerMock player = server.addPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, PotionEffect.INFINITE_DURATION, 0));
		server.getScheduler().performOneTick();
		assertFalse(player.getActivePotionEffects().isEmpty());
	}

	@Test
	void belowFiniteDuration()
	{
		int duration = 3;

		PlayerMock player = server.addPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 0));
		server.getScheduler().performTicks(duration - 1);

		assertFalse(player.getActivePotionEffects().isEmpty());
	}

	@Test
	void aboveFiniteDuration()
	{
		int duration = 3;

		PlayerMock player = server.addPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, 0));
		server.getScheduler().performTicks(duration + 1);

		assertTrue(player.getActivePotionEffects().isEmpty());
	}

	@Test
	void testEffectDecreasesOnTick() {
		PlayerMock player = server.addPlayer();
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 3, 0));
		assertEquals(3, ((PotionEffect)(player.getActivePotionEffects().toArray()[0])).getDuration());

		// 1 tick.
		server.getScheduler().performTicks(1);

		// verify it's OK
		var effect = player.getPotionEffect(PotionEffectType.SPEED);
		assertEquals(2, ((PotionEffect)(player.getActivePotionEffects().toArray()[0])).getDuration());
		assertEquals(2, effect.getDuration());
		player.removePotionEffect(PotionEffectType.SPEED);

		assertFalse(player.hasPotionEffect(PotionEffectType.SPEED));

		// 2 ticks..
		server.getScheduler().performTicks(1);

		assertEquals(2, effect.getDuration(), "Effect should have stayed the same");
		player.addPotionEffect(effect);
		assertTrue(player.hasPotionEffect(PotionEffectType.SPEED));

		// 3 ticks (but skipped 1)
		server.getScheduler().performTicks(1);
		assertEquals(1, ((PotionEffect)(player.getActivePotionEffects().toArray()[0])).getDuration());

		// expired on tick 4
		server.getScheduler().performTicks(1);
		assertFalse(player.hasPotionEffect(PotionEffectType.SPEED));
	}

}
