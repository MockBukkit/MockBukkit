package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class ItemDisplayMockTest
{

	@MockBukkitInject
	private ItemDisplayMock itemDisplay;

	@Test
	void getItemStack_default()
	{
		assertEquals(ItemStack.empty(), itemDisplay.getItemStack());
	}

	@Test
	void setItemStack()
	{
		ItemStack itemStack = new ItemStackMock(Material.ACACIA_FENCE);
		itemDisplay.setItemStack(itemStack);
		assertEquals(itemStack, itemDisplay.getItemStack());
	}

	@Test
	void getItemDisplayTransform_default()
	{
		assertEquals(ItemDisplay.ItemDisplayTransform.NONE, itemDisplay.getItemDisplayTransform());
	}

	@Test
	void setItemDisplayTransform()
	{
		itemDisplay.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIXED);
		assertEquals(ItemDisplay.ItemDisplayTransform.FIXED, itemDisplay.getItemDisplayTransform());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ITEM_DISPLAY, itemDisplay.getType());
	}

}
