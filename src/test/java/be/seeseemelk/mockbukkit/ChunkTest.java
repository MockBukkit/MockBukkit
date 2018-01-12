package be.seeseemelk.mockbukkit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChunkTest
{
	private ServerMock server;
	private WorldMock world;

	@Before
	public void setUp() throws Exception
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unload();
	}

	@Test
	public void getX_AnyValue_ExactValue()
	{
		assertEquals(10, world.getChunkAt(10, 20).getX());
	}
	
	@Test
	public void getZ_AnyValue_Exact_Value()
	{
		assertEquals(20, world.getChunkAt(10, 20).getZ());
	}
	
	@Test
	public void getWorld_AnyChunkFromWorld_ExactWorldReference()
	{
		assertSame(world, world.getChunkAt(0, 0).getWorld());
	}
	
	@Test
	public void isLoaded_JustCreated_True()
	{
		assertTrue(world.getChunkAt(0, 0).isLoaded());
	}
	
	@Test
	public void isLoaded_AfterUnload_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertTrue(chunk.unload());
		assertFalse(chunk.isLoaded());
	}
	
	@Test
	public void isLoaded_AfterLoad_True()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		chunk.unload();
		assertTrue(chunk.load());
		assertTrue(chunk.isLoaded());
	}
	
	@Test
	public void equals_DifferentChunk_False()
	{
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 1);
		assertFalse(chunk1.equals(chunk2));
	}
	
	@Test
	public void equals_SameChunk_True()
	{
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 0);
		assertTrue(chunk1.equals(chunk2));
		assertEquals(chunk1.hashCode(), chunk2.hashCode());
	}
	
	@Test
	public void equals_Null_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertFalse(chunk.equals(null));
	}
	
	@Test
	public void equals_DifferentClass_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		Object obj = Runtime.getRuntime();
		assertFalse(chunk.equals(obj));
	}
	

}
