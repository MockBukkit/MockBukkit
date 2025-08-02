package org.mockbukkit.mockbukkit.entity;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TropicalFish.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class TropicalFishMockTest
{

	@MockBukkitInject
	private TropicalFishMock tropicalFish;

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
