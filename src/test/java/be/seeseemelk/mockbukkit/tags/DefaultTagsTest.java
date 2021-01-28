package be.seeseemelk.mockbukkit.tags;

import static org.junit.Assert.assertTrue;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

/**
 * These are just some example tests for common tags to ensure their normal functionality has not been disturbed.
 *
 * @author TheBusyBiscuit
 *
 */
public class DefaultTagsTest
{

	@Before
	public void setUp()
	{
		MockBukkit.mock();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void testSaplings()
	{
		assertTrue(Tag.SAPLINGS.isTagged(Material.OAK_SAPLING));
		assertTrue(Tag.SAPLINGS.isTagged(Material.DARK_OAK_SAPLING));
		assertTrue(Tag.SAPLINGS.isTagged(Material.JUNGLE_SAPLING));
	}

	@Test
	public void testLogs()
	{
		assertTrue(Tag.LOGS.isTagged(Material.ACACIA_LOG));
		assertTrue(Tag.LOGS.isTagged(Material.BIRCH_LOG));
		assertTrue(Tag.LOGS.isTagged(Material.CRIMSON_HYPHAE));
		assertTrue(Tag.LOGS.isTagged(Material.WARPED_HYPHAE));
	}

	@Test
	public void testIce()
	{
		assertTrue(Tag.ICE.isTagged(Material.ICE));
		assertTrue(Tag.ICE.isTagged(Material.BLUE_ICE));
	}

	@Test
	public void testBoats()
	{
		assertTrue(Tag.ITEMS_BOATS.isTagged(Material.OAK_BOAT));
		assertTrue(Tag.ITEMS_BOATS.isTagged(Material.BIRCH_BOAT));
		assertTrue(Tag.ITEMS_BOATS.isTagged(Material.DARK_OAK_BOAT));
	}

}
