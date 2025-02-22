package org.mockbukkit.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

@ExtendWith(MockBukkitExtension.class)
public abstract class ContainerStateMockTest
{

	protected abstract ContainerStateMock instance();

	private void addItem(ContainerStateMock container)
	{
		container.getInventory().addItem(new ItemStackMock(Material.EMERALD, 4));
	}

	private void checkFirstItemIsFourEmeralds(ContainerStateMock container)
	{
		Inventory inventory = container.getInventory();
		assertFalse(inventory.isEmpty());
		ItemStack item = inventory.getItem(0);
		assertNotNull(item);
		assertEquals(Material.EMERALD, item.getType());
		assertEquals(4, item.getAmount());
	}

	private void changeFirstItem(ContainerStateMock container)
	{
		Inventory inventory = container.getInventory();
		ItemStack item = inventory.getItem(0);
		assertNotNull(item);
		item.setType(Material.DIAMOND_BLOCK);
		item.setAmount(2);
	}

	private ContainerStateMock initInstance()
	{
		ContainerStateMock original = instance();
		original.setLock("jeb");
		original.setCustomName("stash");
		addItem(original);
		return original;
	}

	@Test
	void testCopy_deeplyCopies()
	{
		ContainerStateMock original = initInstance();
		ContainerStateMock copy = (ContainerStateMock) original.copy();
		assertNotNull(copy);
		assertEquals(original, copy);
		checkFirstItemIsFourEmeralds(copy);

		changeFirstItem(copy);
		copy.setLock("val");
		copy.setCustomName("box");

		checkFirstItemIsFourEmeralds(original);
		assertEquals("jeb", original.getLock());
		assertEquals("stash", original.getCustomName());
	}

	@Nested
	class GetLock
	{
		@Test
		void getDefaultShouldBeEmpty()
		{
			ContainerStateMock original = instance();
			assertEquals("", original.getLock());
		}

		@Test
		void givenNullLockShouldReturnEmpty()
		{
			ContainerStateMock original = instance();
			original.setLock(null);
			assertEquals("", original.getLock());
		}

		@Test
		void givenCustomLockShouldReturnLock()
		{
			ContainerStateMock original = instance();
			original.setLock("jeb");
			assertEquals("jeb", original.getLock());
		}
	}

}
