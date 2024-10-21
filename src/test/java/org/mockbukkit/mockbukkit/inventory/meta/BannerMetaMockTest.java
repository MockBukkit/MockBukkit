package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BannerMetaMockTest
{

	private BannerMetaMock meta;

	@BeforeEach
	void setUp()
	{
		meta = new BannerMetaMock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertTrue(meta.getPatterns().isEmpty());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		meta.setPatterns(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));

		BannerMetaMock cloned = new BannerMetaMock(meta);

		assertEquals(1, cloned.getPatterns().size());
		assertEquals(DyeColor.BLUE, cloned.getPatterns().get(0).getColor());
		assertEquals(PatternType.STRIPE_BOTTOM, cloned.getPatterns().get(0).getPattern());
	}

	@Test
	void setPatterns_Sets()
	{
		meta.setPatterns(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));
		assertEquals(1, meta.getPatterns().size());
		assertEquals(DyeColor.BLUE, meta.getPatterns().get(0).getColor());
		assertEquals(PatternType.STRIPE_BOTTOM, meta.getPatterns().get(0).getPattern());
	}

	@Test
	void setPatterns_ClonesList()
	{
		List<Pattern> patterns = new ArrayList<>(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));
		meta.setPatterns(patterns);
		assertEquals(1, meta.numberOfPatterns());

		patterns.clear();
		assertTrue(patterns.isEmpty());

		assertEquals(1, meta.numberOfPatterns());
	}

	@Test
	void addPattern_AddsPattern()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		assertEquals(1, meta.numberOfPatterns());
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE));
		assertEquals(2, meta.numberOfPatterns());

		assertEquals(DyeColor.BLUE, meta.getPattern(0).getColor());
		assertEquals(DyeColor.CYAN, meta.getPattern(1).getColor());
	}

	@Test
	void getPattern_ReturnsCorrectPattern()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE));

		assertEquals(DyeColor.BLUE, meta.getPattern(0).getColor());
		assertEquals(DyeColor.CYAN, meta.getPattern(1).getColor());
	}

	@Test
	void removePattern_RemovesCorrectPattern()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE));

		meta.removePattern(0);

		assertEquals(DyeColor.CYAN, meta.getPattern(0).getColor());
	}

	@Test
	void setPattern_SetsCorrectPattern()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE));

		meta.setPattern(0, new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));

		assertEquals(DyeColor.RED, meta.getPattern(0).getColor());
		assertEquals(DyeColor.CYAN, meta.getPattern(1).getColor());
	}

	@Test
	void numberOfPatterns_CorrectNumber()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE));

		assertEquals(2, meta.numberOfPatterns());
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		BannerMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		BannerMetaMock clone = meta.clone();
		clone.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		meta.setPatterns(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));

		BannerMetaMock cloned = meta.clone();

		assertEquals(1, cloned.getPatterns().size());
		assertEquals(DyeColor.BLUE, cloned.getPatterns().get(0).getColor());
		assertEquals(PatternType.STRIPE_BOTTOM, cloned.getPatterns().get(0).getPattern());
	}

}
