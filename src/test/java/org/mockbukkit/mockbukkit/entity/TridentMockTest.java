package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class TridentMockTest
{

	@MockBukkitInject
	private TridentMock trident;

	@Test
	void getDamage_default()
	{
		assertEquals(8.0, trident.getDamage());
	}

	@Test
	void hasGlint()
	{
		assertFalse(trident.hasGlint());
	}

	@Test
	void setGlint()
	{
		trident.setGlint(true);
		assertTrue(trident.hasGlint());
	}

	@Test
	void getLoyaltyLevel_default()
	{
		assertEquals(0, trident.getLoyaltyLevel());
	}

	@Test
	void setLoyaltyLevel()
	{
		trident.setLoyaltyLevel(4);
		assertEquals(4, trident.getLoyaltyLevel());
	}

	@Test
	void setLoyaltyLevel_negative()
	{
		assertThrows(IllegalArgumentException.class, () -> trident.setLoyaltyLevel(-1));
	}

	@Test
	void setLoyaltyLevel_large()
	{
		assertThrows(IllegalArgumentException.class, () -> trident.setLoyaltyLevel(128));
	}

	@Test
	void hasDealtDamage_default()
	{
		assertFalse(trident.hasDealtDamage());
	}

	@Test
	void setHasDealtDamage()
	{
		trident.setHasDealtDamage(true);
		assertTrue(trident.hasDealtDamage());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.TRIDENT, trident.getType());
	}

}
