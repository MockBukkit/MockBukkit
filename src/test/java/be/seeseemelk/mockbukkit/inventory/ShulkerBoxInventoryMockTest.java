package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ShulkerBoxInventoryMockTest
{

	private ShulkerBoxInventoryMock inventory;

	@BeforeEach
	public void setUp()
	{
		inventory = new ShulkerBoxInventoryMock(null);
	}

	@Test
	void testGetSnapshot()
	{
		assertNotNull(inventory.getSnapshot());

		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		assertNotEquals(inventory, inventory.getSnapshot());
		assertTrue(Arrays.stream(inventory.getContents()).anyMatch(stack -> stack != null && stack.isSimilar(item)));
	}

}
