package org.mockbukkit.mockbukkit.potion;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
