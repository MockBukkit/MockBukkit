package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;
import net.kyori.adventure.text.Component;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class BannerMockTest
{

	private BannerMock meta;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		meta = new BannerMock(Material.BLACK_BANNER);
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
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BannerMock(Material.BLACK_BANNER));
	}

	@Test
	void constructor_Material_NotBanner_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BannerMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		assertDoesNotThrow(() -> new BannerMock(new BlockMock(Material.BLACK_BANNER)));
	}

	@Test
	void constructor_Block_NotBanner_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BannerMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void constructor_Clone_CopiesValues()
	{
		meta.setBaseColor(DyeColor.CYAN);
		meta.setPatterns(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));
		meta.customName(Component.text("Custom Name"));

		BannerMock cloned = new BannerMock(meta);

		assertEquals(DyeColor.CYAN, cloned.getBaseColor());
		assertEquals(Component.text("Custom Name"), cloned.customName());
		assertEquals(1, cloned.getPatterns().size());
		assertEquals(DyeColor.BLUE, cloned.getPatterns().get(0).getColor());
		assertEquals(PatternType.STRIPE_BOTTOM, cloned.getPatterns().get(0).getPattern());
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		BannerMock state = meta.getSnapshot();
		assertNotSame(meta, state);
	}

	@Test
	void getSnapshot_CopiesValues()
	{
		meta.setBaseColor(DyeColor.CYAN);
		meta.setPatterns(List.of(new Pattern(DyeColor.BLUE, PatternType.STRIPE_BOTTOM)));
		BannerMock state = meta.getSnapshot();

		assertEquals(meta.getBaseColor(), state.getBaseColor());
		assertEquals(meta.getPatterns(), state.getPatterns());
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
	void blockStateMock_MockState_CorrectType()
	{
		for (Material mat : Tag.ITEMS_BANNERS.getValues())
		{
			if (BlockStateMock.mockState(new BlockMock(mat)) instanceof BannerMock)
				continue;
			fail("BlockStateMock for '" + mat + "' is not a " + BannerMock.class.getSimpleName());
		}
	}

	@Test
	void testSetCustomName()
	{
		String customName = "Custom Name";
		meta.setCustomName(customName);
		assertEquals(customName, meta.getCustomName());
	}

}
