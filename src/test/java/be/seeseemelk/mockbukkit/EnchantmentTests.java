package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.enchantments.EnchantmentsMock;
import org.bukkit.enchantments.Enchantment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        int originalLength = Enchantment.values().length;
        EnchantmentsMock.registerDefaultEnchantments();
        int newLength = Enchantment.values().length;
        assertEquals(originalLength, newLength);
    }
}
