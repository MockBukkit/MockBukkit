package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ItemStackMockTest
{
	@Test
	void getEnchantments_GivenDefaultValue()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		Map<Enchantment, Integer> actual = itemStack.getEnchantments();
		assertNotNull(actual);
		assertTrue(actual.isEmpty());
	}

	@Test
	void getEnchantments_GivenCustomEnchantment()
	{
		ItemStackMock itemStack = new ItemStackMock(Material.DIAMOND_PICKAXE);
		itemStack.addEnchantment(Enchantment.EFFICIENCY, 5);

		Map<Enchantment, Integer> actual = itemStack.getEnchantments();
		assertNotNull(actual);
		assertEquals(1, actual.size());
		assertEquals(5, actual.get(Enchantment.EFFICIENCY));
	}

}
