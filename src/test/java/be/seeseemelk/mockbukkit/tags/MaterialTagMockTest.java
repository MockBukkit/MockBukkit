package be.seeseemelk.mockbukkit.tags;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaterialTagMockTest
{

	private MaterialTagMock tag;

	@BeforeEach
	public void setUp()
	{
		tag = new MaterialTagMock(new NamespacedKey("minecraft", "test"), Material.DIRT, Material.GRASS_BLOCK);
	}

	@Test
	public void getKey()
	{
		assertEquals(new NamespacedKey("minecraft", "test"), tag.getKey());
	}

	@Test
	public void isTagged()
	{
		assertTrue(tag.isTagged(Material.DIRT));
		assertTrue(tag.isTagged(Material.GRASS_BLOCK));
		assertFalse(tag.isTagged(Material.STONE));
	}

	@Test
	public void getValues()
	{
		assertEquals(2, tag.getValues().size());
		assertTrue(tag.getValues().contains(Material.DIRT));
		assertTrue(tag.getValues().contains(Material.GRASS_BLOCK));
	}

}
