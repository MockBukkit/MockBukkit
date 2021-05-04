package be.seeseemelk.mockbukkit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.enchantments.EnchantmentsMock;

class EnchantmentTests
{
	@BeforeEach
	public void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testEnchantmentValuesContainsEnchantment()
	{
		final Enchantment[] enchantments = Enchantment.values();
		assertTrue(enchantments.length > 0);
	}

	@Test
	void testEnchantmentsRegisterTwiceDoesNotThrow()
	{
		EnchantmentsMock.registerDefaultEnchantments();
		int originalLength = Enchantment.values().length;
		EnchantmentsMock.registerDefaultEnchantments();
		int newLength = Enchantment.values().length;
		assertEquals(originalLength, newLength);
	}
}
