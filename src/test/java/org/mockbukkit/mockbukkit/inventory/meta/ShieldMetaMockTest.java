package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.bukkit.DyeColor;
import org.bukkit.Server;
import org.bukkit.inventory.meta.ShieldMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.block.state.BannerStateMock;
import org.mockbukkit.mockbukkit.block.state.BeehiveStateMock;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockBukkitExtension.class)
class ShieldMetaMockTest
{

	@MockBukkitInject
	private Server server;
	private ShieldMeta meta;
	private final List<Pattern> patterns = List.of(
			new Pattern(DyeColor.GRAY, PatternType.GRADIENT),
			new Pattern(DyeColor.GRAY, PatternType.GRADIENT_UP),
			new Pattern(DyeColor.BLACK, PatternType.BRICKS)
	);

	@BeforeEach
	void setUp()
	{
		meta = new ShieldMetaMock();
	}

	@Test
	void testGetBaseColorDefault()
	{
		assertNull(meta.getBaseColor());
	}

	@Test
	void testSetBaseColor()
	{
		meta.setBaseColor(DyeColor.BROWN);
		assertEquals(DyeColor.BROWN, meta.getBaseColor());
	}

	@Test
	void testSetBaseColorNull()
	{
		assertDoesNotThrow(() -> meta.setBaseColor(null));
		assertNull(meta.getBaseColor());

		meta.setBaseColor(DyeColor.BROWN);
		meta.addPattern(new Pattern(DyeColor.GRAY, PatternType.GRADIENT));
		assertDoesNotThrow(() -> meta.setBaseColor(null));
		assertEquals(DyeColor.WHITE, meta.getBaseColor());

		meta.removePattern(0);
		assertDoesNotThrow(() -> meta.setBaseColor(null));
		assertNull(meta.getBaseColor());
	}

	@Test
	void testSetGetPatterns()
	{
		assertFalse(((BlockStateMeta) meta).hasBlockState());
		meta.setPatterns(Collections.emptyList());
		assertFalse(((BlockStateMeta) meta).hasBlockState());
		assertNotNull(meta.getPatterns());

		meta.setPatterns(patterns);
		assertTrue(((BlockStateMeta) meta).hasBlockState());
		assertEquals(patterns, meta.getPatterns());
		assertNotSame(meta.getPatterns(), meta.getPatterns());
		assertDoesNotThrow(() -> meta.getPatterns().add(new Pattern(DyeColor.MAGENTA, PatternType.CIRCLE)));

		final List<Pattern> patterns2 = List.of(
				new Pattern(DyeColor.YELLOW, PatternType.GRADIENT),
				new Pattern(DyeColor.ORANGE, PatternType.BRICKS)
		);
		meta.setPatterns(patterns2);
		assertEquals(patterns2, meta.getPatterns());
	}

	@Test
	void testGetBlockState()
	{
		assertInstanceOf(BlockStateMeta.class, meta);
		BlockStateMeta bsMeta = (BlockStateMeta) meta;
		assertFalse(bsMeta.hasBlockState());
		var state = bsMeta.getBlockState();
		assertNotNull(state);
		assertFalse(bsMeta.hasBlockState());
		assertInstanceOf(Banner.class, state);
		Banner bannerState = (Banner) state;
		assertEquals(Material.WHITE_BANNER, bannerState.getType());

		meta.setBaseColor(DyeColor.RED);
		assertEquals(Material.RED_BANNER, ((BlockStateMeta) meta).getBlockState().getType());
	}

	@Test
	void testAddPattern()
	{
		assertFalse(((BlockStateMeta) meta).hasBlockState());
		meta.addPattern(patterns.getFirst());
		assertTrue(((BlockStateMeta) meta).hasBlockState());
		assertNotNull(meta.getPatterns());
		assertEquals(patterns.getFirst(), meta.getPattern(0));
	}

	@Test
	void testGetPattern()
	{
		assertThrows(IndexOutOfBoundsException.class, () -> meta.getPattern(0));
		meta.setPatterns(patterns);
		assertEquals(patterns.get(0), meta.getPattern(0));
		assertEquals(patterns.get(1), meta.getPattern(1));
		assertEquals(patterns.get(2), meta.getPattern(2));
		assertThrows(IndexOutOfBoundsException.class, () -> meta.getPattern(3));
	}

