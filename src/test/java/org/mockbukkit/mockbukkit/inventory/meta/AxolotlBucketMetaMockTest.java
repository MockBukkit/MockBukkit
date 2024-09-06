package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.entity.Axolotl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class AxolotlBucketMetaMockTest
{

	private AxolotlBucketMetaMock meta;

	@BeforeEach
	void setUp()
	{
		meta = new AxolotlBucketMetaMock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertNull(meta.getVariant());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		meta.setVariant(Axolotl.Variant.GOLD);

		AxolotlBucketMetaMock clone = new AxolotlBucketMetaMock(meta);

		assertEquals(Axolotl.Variant.GOLD, clone.getVariant());
	}

	@Test
	void setVariant()
	{
		meta.setVariant(Axolotl.Variant.GOLD);
		assertEquals(Axolotl.Variant.GOLD, meta.getVariant());
	}

	@Test
	void setVariant_NullVariant_DefaultToLucy()
	{
		meta.setVariant(null);
		assertEquals(Axolotl.Variant.LUCY, meta.getVariant());
	}

	@Test
	void hasVariant()
	{
		assertFalse(meta.hasVariant());

		meta.setVariant(Axolotl.Variant.GOLD);

		assertTrue(meta.hasVariant());
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		AxolotlBucketMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		AxolotlBucketMetaMock clone = meta.clone();
		clone.setVariant(Axolotl.Variant.WILD);
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		meta.setVariant(Axolotl.Variant.GOLD);

		AxolotlBucketMetaMock clone = meta.clone();

		assertEquals(Axolotl.Variant.GOLD, clone.getVariant());
	}

}
