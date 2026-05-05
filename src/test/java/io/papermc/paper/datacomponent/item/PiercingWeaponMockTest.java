package io.papermc.paper.datacomponent.item;

import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("UnstableApiUsage")
@ExtendWith(MockBukkitExtension.class)
class PiercingWeaponMockTest
{

	@Test
	void givenSimpleCreation()
	{
		Key hitSound = NamespacedKey.fromString("test:hitSound");
		Key sound = NamespacedKey.fromString("test:sound");

		PiercingWeapon piercing = PiercingWeapon.piercingWeapon()
				.dealsKnockback(true)
				.dismounts(false)
				.hitSound(hitSound)
				.sound(sound)
				.build();
		
		assertTrue(piercing.dealsKnockback());
		assertFalse(piercing.dismounts());
		assertEquals(hitSound, piercing.hitSound());
		assertEquals(sound, piercing.sound());
	}

}