	@Test
	void testRemovePattern()
	{
		assertThrows(IndexOutOfBoundsException.class, () -> meta.removePattern(0));
		meta.setPatterns(patterns);
		Pattern p0 = meta.removePattern(0);
		assertSame(patterns.get(0), p0);
		assertEquals(patterns.get(1), meta.getPattern(0));
		assertEquals(patterns.get(2), meta.getPattern(1));
		Pattern p2 = meta.removePattern(1);
		assertSame(patterns.get(2), p2);
		assertEquals(patterns.get(1), meta.getPattern(0));

		assertThrows(IndexOutOfBoundsException.class, () -> meta.removePattern(3));
	}

	@Test
	void testSetPattern()
	{
		assertThrows(IndexOutOfBoundsException.class, () -> meta.setPattern(0, patterns.get(0)));
		meta.setPatterns(patterns);
		meta.setPattern(0, patterns.getLast());
		assertSame(patterns.getLast(), meta.getPattern(0));
	}

	@Test
	void testNumberOfPatterns()
	{
		assertEquals(0, meta.numberOfPatterns());
		meta.setPatterns(patterns);
		assertEquals(3, meta.numberOfPatterns());
	}

	@Test
	void testSetBlockState_throwsIfNotBannerType()
	{
		ShieldMetaMock shieldMeta = (ShieldMetaMock) meta;
		var badState = new BeehiveStateMock(Material.BEEHIVE);
		assertThrows(IllegalArgumentException.class, () -> shieldMeta.setBlockState(badState));
	}

	@Test
	void testSetBlockState_throwsIfNull()
	{
		ShieldMetaMock shieldMeta = (ShieldMetaMock) meta;
		assertThrows(NullPointerException.class, () -> shieldMeta.setBlockState(null));
	}

	@Test
	void testSetBlockState()
	{
		ShieldMetaMock shieldMeta = (ShieldMetaMock) meta;
		Banner banner = new BannerStateMock(Material.BLUE_BANNER);
		shieldMeta.setBlockState(banner);
		assertTrue(shieldMeta.hasBlockState());
		assertEquals(banner, shieldMeta.getBlockState());
		assertNotSame(banner, shieldMeta.getBlockState());
	}

	@Test
	void testClearBlockState()
	{
		ShieldMetaMock shieldMeta = (ShieldMetaMock) meta;
		shieldMeta.setBlockState(new BannerStateMock(Material.BLUE_BANNER));
		assertTrue(shieldMeta.hasBlockState());
		shieldMeta.clearBlockState();
		assertFalse(shieldMeta.hasBlockState());
	}

	@Test
	void testSerialization()
	{
		ShieldMetaMock meta = new ShieldMetaMock();
		assertEquals(meta, ShieldMetaMock.deserialize(meta.serialize()));

		meta.setBaseColor(DyeColor.CYAN);
		assertEquals(meta, ShieldMetaMock.deserialize(meta.serialize()));

		meta.addPattern(patterns.get(0));
		meta.addPattern(patterns.get(1));
		meta.addPattern(patterns.get(2));
		ShieldMetaMock meta2 = ShieldMetaMock.deserialize(meta.serialize());
		assertEquals(meta, meta2);

		assertEquals(3, meta2.numberOfPatterns());
		assertEquals(patterns.get(1), meta2.getPattern(1));
		// Patterns are immutable so the instance should be the same.
		assertSame(patterns.get(1), meta2.getPattern(1));
	}

	@Test
	void testCopyConstructor()
	{
		ShieldMetaMock meta2 = new ShieldMetaMock(meta);
		assertEquals(meta, meta2);

		meta.setBaseColor(DyeColor.BLACK);
		assertNotEquals(meta, meta2);

		meta2 = new ShieldMetaMock(meta);
		assertEquals(meta, meta2);
		assertEquals(DyeColor.BLACK, meta2.getBaseColor());
		meta.setPatterns(patterns);
		assertNotEquals(meta, meta2);

		meta2 = new ShieldMetaMock(meta);
		assertEquals(meta, meta2);
		assertEquals(patterns, meta2.getPatterns());
		meta.removePattern(0);
		assertNotEquals(meta, meta2);
		assertEquals(2, meta.numberOfPatterns());
		assertEquals(3, meta2.numberOfPatterns());
	}

}
