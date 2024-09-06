package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDisplayMockTest
{

	private ItemDisplayMock itemDisplay;

	@BeforeEach
	void setUp()
	{
		ServerMock serverMock = MockBukkit.mock();
		this.itemDisplay = new ItemDisplayMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

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
