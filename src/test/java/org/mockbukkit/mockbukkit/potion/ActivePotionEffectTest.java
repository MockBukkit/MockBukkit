package org.mockbukkit.mockbukkit.potion;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
public class ActivePotionEffectTest
{

	@MockBukkitInject
	private ServerMock server;

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

	@Test
	void testCompareTo_HigherAmplifierWins()
	{
		// Given
		PotionEffect effect1 = new PotionEffect(PotionEffectType.SPEED, 200, 1); // amplifier 1
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 200, 2); // amplifier 2

		ActivePotionEffect active1 = new ActivePotionEffect(effect1);
		ActivePotionEffect active2 = new ActivePotionEffect(effect2);

		// When & Then
		assertEquals(1, active1.compareTo(active2)); // effect2 has higher amplifier, so active1 < active2
		assertEquals(-1, active2.compareTo(active1)); // effect1 has lower amplifier, so active2 > active1
	}

	@Test
	void testCompareTo_SameAmplifier_InfiniteEffectWins()
	{
		// Given
		PotionEffect infiniteEffect = new PotionEffect(PotionEffectType.SPEED, -1, 1); // infinite
		PotionEffect finiteEffect = new PotionEffect(PotionEffectType.SPEED, 1000, 1); // finite

		ActivePotionEffect infiniteActive = new ActivePotionEffect(infiniteEffect);
		ActivePotionEffect finiteActive = new ActivePotionEffect(finiteEffect);

		// When & Then
		assertEquals(-1, infiniteActive.compareTo(finiteActive)); // infinite wins
		assertEquals(1, finiteActive.compareTo(infiniteActive)); // finite loses
	}

	@Test
	void testCompareTo_BothInfinite_ReturnsEqual()
	{
		// Given
		PotionEffect infiniteEffect1 = new PotionEffect(PotionEffectType.SPEED, -1, 1);
		PotionEffect infiniteEffect2 = new PotionEffect(PotionEffectType.SPEED, -1, 1);

		ActivePotionEffect infiniteActive1 = new ActivePotionEffect(infiniteEffect1);
		ActivePotionEffect infiniteActive2 = new ActivePotionEffect(infiniteEffect2);

		// When & Then
		assertEquals(0, infiniteActive1.compareTo(infiniteActive2));
		assertEquals(0, infiniteActive2.compareTo(infiniteActive1));
	}

	@Test
	void testCompareTo_SameAmplifier_FiniteEffects_LongerDurationWins()
	{
		PotionEffect shortEffect = new PotionEffect(PotionEffectType.SPEED, 150, 1); // duration 150
		PotionEffect longEffect = new PotionEffect(PotionEffectType.SPEED, 300, 1);  // duration 300

		ActivePotionEffect shortActive = new ActivePotionEffect(shortEffect);
		ActivePotionEffect longActive = new ActivePotionEffect(longEffect);

		// When & Then
		assertEquals(1, shortActive.compareTo(longActive)); // shorter duration loses
		assertEquals(-1, longActive.compareTo(shortActive)); // longer duration wins
	}

	@Test
	void testCompareTo_SameAmplifier_SameDuration_ReturnsEqual()
	{
		// Given
		PotionEffect effect1 = new PotionEffect(PotionEffectType.SPEED, 200, 1);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 200, 1);

		ActivePotionEffect active1 = new ActivePotionEffect(effect1);
		ActivePotionEffect active2 = new ActivePotionEffect(effect2);

		// When & Then
		assertEquals(0, active1.compareTo(active2));
		assertEquals(0, active2.compareTo(active1));
	}

	@Test
	void testCompareTo_AmplifierTakesPrecedenceOverDuration()
	{
		// Given
		PotionEffect lowAmpLongDuration = new PotionEffect(PotionEffectType.SPEED, 1000, 1);
		PotionEffect highAmpShortDuration = new PotionEffect(PotionEffectType.SPEED, 50, 2);

		ActivePotionEffect lowAmpActive = new ActivePotionEffect(lowAmpLongDuration);
		ActivePotionEffect highAmpActive = new ActivePotionEffect(highAmpShortDuration);

		// When & Then
		assertEquals(1, lowAmpActive.compareTo(highAmpActive)); // higher amplifier wins despite shorter duration
		assertEquals(-1, highAmpActive.compareTo(lowAmpActive));
	}

	@Test
	void testCompareTo_AmplifierTakesPrecedenceOverInfinite()
	{
		// Given
		PotionEffect lowAmpInfinite = new PotionEffect(PotionEffectType.SPEED, -1, 1);
		PotionEffect highAmpFinite = new PotionEffect(PotionEffectType.SPEED, 100, 2);

		ActivePotionEffect lowAmpActive = new ActivePotionEffect(lowAmpInfinite);
		ActivePotionEffect highAmpActive = new ActivePotionEffect(highAmpFinite);

		// When & Then
		assertEquals(1, lowAmpActive.compareTo(highAmpActive)); // higher amplifier wins despite being finite
		assertEquals(-1, highAmpActive.compareTo(lowAmpActive));
	}

	@Test
	void testCompareTo_WithTimeProgression()
	{
		PotionEffect effect1 = new PotionEffect(PotionEffectType.SPEED, 200, 1);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 150, 1);

		ActivePotionEffect active1 = new ActivePotionEffect(effect1);
		ActivePotionEffect active2 = new ActivePotionEffect(effect2);

		// Initially, active1 should win (longer duration)
		assertEquals(-1, active1.compareTo(active2));

		// After 100 ticks, durations should be closer
		server.getScheduler().performTicks(100);
		assertEquals(-1, active1.compareTo(active2)); // active1 still wins

		// After another 60 ticks, active2 should be expired
		server.getScheduler().performTicks(60);
		assertEquals(-1, active1.compareTo(active2)); // active1 wins over expired effect
	}

	@Test
	void testCompareTo_SelfComparison()
	{
		// Given
		PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 200, 1);
		ActivePotionEffect active = new ActivePotionEffect(effect);

		// When & Then
		assertEquals(0, active.compareTo(active));
	}

	@Test
	void testCompareTo_ComplexScenario()
	{
		// Test multiple effects with different combinations
		PotionEffect effect1 = new PotionEffect(PotionEffectType.SPEED, 200, 3);  // amp 3, duration 200
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, -1, 2);   // amp 2, infinite
		PotionEffect effect3 = new PotionEffect(PotionEffectType.SPEED, 100, 1);  // amp 1, duration 100

		ActivePotionEffect active1 = new ActivePotionEffect(effect1);
		ActivePotionEffect active2 = new ActivePotionEffect(effect2);
		ActivePotionEffect active3 = new ActivePotionEffect(effect3);

		// When & Then
		// active1 should win (highest amplifier)
		assertEquals(-1, active1.compareTo(active2));
		assertEquals(-1, active1.compareTo(active3));

		// active2 should beat active3 (higher amplifier)
		assertEquals(-1, active2.compareTo(active3));
		assertEquals(1, active3.compareTo(active2));
	}

	@Test
	void testCompareTo_ZeroAmplifiers()
	{
		// Given
		PotionEffect effect1 = new PotionEffect(PotionEffectType.SPEED, 200, 0);
		PotionEffect effect2 = new PotionEffect(PotionEffectType.SPEED, 100, 0);

		ActivePotionEffect active1 = new ActivePotionEffect(effect1);
		ActivePotionEffect active2 = new ActivePotionEffect(effect2);

		// When & Then
		assertEquals(-1, active1.compareTo(active2)); // longer duration wins when amplifiers are equal
		assertEquals(1, active2.compareTo(active1));
	}

}
