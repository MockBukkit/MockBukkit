package org.mockbukkit.mockbukkit.world;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.block.data.AmethystClusterDataMock;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ChunkSnapshotMockTest
{

	private World world;
	private Chunk chunk;

	@BeforeEach
	void setUp()
	{
		world = new WorldMock(Material.GRASS_BLOCK, 0, 319, 4);
		chunk = world.getChunkAt(1, 1);
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
		assertEquals(Material.GRASS_BLOCK, chunk.getChunkSnapshot().getBlockType(0, 1, 0));
		assertEquals(Material.AIR, chunk.getChunkSnapshot().getBlockType(0, 10, 0));
	}

	@Test
	void getBlockData()
	{
		assertEquals(Material.GRASS_BLOCK, chunk.getChunkSnapshot().getBlockData(0, 1, 0).getMaterial());
		assertEquals(Material.AIR, chunk.getChunkSnapshot().getBlockData(0, 10, 0).getMaterial());
	}

	@Test
	void getBlockData_PreservesData()
	{
		AmethystClusterDataMock blockData = (AmethystClusterDataMock) Bukkit.createBlockData(Material.AMETHYST_CLUSTER);
		blockData.setWaterlogged(true);
		blockData.setFacing(BlockFace.SOUTH);
		chunk.getBlock(0, 1, 0).setBlockData(blockData);

		AmethystClusterDataMock snapshotData = (AmethystClusterDataMock) chunk.getChunkSnapshot().getBlockData(0, 1, 0);

		assertEquals(Material.AMETHYST_CLUSTER, snapshotData.getMaterial());
		assertTrue(snapshotData.isWaterlogged());
		assertEquals(BlockFace.SOUTH, snapshotData.getFacing());
	}

	@Test
	void containsAllBlocks()
	{
		ChunkSnapshot snapshot = chunk.getChunkSnapshot();
		for (int x = 0; x < 16; x++)
		{
			for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++)
			{
				for (int z = 0; z < 16; z++)
				{
					assertNotNull(snapshot.getBlockData(x, y, z));
				}
			}
		}
	}

	@Test
	void contains_BlockExists_True()
	{
		assertTrue(chunk.getChunkSnapshot().contains(Bukkit.createBlockData(Material.GRASS_BLOCK)));
	}

	@Test
	void contains_BlockDoesntExist_False()
	{
		assertFalse(chunk.getChunkSnapshot().contains(Bukkit.createBlockData(Material.DIAMOND_BLOCK)));
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

	@Test
	void getBiome_DoesntIncludeBiome_ThrowsException()
	{
		assertThrowsExactly(IllegalStateException.class, () -> chunk.getChunkSnapshot().getBiome(0, 0, 0));
	}

	@Test
	void getBiome()
	{
		// Chunk is at world 1, 1. Chunk 0, 0 = world 16, 16
		world.setBiome(16, 0, 16, Biome.BADLANDS);

		assertEquals(Biome.BADLANDS, chunk.getChunkSnapshot(false, true, false).getBiome(0, 0));
		assertEquals(Biome.BADLANDS, chunk.getChunkSnapshot(false, true, false).getBiome(0, 0, 0));
	}

	@Test
	void getBiome_NoBiomes_ThrowsException()
	{
		ChunkSnapshot snapshot = chunk.getChunkSnapshot(false, false, false);

		assertThrows(IllegalStateException.class, () -> snapshot.getBiome(0, 0, 0));
	}

	@Test
	void getBiome_EitherBiome_ReturnsBiomes()
	{
		// Chunk is at world 1, 1. Chunk 0, 0 = world 16, 16
		world.setBiome(16, 0, 16, Biome.BADLANDS);

		assertEquals(Biome.BADLANDS, chunk.getChunkSnapshot(false, true, false).getBiome(0, 0, 0));
		assertEquals(Biome.BADLANDS, chunk.getChunkSnapshot(false, false, true).getBiome(0, 0, 0));
	}

}
