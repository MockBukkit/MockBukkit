package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CrossbowMetaMockTest
{

	private CrossbowMetaMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new CrossbowMetaMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertTrue(meta.getChargedProjectiles().isEmpty());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		CrossbowMetaMock meta2 = new CrossbowMetaMock(meta);

		assertEquals(1, meta2.getChargedProjectiles().size());
	}

	@Test
	void hasChargedProjectiles_NoProjectiles_ReturnsFalse()
	{
		meta.setChargedProjectiles(null);

		assertFalse(meta.hasChargedProjectiles());
	}

	@Test
	void hasChargedProjectiles_Projectiles_ReturnsTrue()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		assertTrue(meta.hasChargedProjectiles());
	}

	@Test
	void getChargedProjectiles_NoProjectiles_EmptyList()
	{
		meta.setChargedProjectiles(null);

		assertNotNull(meta.getChargedProjectiles());
		assertTrue(meta.getChargedProjectiles().isEmpty());
	}

	@Test
	void getChargedProjectiles_Projectiles_ClonedList()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		List<ItemStack> projectiles = meta.getChargedProjectiles();
		assertEquals(1, projectiles.size());

		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));
		assertEquals(2, meta.getChargedProjectiles().size());

		assertEquals(1, projectiles.size());
	}

	@Test
	void setChargedProjectiles_Null_ClearsList()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		meta.setChargedProjectiles(null);

		assertFalse(meta.hasChargedProjectiles());
	}

	@Test
	void setChargedProjectiles_SetsList()
	{
		meta.setChargedProjectiles(
				List.of(new ItemStack(Material.FIREWORK_ROCKET), new ItemStack(Material.FIREWORK_ROCKET)));

		assertEquals(2, meta.getChargedProjectiles().size());
	}

	@Test
	void setChargedProjectiles_RemovesOld()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		meta.setChargedProjectiles(
				List.of(new ItemStack(Material.FIREWORK_ROCKET), new ItemStack(Material.FIREWORK_ROCKET)));

		assertEquals(2, meta.getChargedProjectiles().size());
	}

	@Test
	void setChargedProjectiles_AcceptsAllArrows()
	{
		meta.setChargedProjectiles(List.of(new ItemStack(Material.FIREWORK_ROCKET), new ItemStack(Material.ARROW),
				new ItemStack(Material.TIPPED_ARROW), new ItemStack(Material.SPECTRAL_ARROW)));
	}

	@Test
	void setChargedProjectiles_NullItem_ThrowsException()
	{
		// List#of doesn't accept null values.
		assertThrowsExactly(IllegalArgumentException.class,
				() -> meta.setChargedProjectiles(Arrays.asList(new ItemStack(Material.FIREWORK_ROCKET), null)));
	}

	@Test
	void setChargedProjectiles_NotArrow_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class,
				() -> meta.setChargedProjectiles(List.of(new ItemStack(Material.STONE))));
	}

	@Test
	void addChargedProjectile_AddsProjectile()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		assertEquals(1, meta.getChargedProjectiles().size());
	}

	@Test
	void addChargedProjectile_DoesntOverwrite()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		assertEquals(2, meta.getChargedProjectiles().size());
	}

	@Test
	void addChargedProjectile_AcceptsAllArrows()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));
		meta.addChargedProjectile(new ItemStack(Material.ARROW));
		meta.addChargedProjectile(new ItemStack(Material.SPECTRAL_ARROW));
		meta.addChargedProjectile(new ItemStack(Material.TIPPED_ARROW));
	}

	@Test
	void addChargedProjectile_NullItem_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> meta.addChargedProjectile(null));
	}

	@Test
	void addChargedProjectile_NotArrow_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class,
				() -> meta.addChargedProjectile(new ItemStack(Material.STONE)));
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		CrossbowMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		CrossbowMetaMock clone = meta.clone();
		clone.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		meta.addChargedProjectile(new ItemStack(Material.FIREWORK_ROCKET));

		CrossbowMetaMock meta2 = meta.clone();

		assertEquals(1, meta2.getChargedProjectiles().size());
	}

}
