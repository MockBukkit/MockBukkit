package org.mockbukkit.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
			assertFalse(original.isLocked());
		}

		@Test
		void givenNullLockShouldReturnEmpty()
		{
			ContainerStateMock original = instance();
			original.setLock(null);
			assertEquals("", original.getLock());
			assertFalse(original.isLocked());
		}

		@Test
		void givenCustomLockShouldReturnLock()
		{
			ContainerStateMock original = instance();
			original.setLock("jeb");
			assertEquals("jeb", original.getLock());
			assertTrue(original.isLocked());
		}
	}

	@Nested
	class GetInventory
	{
		@Test
		void givenStateWithItems_WhenGettingInventory_ThenSameInventoryIsReturned()
		{
			ContainerStateMock container = instance();

			Inventory originalInventory = container.getInventory();
			assertFalse(originalInventory.contains(ItemStack.of(Material.DIAMOND)));

			container.getInventory().addItem(ItemStack.of(Material.DIAMOND));
			assertTrue(originalInventory.contains(ItemStack.of(Material.DIAMOND)));
			assertSame(originalInventory, container.getInventory());
		}
	}

	@Nested
	class GetSnapshotInventory
	{
		@Test
		void givenStateWithItems_WhenGettingInventory_ThenDifferentInventoryIsReturned()
		{
			ContainerStateMock container = instance();

			Inventory inventory = container.getInventory();
			Inventory snapshotInventory = container.getSnapshotInventory();
			assertEquals(inventory, snapshotInventory);
			assertNotSame(inventory, snapshotInventory);
		}

		@Test
		void givenStateWithItems_WhenInventoryIsChanged_TheSnapshotInventoryIsNotChanged()
		{
			ContainerStateMock container = instance();

			Inventory inventory = container.getInventory();
			Inventory snapshotInventory = container.getSnapshotInventory();

			inventory.addItem(ItemStack.of(Material.DIAMOND));

			assertTrue(inventory.contains(ItemStack.of(Material.DIAMOND)));
			assertFalse(snapshotInventory.contains(ItemStack.of(Material.DIAMOND)));
			assertNotEquals(inventory, snapshotInventory);
		}
	}

}
