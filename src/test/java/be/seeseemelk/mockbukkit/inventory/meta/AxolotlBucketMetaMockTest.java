package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.entity.Axolotl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AxolotlBucketMetaMockTest
{

	private AxolotlBucketMetaMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new AxolotlBucketMetaMock();
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
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
	void clone_CopiesValues()
	{
		meta.setVariant(Axolotl.Variant.GOLD);

		AxolotlBucketMetaMock clone = meta.clone();

		assertEquals(Axolotl.Variant.GOLD, clone.getVariant());
	}

}
