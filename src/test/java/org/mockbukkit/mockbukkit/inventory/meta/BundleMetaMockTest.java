package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BundleMetaMockTest
{

	private BundleMetaMock meta;

	@BeforeEach
	void setUp()
	{
		meta = new BundleMetaMock();
	}

	@Test
	void cloneConstructor_CopiesValues()
	{
		meta.setItems(Arrays.asList(new ItemStackMock(Material.STONE), new ItemStackMock(Material.DIRT)));

		BundleMetaMock otherMeta = new BundleMetaMock(meta);

		assertEquals(2, otherMeta.getItems().size());
		assertEquals(Material.STONE, otherMeta.getItems().get(0).getType());
		assertEquals(Material.DIRT, otherMeta.getItems().get(1).getType());
	}

	@Test
	void hasItems_Constructor_False()
	{
		assertFalse(meta.hasItems());
	}

	@Test
	void hasItems_hasItem_True()
	{
		meta.addItem(new ItemStackMock(Material.STONE));
		assertTrue(meta.hasItems());
	}

	@Test
	void getItems_Constructor_EmptyList()
	{
		assertTrue(meta.getItems().isEmpty());
	}

	@Test
	void getItems_ReturnsItems()
	{
		meta.addItem(new ItemStackMock(Material.STONE));
		assertEquals(1, meta.getItems().size());
		assertEquals(Material.STONE, meta.getItems().get(0).getType());
	}

	@Test
	void setItems()
	{
		meta.setItems(Arrays.asList(new ItemStackMock(Material.STONE), new ItemStackMock(Material.DIRT)));
		assertEquals(2, meta.getItems().size());
		assertEquals(Material.STONE, meta.getItems().get(0).getType());
		assertEquals(Material.DIRT, meta.getItems().get(1).getType());
	}

	@Test
	void setItems_NullItem_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () ->
		{
			meta.setItems(Arrays.asList(new ItemStackMock(Material.STONE), null, new ItemStackMock(Material.DIRT)));
		});
	}

	@Test
	void setItems_AirItem_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () ->
		{
			meta.setItems(Arrays.asList(
							new ItemStackMock(Material.STONE),
							new ItemStackMock(Material.AIR),
							new ItemStackMock(Material.DIRT)
					)
			);
		});
	}

	@Test
	void addItems()
	{
		meta.addItem(new ItemStackMock(Material.STONE));
		meta.addItem(new ItemStackMock(Material.DIRT));
		assertEquals(2, meta.getItems().size());
		assertEquals(Material.STONE, meta.getItems().get(0).getType());
		assertEquals(Material.DIRT, meta.getItems().get(1).getType());
	}

	@Test
	void addItems_NullItem_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> meta.addItem(null));
	}

	@Test
	void addItems_AirItem_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> meta.addItem(new ItemStackMock(Material.AIR)));
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		BundleMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		BundleMetaMock clone = meta.clone();
		clone.addItem(new ItemStackMock(Material.STONE));
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		meta.setItems(Arrays.asList(new ItemStackMock(Material.STONE), new ItemStackMock(Material.DIRT)));

		BundleMetaMock otherMeta = meta.clone();

		assertEquals(2, otherMeta.getItems().size());
		assertEquals(Material.STONE, otherMeta.getItems().get(0).getType());
		assertEquals(Material.DIRT, otherMeta.getItems().get(1).getType());
	}

}
