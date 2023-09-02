package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.EntityMock;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
		assertNotEquals(chunk, null);
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
		world.spawn(new Location(world, 60, 5, 20), Zombie.class);
		assertEquals(2, chunk.getEntities().length);
		assertEquals(entity1, chunk.getEntities()[0]);
		assertEquals(entity2, chunk.getEntities()[1]);
	}

}
