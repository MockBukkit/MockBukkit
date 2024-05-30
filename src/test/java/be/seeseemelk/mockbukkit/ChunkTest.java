package be.seeseemelk.mockbukkit;

import org.bukkit.Chunk.LoadLevel;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChunkTest
{

	private ServerMock server;
	private WorldMock world;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getX_AnyValue_ExactValue()
	{
		assertEquals(10, world.getChunkAt(10, 20).getX());
	}

	@Test
	void getZ_AnyValue_Exact_Value()
	{
		assertEquals(20, world.getChunkAt(10, 20).getZ());
	}

	@Test
	void getChunkKey()
	{
		long chunkKey = 10L | (20L << 32); // x = 10, y = 20
		ChunkMock chunk = world.getChunkAt(10, 20);
		assertEquals(chunkKey, chunk.getChunkKey());
		assertEquals(chunk, world.getChunkAt(chunkKey));
	}

	@Test
	void getWorld_AnyChunkFromWorld_ExactWorldReference()
	{
		assertSame(world, world.getChunkAt(0, 0).getWorld());
	}

	@Test
	void getBlock_CorrectBlock()
	{
		world.getBlockAt(16 + 8, 0, 16 + 6).setType(Material.STONE);
		ChunkMock chunk = world.getChunkAt(1, 1);

		Material type = chunk.getBlock(8, 0, 6).getType();

		assertEquals(Material.STONE, type);
	}

	@Test
	void getBlock_Coordinate_CorrectBlock()
	{
		world.getBlockAt(16 + 8, 0, 16 + 6).setType(Material.STONE);
		ChunkMock chunk = world.getChunkAt(1, 1);

		Material type = chunk.getBlock(new Coordinate(8, 0, 6)).getType();

		assertEquals(Material.STONE, type);
	}

	@Test
	void getBlocks_CorrectSize()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);

		// w * w * h
		assertEquals(32768, chunk.getBlocks().size());
	}

	@Test
	void getBlocks_CorrectBlocks()
	{
		world.getBlockAt(16, 0, 16).setType(Material.STONE);
		world.getBlockAt(16, 0, 17).setType(Material.STONE_BRICKS);
		ChunkMock chunk = world.getChunkAt(1, 1);

		Block block1 = chunk.getBlocks().get(0);
		Block block2 = chunk.getBlocks().get(1);

		assertEquals(Material.STONE, block1.getType());
		assertEquals(Material.STONE_BRICKS, block2.getType());
	}

	@Test
	void isLoaded_JustCreated_True()
	{
		assertTrue(world.getChunkAt(0, 0).isLoaded());
	}

	@Test
	void isLoaded_AfterUnload_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertTrue(chunk.unload());
		assertFalse(chunk.isLoaded());
	}

	@Test
	void isLoaded_AfterLoad_True()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		chunk.unload();
		assertTrue(chunk.load());
		assertTrue(chunk.isLoaded());
	}

	@Test
	void isForceLoaded_JustCreated_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertFalse(chunk.isForceLoaded());
	}

	@Test
	void isForceLoaded_AfterSet_True()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		chunk.setForceLoaded(true);
		assertTrue(chunk.isForceLoaded());
	}

	@Test
	void equals_DifferentChunk_False()
	{
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 1);
		assertNotEquals(chunk1, chunk2);
	}

	@Test
	void equals_SameChunk_True()
	{
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 0);
		assertEquals(chunk1, chunk2);
		assertEquals(chunk1.hashCode(), chunk2.hashCode());
	}

	@Test
	void equals_Null_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertNotEquals(null, chunk);
	}

	@Test
	void equals_DifferentClass_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		Object obj = new Object();
		assertNotEquals(chunk, obj);
	}

	@Test
	void getEntities_EmptyChunk_EmptyList()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertEquals(0, chunk.getEntities().length);
	}

	@Test
	void getEntities_OneEntity_OneEntity()
	{
		ChunkMock chunk = world.getChunkAt(10 >> 4, 20 >> 4);
		Entity entity = world.spawn(new Location(world, 10, 5, 20), Zombie.class);
		assertEquals(1, chunk.getEntities().length);
		assertEquals(entity, chunk.getEntities()[0]);
	}

	@Test
	void getEntities_TwoEntities_TwoEntities_ExcludesOutOfChunk()
	{
		ChunkMock chunk = world.getChunkAt(10 >> 4, 20 >> 4);
		Entity entity1 = world.spawn(new Location(world, 10, 5, 20), Zombie.class);
		Entity entity2 = world.spawn(new Location(world, 10, 5, 20), Zombie.class);
		Zombie entity3 = world.spawn(new Location(world, 60, 5, 20), Zombie.class);

		List<Entity> entities = List.of(chunk.getEntities());

		assertEquals(2, entities.size());
		assertTrue(entities.contains(entity1));
		assertTrue(entities.contains(entity2));
		assertFalse(entities.contains(entity3));
	}

	@Test
	void getLoadLevel()
	{
		ChunkMock chunk = world.getChunkAt(0, 1);
		assertEquals(LoadLevel.ENTITY_TICKING, chunk.getLoadLevel());
		chunk.unload();
		assertEquals(LoadLevel.UNLOADED, chunk.getLoadLevel());
		chunk.load();
		assertEquals(LoadLevel.ENTITY_TICKING, chunk.getLoadLevel());
	}

	@Test
	void contains_BlockData_True()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		Block block = chunk.getBlock(0, 1, 0);
		assertTrue(chunk.contains(block.getBlockData()));
	}

	@Test
	void contains_BlockData_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		Block block = chunk.getBlock(0, 0, 0);
		assertTrue(chunk.contains(block.getBlockData()));
	}

	@Test
	void contains_Biome_True()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		Block block = chunk.getBlock(0, 1, 0);
		block.setBiome(Biome.BADLANDS);
		assertTrue(chunk.contains(Biome.BADLANDS));
	}

	@Test
	void contains_Biome_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		Block block = chunk.getBlock(0, 0, 0);
		assertFalse(chunk.contains(Biome.BADLANDS));
	}

	@Test
	void setSlimeChunk()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertFalse(chunk.isSlimeChunk());
		chunk.setSlimeChunk(true);
		assertTrue(chunk.isSlimeChunk());
		chunk.setSlimeChunk(false);
		assertFalse(chunk.isSlimeChunk());
	}

}
