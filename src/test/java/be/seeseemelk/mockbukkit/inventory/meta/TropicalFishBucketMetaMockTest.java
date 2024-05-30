package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TropicalFishBucketMetaMockTest
{

	private TropicalFishBucketMetaMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new TropicalFishBucketMetaMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

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
	void checkVars_CorrectDefaults()
	{
		meta.checkVars();
		assertEquals(DyeColor.WHITE, meta.getPatternColor());
		assertEquals(DyeColor.WHITE, meta.getBodyColor());
		assertEquals(TropicalFish.Pattern.KOB, meta.getPattern());
	}

	@Test
	void getPatternColor_NullVariant_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> meta.getPatternColor());
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
		assertThrowsExactly(NullPointerException.class, () -> meta.getBodyColor());
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
		assertThrowsExactly(NullPointerException.class, () -> meta.getPattern());
	}

	@Test
	void setPattern_SetsPattern()
	{
		meta.setPattern(TropicalFish.Pattern.BETTY);

		assertEquals(TropicalFish.Pattern.BETTY, meta.getPattern());
	}

	@Test
	void equals_SameInstance_True()
	{
		meta.checkVars();
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentObjects_SameValues_True()
	{
		meta.checkVars();
		assertEquals(meta, meta.clone());
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

}
