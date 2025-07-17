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
	void testEffectDecreasesOnTick()
	{
		PlayerMock player = server.addPlayer();
		var speedFor3S = new PotionEffect(PotionEffectType.SPEED, 3, 0);
		player.addPotionEffect(speedFor3S);
		assertEquals(3, player.getActivePotionEffects().iterator().next().getDuration());

		// 1 tick.
		server.getScheduler().performTicks(1);

		// verify it's OK
		assertEquals(3, speedFor3S.getDuration()); // The original shouldn't be modified

		var effect = player.getPotionEffect(PotionEffectType.SPEED);  // This has 2s left
		assertEquals(2, effect.getDuration());

		assertEquals(2, player.getActivePotionEffects().iterator().next().getDuration()); // And this one too
		player.removePotionEffect(PotionEffectType.SPEED);

		assertTrue(player.getActivePotionEffects().isEmpty());

		// 2 ticks..
		server.getScheduler().performTicks(1);

		assertEquals(2, effect.getDuration(), "Effect's duration should have stayed the same");
		player.addPotionEffect(effect);
		assertTrue(player.hasPotionEffect(PotionEffectType.SPEED));

		// 3 ticks (but skipped 1)
		server.getScheduler().performTicks(1);
		assertEquals(1, player.getActivePotionEffects().iterator().next().getDuration());

		// expired on tick 4
		server.getScheduler().performTicks(1);
		assertFalse(player.hasPotionEffect(PotionEffectType.SPEED));
	}

}
