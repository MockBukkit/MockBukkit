package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish.Pattern;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		assertEquals(Material.TROPICAL_FISH_BUCKET, tropicalFish.getBaseBucketItem().getType());
	}

	@Test
	void testGetPatternColor()
	{
		assertNotNull(tropicalFish.getPatternColor());
	}

	@Test
	void testSetPatternColor()
	{
		tropicalFish.setPatternColor(DyeColor.RED);
		assertEquals(DyeColor.RED, tropicalFish.getPatternColor());
	}

	@Test
	void testGetBodyColor()
	{
		assertNotNull(tropicalFish.getBodyColor());
	}

	@Test
	void testSetBodyColor()
	{
		tropicalFish.setBodyColor(DyeColor.RED);
		assertEquals(DyeColor.RED, tropicalFish.getBodyColor());
	}

	@Test
	void testGetPattern()
	{
		assertNotNull(tropicalFish.getPattern());
	}

	@Test
	void testSetPattern()
	{
		tropicalFish.setPattern(Pattern.BETTY);
		assertEquals(Pattern.BETTY, tropicalFish.getPattern());
	}

}
