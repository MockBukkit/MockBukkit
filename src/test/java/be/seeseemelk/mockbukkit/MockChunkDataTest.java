package be.seeseemelk.mockbukkit;

import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.data.type.AmethystCluster;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class MockChunkDataTest
{

	private ServerMock server;
	private WorldMock world;
	private ChunkGenerator.ChunkData chunkData;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("dummy");
		chunkData = server.createChunkData(world);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void test_set_and_get()
	{
		WorldMock dummy = server.addSimpleWorld("dummy");
		ChunkGenerator.ChunkData data = server.createChunkData(dummy);

		data.setBlock(0, 0, 0, Material.STONE);
		assertEquals(Material.STONE, data.getType(0, 0, 0));
	}

	@Test
	void setBlock_NullMaterial_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> chunkData.setBlock(0, 0, 0, (Material) null));
	}

	@Test
	void setBlock_MaterialWithBlockDataMockSubclass()
	{
		chunkData.setBlock(0, 0, 0, Material.BLUE_BED);
		assertInstanceOf(Bed.class, chunkData.getBlockData(0, 0, 0));
	}

	@Test
	void setBlock_NullMaterialData_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> chunkData.setBlock(0, 0, 0, (MaterialData) null));
	}

	@Test
	void setBlock_MaterialDataWithBlockDataMockSubclass()
	{
		MaterialData materialData = new MaterialData(Material.AMETHYST_CLUSTER);
		chunkData.setBlock(0, 0, 0, materialData);
		assertInstanceOf(AmethystCluster.class, chunkData.getBlockData(0, 0, 0));
	}

	@Test
	void test_set_out_of_bounds()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> chunkData.setBlock(33, 1000, 33, Material.STONE));
	}

	@Test
	void test_get_out_of_bounds()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> chunkData.getType(33, 1000, 33));
	}

	@Test
	void test_neg_min_height()
	{
		world = new WorldMock(Material.GRASS_BLOCK, -60, 256, 70);
		chunkData = server.createChunkData(world);

		chunkData.setBlock(0, -40, 0, Material.OBSIDIAN);
		assertEquals(Material.OBSIDIAN, chunkData.getType(0, -40, 0));
	}

	@Test
	void test_pos_min_height()
	{
		world = new WorldMock(Material.GRASS_BLOCK, 60, 256, 70);
		chunkData = server.createChunkData(world);

		chunkData.setBlock(0, 80, 0, Material.OBSIDIAN);
		assertEquals(Material.OBSIDIAN, chunkData.getType(0, 80, 0));
	}

	@Test
	void getBiome()
	{
		world = new WorldMock(Material.GRASS_BLOCK, 60, 256, 70);
		chunkData = server.createChunkData(world);

		Biome worldBiome = world.getBiome(0, 0, 0);
		assertNotNull(worldBiome);

		Biome chunkBiome = chunkData.getBiome(0, 0, 0);
		assertNotNull(chunkBiome);

		assertEquals(worldBiome, chunkBiome);
	}

	@Test
	void setRegion_NullMaterial_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> chunkData.setRegion(0, 0, 0, 1, 1, 1, (Material) null));
	}

	@Test
	void setRegion_MaterialWithBlockDataMockSubclass()
	{
		int min = 0;
		int max = 2;
		chunkData.setRegion(min, min, min, max, max, max, Material.BIRCH_STAIRS);
		assertInstanceOfForVolume(Stairs.class, min, max);
	}

	@Test
	void setRegion_NullMaterialData_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class,
				() -> chunkData.setRegion(0, 0, 0, 1, 1, 1, (MaterialData) null));
	}

	@Test
	void setRegion_MaterialDataWithBlockDataMockSubclass()
	{
		int min = 0;
		int max = 2;
		MaterialData materialData = new MaterialData(Material.WARPED_TRAPDOOR);
		chunkData.setRegion(min, min, min, max, max, max, materialData);
		assertInstanceOfForVolume(TrapDoor.class, min, max);
	}

	private <T> void assertInstanceOfForVolume(Class<T> clazz, int min, int max)
	{
		for (int x = min; x < max; x++)
		{
			for (int y = min; y < max; y++)
			{
				for (int z = min; z < max; z++)
				{
					assertInstanceOf(clazz, chunkData.getBlockData(x, y, z));
				}
			}
		}
	}

}
