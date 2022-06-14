package be.seeseemelk.mockbukkit;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChunkSnapshotMockTest
{

	private ServerMock server;
	private World world;
	private Chunk chunk;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock(Material.GRASS, 0, 319, 4);
		chunk = world.getChunkAt(1, 1);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getX()
	{
		assertEquals(1, chunk.getChunkSnapshot().getX());
	}

	@Test
	void getZ()
	{
		assertEquals(1, chunk.getChunkSnapshot().getZ());
	}

	@Test
	void getWorldName()
	{
		assertEquals(world.getName(), chunk.getChunkSnapshot().getWorldName());
	}

	@Test
	void getBlockType()
	{
		assertEquals(Material.GRASS, chunk.getChunkSnapshot().getBlockType(0, 1, 0));
		assertEquals(Material.AIR, chunk.getChunkSnapshot().getBlockType(0, 10, 0));
	}

	@Test
	void getFullTime()
	{
		long time = world.getFullTime();
		assertEquals(time, chunk.getChunkSnapshot().getCaptureFullTime());
	}

	@Test
	void isSectionEmpty()
	{
		assertFalse(chunk.getChunkSnapshot().isSectionEmpty(0));
		assertTrue(chunk.getChunkSnapshot().isSectionEmpty(1));
	}

}
