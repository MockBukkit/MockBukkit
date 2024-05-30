package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AxolotlMockTest
{

	private ServerMock server;
	private AxolotlMock axolotl;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		axolotl = new AxolotlMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetPickupSound()
	{
		assertEquals(Sound.ITEM_BUCKET_FILL_AXOLOTL, axolotl.getPickupSound());
	}

	@Test
	void testGetBaseBucketItem()
	{
		assertEquals(Material.AXOLOTL_BUCKET, axolotl.getBaseBucketItem().getType());
	}

	@Test
	void testIsPlayingDeadDefault()
	{
		assertFalse(axolotl.isPlayingDead());
	}

	@Test
	void testSetPlayingDead()
	{
		axolotl.setPlayingDead(true);
		assertTrue(axolotl.isPlayingDead());
	}

	@Test
	void testIsFromBucketDefault()
	{
		assertFalse(axolotl.isFromBucket());
	}

	@Test
	void testSetFromBucket()
	{
		axolotl.setFromBucket(true);
		assertTrue(axolotl.isFromBucket());
	}

	@Test
	void testGetVariantDefault()
	{
		assertEquals(Axolotl.Variant.LUCY, axolotl.getVariant());
	}

	@Test
	void testSetVariant()
	{
		axolotl.setVariant(Axolotl.Variant.CYAN);
		assertEquals(Axolotl.Variant.CYAN, axolotl.getVariant());
	}

	@Test
	void testSetVariantThrowsWithNullVariant()
	{
		assertThrows(NullPointerException.class, () -> axolotl.setVariant(null));
	}

	@Test
	void testIsBreedItemItemStack()
	{
		assertTrue(axolotl.isBreedItem(new ItemStack(Material.TROPICAL_FISH_BUCKET)));
	}

	@Test
	void testIsBreedItemMaterial()
	{
		assertTrue(axolotl.isBreedItem(Material.TROPICAL_FISH_BUCKET));
	}

	@Test
	void testIsBreedItemItemStackFalse()
	{
		assertFalse(axolotl.isBreedItem(new ItemStack(Material.STONE)));
	}

	@Test
	void testIsBreedItemMaterialFalse()
	{
		assertFalse(axolotl.isBreedItem(Material.STONE));
	}

	@Test
	void testIsBreedItemNull()
	{
		assertThrows(NullPointerException.class, () -> axolotl.isBreedItem((ItemStack) null));
	}

	@Test
	void testIsBreedItemNullWithMaterial()
	{
		assertThrows(NullPointerException.class, () -> axolotl.isBreedItem((Material) null));
	}

}
