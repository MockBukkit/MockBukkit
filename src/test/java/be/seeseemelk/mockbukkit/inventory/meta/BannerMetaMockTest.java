package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BannerMetaMockTest
{

	private BannerMetaMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new BannerMetaMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertNull(meta.getBaseColor());
		assertTrue(meta.getPatterns().isEmpty());
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		meta.setBaseColor(DyeColor.CYAN);
		meta.setPatterns(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));

		BannerMetaMock cloned = new BannerMetaMock(meta);

		assertEquals(DyeColor.CYAN, cloned.getBaseColor());
		assertEquals(1, cloned.getPatterns().size());
		assertEquals(DyeColor.BLUE, cloned.getPatterns().get(0).getColor());
		assertEquals(PatternType.STRIPE_BOTTOM, cloned.getPatterns().get(0).getPattern());
	}

	@Test
	void setBaseColor_Sets()
	{
		meta.setBaseColor(DyeColor.CYAN);
		assertEquals(DyeColor.CYAN, meta.getBaseColor());
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
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE_MIDDLE));
		assertEquals(2, meta.numberOfPatterns());

		assertEquals(DyeColor.BLUE, meta.getPattern(0).getColor());
		assertEquals(DyeColor.CYAN, meta.getPattern(1).getColor());
	}

	@Test
	void getPattern_ReturnsCorrectPattern()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE_MIDDLE));

		assertEquals(DyeColor.BLUE, meta.getPattern(0).getColor());
		assertEquals(DyeColor.CYAN, meta.getPattern(1).getColor());
	}

	@Test
	void removePattern_RemovesCorrectPattern()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE_MIDDLE));

		meta.removePattern(0);

		assertEquals(DyeColor.CYAN, meta.getPattern(0).getColor());
	}

	@Test
	void setPattern_SetsCorrectPattern()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE_MIDDLE));

		meta.setPattern(0, new Pattern(DyeColor.RED, PatternType.STRIPE_TOP));

		assertEquals(DyeColor.RED, meta.getPattern(0).getColor());
		assertEquals(DyeColor.CYAN, meta.getPattern(1).getColor());
	}

	@Test
	void numberOfPatterns_CorrectNumber()
	{
		meta.addPattern(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM));
		meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.CIRCLE_MIDDLE));

		assertEquals(2, meta.numberOfPatterns());
	}

	@Test
	void clone_CopiesValues()
	{
		meta.setBaseColor(DyeColor.CYAN);
		meta.setPatterns(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));

		BannerMetaMock cloned = meta.clone();

		assertEquals(DyeColor.CYAN, cloned.getBaseColor());
		assertEquals(1, cloned.getPatterns().size());
		assertEquals(DyeColor.BLUE, cloned.getPatterns().get(0).getColor());
		assertEquals(PatternType.STRIPE_BOTTOM, cloned.getPatterns().get(0).getPattern());
	}

}
