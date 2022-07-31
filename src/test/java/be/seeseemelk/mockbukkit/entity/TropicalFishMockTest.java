package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish.Pattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TropicalFishMockTest
{

	private TropicalFishMock tropicalFish;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		tropicalFish = new TropicalFishMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.TROPICAL_FISH, tropicalFish.getType());
	}

	@Test
	void testGetBaseBucketItem()
	{
		assertEquals(Material.TROPICAL_FISH_BUCKET,tropicalFish.getBaseBucketItem().getType());
	}

	@Test
	void testGetPatternColor()
	{
		assertTrue(Arrays.stream(DyeColor.values()).anyMatch(dyeColor -> tropicalFish.getPatternColor().equals(dyeColor)));
	}

	@Test
	void testSetPatternColor()
	{
		tropicalFish.setPatternColor(DyeColor.RED);
		assertEquals(DyeColor.RED,tropicalFish.getPatternColor());
	}

	@Test
	void testGetBodyColor()
	{
		assertTrue(Arrays.stream(DyeColor.values()).anyMatch(dyeColor -> tropicalFish.getBodyColor().equals(dyeColor)));
	}

	@Test
	void testSetBodyColor()
	{
		tropicalFish.setBodyColor(DyeColor.RED);
		assertEquals(DyeColor.RED,tropicalFish.getBodyColor());
	}

	@Test
	void testGetPattern()
	{
		assertTrue(Arrays.stream(Pattern.values()).anyMatch(pattern -> tropicalFish.getPattern().equals(pattern)));
	}

	@Test
	void testSetPattern()
	{
		tropicalFish.setPattern(Pattern.BETTY);
		assertEquals(Pattern.BETTY, tropicalFish.getPattern());
	}
}
