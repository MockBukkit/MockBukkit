package io.papermc.paper.datacomponent.item.consumable;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("UnstableApiUsage")
@ExtendWith(MockBukkitExtension.class)
class ConsumableTypesBridgeMockTest
{

	private final ConsumableTypesBridgeMock bridge = new ConsumableTypesBridgeMock();

	@Test
	void givenClearAllStatusEffects()
	{
		ConsumeEffect.ClearAllStatusEffects actual = bridge.clearAllStatusEffects();
		assertNotNull(actual);
	}

	@Test
	void givenPlaySound()
	{
		NamespacedKey key = Registry.SOUNDS.getKey(Sound.AMBIENT_CAVE);
		assertNotNull(key);

		ConsumeEffect.PlaySound actual = bridge.playSoundEffect(key);
		assertNotNull(actual);
		assertEquals(key, actual.sound());
	}

	@Test
	void givenTeleportRandomly()
	{
		float diameter = 20;
		ConsumeEffect.TeleportRandomly actual = bridge.teleportRandomlyEffect(diameter);
		assertNotNull(actual);
		assertEquals(diameter, actual.diameter());
	}

}
