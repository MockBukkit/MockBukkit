package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BundleMetaMockTest
{

	private BundleMetaMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new BundleMetaMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void cloneConstructor_CopiesValues()
	{
		meta.setItems(Arrays.asList(new ItemStack(Material.STONE), new ItemStack(Material.DIRT)));

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
		meta.addItem(new ItemStack(Material.STONE));
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
		meta.addItem(new ItemStack(Material.STONE));
		assertEquals(1, meta.getItems().size());
		assertEquals(Material.STONE, meta.getItems().get(0).getType());
	}

	@Test
	void setItems()
	{
		meta.setItems(Arrays.asList(new ItemStack(Material.STONE), new ItemStack(Material.DIRT)));
		assertEquals(2, meta.getItems().size());
		assertEquals(Material.STONE, meta.getItems().get(0).getType());
		assertEquals(Material.DIRT, meta.getItems().get(1).getType());
	}

	@Test
	void setItems_NullItem_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			meta.setItems(Arrays.asList(new ItemStack(Material.STONE), null, new ItemStack(Material.DIRT)));
		});
	}

	@Test
	void setItems_AirItem_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> {
			meta.setItems(Arrays.asList(new ItemStack(Material.STONE), new ItemStack(Material.AIR),
					new ItemStack(Material.DIRT)));
		});
	}

	@Test
	void addItems()
	{
		meta.addItem(new ItemStack(Material.STONE));
		meta.addItem(new ItemStack(Material.DIRT));
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
		assertThrowsExactly(IllegalArgumentException.class, () -> meta.addItem(new ItemStack(Material.AIR)));
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
		clone.addItem(new ItemStack(Material.STONE));
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		meta.setItems(Arrays.asList(new ItemStack(Material.STONE), new ItemStack(Material.DIRT)));

		BundleMetaMock otherMeta = meta.clone();

		assertEquals(2, otherMeta.getItems().size());
		assertEquals(Material.STONE, otherMeta.getItems().get(0).getType());
		assertEquals(Material.DIRT, otherMeta.getItems().get(1).getType());
	}

}
