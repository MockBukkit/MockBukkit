package be.seeseemelk.mockbukkit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChunkTest
{
	private ServerMock server;
	private WorldMock world;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
	}

	@AfterEach
	public void tearDown()
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

}
