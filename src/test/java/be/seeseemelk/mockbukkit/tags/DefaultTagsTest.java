package be.seeseemelk.mockbukkit.tags;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

/**
 * These are just some example tests for common tags to ensure their normal functionality has not been disturbed.
 *
 * @author TheBusyBiscuit
 *
 */
class DefaultTagsTest
{

	@BeforeEach
	public void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

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
