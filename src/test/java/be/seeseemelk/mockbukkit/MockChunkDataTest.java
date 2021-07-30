package be.seeseemelk.mockbukkit;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MockChunkDataTest
{
	private ServerMock server;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void test_set_and_get()
	{
		WorldMock dummy = server.addSimpleWorld("dummy");
		ChunkGenerator.ChunkData data = server.createChunkData(dummy);

		data.setBlock(0, 0, 0, Material.STONE);
		Assertions.assertEquals(Material.STONE, data.getType(0, 0, 0));
	}

	@Test
	void test_set_out_of_bounds()
	{
		WorldMock dummy = server.addSimpleWorld("dummy");
		ChunkGenerator.ChunkData data = server.createChunkData(dummy);

		Assertions.assertDoesNotThrow(() -> data.setBlock(33, 1000, 33, Material.STONE));
		Assertions.assertEquals(Material.AIR, data.getType(33, 1000, 33));
	}

	@Test
	void test_get_out_of_bounds()
	{
		WorldMock dummy = server.addSimpleWorld("dummy");
		ChunkGenerator.ChunkData data = server.createChunkData(dummy);

		Assertions.assertEquals(Material.AIR, data.getType(33, 1000, 33));
	}

	@Test
	void test_neg_min_height()
	{
		MinHeightMock dummy = new MinHeightMock(-60, 256);
		ChunkGenerator.ChunkData data = server.createChunkData(dummy);

		data.setBlock(0, -40, 0, Material.OBSIDIAN);
		Assertions.assertEquals(Material.OBSIDIAN, data.getType(0, -40, 0));
	}

	@Test
	void test_pos_min_height()
	{
		MinHeightMock dummy = new MinHeightMock(60, 256);
		ChunkGenerator.ChunkData data = server.createChunkData(dummy);

		data.setBlock(0, 80, 0, Material.OBSIDIAN);
		Assertions.assertEquals(Material.OBSIDIAN, data.getType(0, 80, 0));
	}

	private static final class MinHeightMock extends WorldMock
	{
		private final int minHeight;
		private final int maxHeight;

		private MinHeightMock(int minHeight, int maxHeight)
		{
			super(Material.GRASS, 2);
			this.minHeight = minHeight;
			this.maxHeight = maxHeight;
		}

		@Override
		public int getMinHeight()
		{
			return minHeight;
		}

		@Override
		public int getMaxHeight()
		{
			return maxHeight;
		}
	}
}
