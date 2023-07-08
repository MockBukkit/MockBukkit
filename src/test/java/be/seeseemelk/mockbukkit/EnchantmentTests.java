package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.enchantments.EnchantmentsMock;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnchantmentTests
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
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

	@Test
	void testEnchantmentsConstructorPrivate() throws NoSuchMethodException
	{
		Constructor<EnchantmentsMock> constructor = EnchantmentsMock.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		Exception exception = assertThrows(InvocationTargetException.class, constructor::newInstance);
		assertTrue(exception.getCause() instanceof UnsupportedOperationException);
		assertTrue(exception.getCause().getMessage().contains("Utility class"));
	}

}
