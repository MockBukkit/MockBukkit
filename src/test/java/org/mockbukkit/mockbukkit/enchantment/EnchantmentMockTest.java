package org.mockbukkit.mockbukkit.enchantment;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class EnchantmentMockTest
{

	@Test
	void testEnchantmentValuesContainsEnchantment()
	{
		final Enchantment[] enchantments = Enchantment.values();
		assertTrue(enchantments.length > 0);
	}

	@Test
	void displayName_notNull()
	{
		assertNotNull(Enchantment.PROTECTION.displayName(1));
	}

	@Test
	void isTradeable()
	{
		assertTrue(Enchantment.FEATHER_FALLING.isTradeable());
	}

	@Test
	void isDiscoverable()
	{
		assertTrue(Enchantment.FEATHER_FALLING.isDiscoverable());
	}

	@Test
	void getMaxModifiedCost()
	{
		assertEquals(11, Enchantment.FEATHER_FALLING.getMaxModifiedCost(1));
	}

	@Test
	void getMinModifiedCost()
	{
		assertEquals(5, Enchantment.FEATHER_FALLING.getMinModifiedCost(1));
	}

	@Test
	void getName()
	{
		assertNotNull(Enchantment.PROTECTION.getName());
	}

	@Test
	void getMaxLevel()
	{
		assertEquals(1, Enchantment.MENDING.getMaxLevel());
	}

	@Test
	void getStartLeve()
	{
		assertEquals(1, Enchantment.UNBREAKING.getStartLevel());
	}

	@Test
	void isTreasure()
	{
		assertFalse(Enchantment.FLAME.isTreasure());
	}

	@Test
	void isCursed()
	{
		assertFalse(Enchantment.FLAME.isCursed());
	}

	@Test
	void conflictsWith()
	{
		assertTrue(Enchantment.FIRE_PROTECTION.conflictsWith(Enchantment.PROTECTION));
	}


	@Test
	void conflictsWith_nullInput()
	{
		assertThrows(IllegalArgumentException.class, () -> Enchantment.FIRE_PROTECTION.conflictsWith(null));
	}
}
