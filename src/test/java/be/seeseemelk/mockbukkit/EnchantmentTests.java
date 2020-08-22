package be.seeseemelk.mockbukkit;

import static org.junit.Assert.assertTrue;

import org.bukkit.enchantments.Enchantment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.enchantments.EnchantmentsMock;

public class EnchantmentTests
{
	@Before
	public void setUp()
	{
		MockBukkit.mock();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void testEnchantmentValuesContainsEnchantment()
	{
		final Enchantment[] enchantments = Enchantment.values();
		assertTrue(enchantments.length > 0);
	}

	@Test
	public void testEnchantmentsRegisterTwiceDoesNotThrow()
	{
		EnchantmentsMock.registerDefaultEnchantments();
		EnchantmentsMock.registerDefaultEnchantments();
	}
}
