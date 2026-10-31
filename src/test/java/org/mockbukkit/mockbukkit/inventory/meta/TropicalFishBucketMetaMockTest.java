package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class TropicalFishBucketMetaMockTest
{

	@MockBukkitInject
	private TropicalFishBucketMetaMock meta;

	@Test
	void cloneConstructor_CopiesValues()
	{
		meta.setPatternColor(DyeColor.CYAN);
		meta.setBodyColor(DyeColor.MAGENTA);
		meta.setPattern(TropicalFish.Pattern.BETTY);

		TropicalFishBucketMetaMock otherMeta = new TropicalFishBucketMetaMock(meta);

		assertEquals(DyeColor.CYAN, otherMeta.getPatternColor());
		assertEquals(DyeColor.MAGENTA, otherMeta.getBodyColor());
		assertEquals(TropicalFish.Pattern.BETTY, otherMeta.getPattern());
	}

	@Test
	void hasVariant_Constructor_False()
	{
		assertFalse(meta.hasVariant());
	}

	@Test
	void hasVariant_AnyValueSet_True()
	{
		TropicalFishBucketMetaMock meta = new TropicalFishBucketMetaMock();
		assertFalse(meta.hasVariant());
		meta.setPatternColor(DyeColor.CYAN);
		assertTrue(meta.hasVariant());

		meta = new TropicalFishBucketMetaMock();
		assertFalse(meta.hasVariant());
		meta.setBodyColor(DyeColor.CYAN);
		assertTrue(meta.hasVariant());

		meta = new TropicalFishBucketMetaMock();
		assertFalse(meta.hasVariant());
		meta.setPattern(TropicalFish.Pattern.BETTY);
		assertTrue(meta.hasVariant());
	}

	@Test
	void getPatternColor_NullVariant_ThrowsException()
	{
		assertThrowsExactly(IllegalStateException.class, () -> meta.getPatternColor());
	}

	@Test
	void setPatternColor_SetsPatternColor()
	{
		meta.setPatternColor(DyeColor.CYAN);

		assertEquals(DyeColor.CYAN, meta.getPatternColor());
	}

	@Test
	void getBodyColor_NullVariant_ThrowsException()
	{
		assertThrowsExactly(IllegalStateException.class, () -> meta.getBodyColor());
	}

	@Test
	void setBodyColor_SetsBodyColor()
	{
		meta.setBodyColor(DyeColor.CYAN);

		assertEquals(DyeColor.CYAN, meta.getBodyColor());
	}

	@Test
	void getPattern_NullVariant_ThrowsException()
	{
		assertThrowsExactly(IllegalStateException.class, () -> meta.getPattern());
	}

	@Test
	void setPattern_SetsPattern()
	{
		meta.setPattern(TropicalFish.Pattern.BETTY);

		assertEquals(TropicalFish.Pattern.BETTY, meta.getPattern());
	}

	@Test
	void equals_DifferentObjects_DifferentValues_True()
	{
		TropicalFishBucketMetaMock clone = meta.clone();
		clone.setPattern(TropicalFish.Pattern.CLAYFISH);
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		meta.setPatternColor(DyeColor.CYAN);
		meta.setBodyColor(DyeColor.MAGENTA);
		meta.setPattern(TropicalFish.Pattern.BETTY);

		TropicalFishBucketMetaMock otherMeta = meta.clone();

		assertEquals(DyeColor.CYAN, otherMeta.getPatternColor());
		assertEquals(DyeColor.MAGENTA, otherMeta.getBodyColor());
		assertEquals(TropicalFish.Pattern.BETTY, otherMeta.getPattern());
	}

	@Test
	void clone_PartialState_PreservesAbsence()
	{
		meta.setBodyColor(DyeColor.RED);
		TropicalFishBucketMetaMock clone = meta.clone();

		assertTrue(clone.hasBodyColor());
		assertFalse(clone.hasPattern());
		assertEquals(meta, clone);
	}

}
