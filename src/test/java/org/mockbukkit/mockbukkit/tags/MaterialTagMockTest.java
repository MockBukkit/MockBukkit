package org.mockbukkit.mockbukkit.tags;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTagMockTest
{

	private MaterialTagMock tag;

	@BeforeEach
	void setUp()
	{
		tag = new MaterialTagMock(new NamespacedKey("minecraft", "test"), Material.DIRT, Material.GRASS_BLOCK);
	}

	@Test
	void getKey()
	{
		assertEquals(new NamespacedKey("minecraft", "test"), tag.getKey());
	}

	@Test
	void isTagged()
	{
		assertTrue(tag.isTagged(Material.DIRT));
		assertTrue(tag.isTagged(Material.GRASS_BLOCK));
		assertFalse(tag.isTagged(Material.STONE));
	}

	@Test
	void getValues()
	{
		assertEquals(2, tag.getValues().size());
		assertTrue(tag.getValues().contains(Material.DIRT));
		assertTrue(tag.getValues().contains(Material.GRASS_BLOCK));
	}

}
