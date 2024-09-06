package org.mockbukkit.mockbukkit.tags;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * These are just some example tests for common tags to ensure their normal functionality has not been disturbed.
 *
 * @author TheBusyBiscuit
 */
@ExtendWith(MockBukkitExtension.class)
class DefaultTagsTest
{

	@Test
	void testSaplings()
	{
		assertTrue(Tag.SAPLINGS.isTagged(Material.OAK_SAPLING));
		assertTrue(Tag.SAPLINGS.isTagged(Material.DARK_OAK_SAPLING));
		assertTrue(Tag.SAPLINGS.isTagged(Material.JUNGLE_SAPLING));
	}

	@Test
	void testLogs()
	{
		assertTrue(Tag.LOGS.isTagged(Material.ACACIA_LOG));
		assertTrue(Tag.LOGS.isTagged(Material.BIRCH_LOG));
		assertTrue(Tag.LOGS.isTagged(Material.CRIMSON_HYPHAE));
		assertTrue(Tag.LOGS.isTagged(Material.WARPED_HYPHAE));
	}

	@Test
	void testIce()
	{
		assertTrue(Tag.ICE.isTagged(Material.ICE));
		assertTrue(Tag.ICE.isTagged(Material.BLUE_ICE));
	}

	@Test
	void testBoats()
	{
		assertTrue(Tag.ITEMS_BOATS.isTagged(Material.OAK_BOAT));
		assertTrue(Tag.ITEMS_BOATS.isTagged(Material.BIRCH_BOAT));
		assertTrue(Tag.ITEMS_BOATS.isTagged(Material.DARK_OAK_BOAT));
	}

}
