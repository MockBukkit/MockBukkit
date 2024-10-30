package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.Material;
import org.bukkit.Rotation;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemFrameMockTest
{
	private ServerMock server;
	private ItemFrameMock itemFrame;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		itemFrame = new ItemFrameMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getItem_GivenDefaultValue()
	{
		assertEquals(ItemStack.empty(), itemFrame.getItem());
	}

	@Test
	void getItem_GivenItemChangeAndWithoutPlayingSound()
	{
		ItemStack item = new ItemStack(Material.DIAMOND);
		itemFrame.setItem(item);

		ItemStack actual = itemFrame.getItem();
		assertNotNull(actual);
		assertEquals(item, actual);
		assertNotSame(item, actual);
	}

	@Test
	void getItem_GivenItemChangeAndPlayingSoundInWorld()
	{
		WorldMock world = new WorldMock();
		itemFrame.getLocation().setWorld(world);

		ItemStack item = new ItemStack(Material.DIAMOND);
		itemFrame.setItem(item);

		ItemStack actual = itemFrame.getItem();
		assertNotNull(actual);
		assertEquals(item, actual);
		assertNotSame(item, actual);
	}

	@Test
	void setItem_GivenNullItem()
	{
		itemFrame.setItem(null);

		ItemStack actual = itemFrame.getItem();
		assertNotNull(actual);
		assertEquals(ItemStack.empty(), actual);
	}

	@Test
	void setItem_GivenAirItem()
	{
		ItemStack item = new ItemStack(Material.AIR);

		itemFrame.setItem(item);

		ItemStack actual = itemFrame.getItem();
		assertNotNull(actual);
		assertEquals(ItemStack.empty(), actual);
	}

	@Test
	void getItemDropChance_GivenDefaultValue()
	{
		assertEquals(1.0F, itemFrame.getItemDropChance());
	}

	@ParameterizedTest
	@ValueSource(floats = {0.0F, 0.25F, 0.5F, 0.75F, 1.0F})
	void getItemDropChance_GivenValidValues(float value)
	{
		itemFrame.setItemDropChance(value);
		assertEquals(value, itemFrame.getItemDropChance());
	}

	@ParameterizedTest
	@ValueSource(floats = {-1, -0.01F, 1.01F, 2F})
	void setItemDropChance_GivenIllegalValues(float value)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> itemFrame.setItemDropChance(value));
		assertEquals(String.format("Chance (%s) outside range [0, 1]", value), e.getMessage());
	}

	@Test
	void getRotation_GivenDefaultValue()
	{
		assertEquals(Rotation.NONE, itemFrame.getRotation());
	}

	@ParameterizedTest
	@EnumSource(Rotation.class)
	void getRotation_GivenPossibleValues(Rotation rotation)
	{
		itemFrame.setRotation(rotation);
		assertEquals(rotation, itemFrame.getRotation());
	}

	@Test
	void setRotation_GivenNullValue()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> itemFrame.setRotation(null));
		assertEquals("Rotation cannot be null", e.getMessage());
	}

	@Test
	void isVisible_GivenDefaultValue()
	{
		assertTrue(itemFrame.isVisible());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void isVisible_GivenPossibleValues(boolean visible)
	{
		itemFrame.setVisible(visible);
		assertEquals(visible, itemFrame.isVisible());
	}

	@Test
	void isFixed_GivenDefaultValue()
	{
		assertFalse(itemFrame.isFixed());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
	void isFixed_GivenPossibleValues(boolean visible)
	{
		itemFrame.setFixed(visible);
		assertEquals(visible, itemFrame.isFixed());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ITEM_FRAME, itemFrame.getType());
	}
}
