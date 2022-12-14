package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChunkTest
{

	private ServerMock server;
	private WorldMock world;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		world = server.addSimpleWorld("world");
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
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
	public void isSlimeChunk_AfterSetSlimeChunkTrue_True()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		chunk.setSlimeChunk(true);
		assertTrue(chunk.isSlimeChunk());
	}

	@Test
	public void isSlimeChunk_AfterSetSlimeChunkFalse_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		chunk.setSlimeChunk(false);
		assertFalse(chunk.isSlimeChunk());
	}

	@Test
	public void isSlimeChunk_AtPlayerLocation_AfterSetSlimeChunkTrue_True()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		chunk.setSlimeChunk(true);
		PlayerMock player = server.addPlayer();
		player.setLocation(new Location(world, 0, 0, 0));
		assertTrue(player.getLocation().getChunk().isSlimeChunk());
	}

	@Test
	public void isSlimeChunk_AtPlayerLocation_AfterSetSlimeChunkFalse_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		chunk.setSlimeChunk(false);
		PlayerMock player = server.addPlayer();
		player.setLocation(new Location(world, 0, 0, 0));
		assertFalse(player.getLocation().getChunk().isSlimeChunk());
	}

	@Test
	public void equals_DifferentChunk_False()
	{
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 1);
		assertNotEquals(chunk1, chunk2);
	}

	@Test
	public void equals_SameChunk_True()
	{
		ChunkMock chunk1 = world.getChunkAt(0, 0);
		ChunkMock chunk2 = world.getChunkAt(0, 0);
		assertEquals(chunk1, chunk2);
		assertEquals(chunk1.hashCode(), chunk2.hashCode());
	}

	@Test
	public void equals_Null_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		assertNotEquals(chunk, null);
	}

	@Test
	public void equals_DifferentClass_False()
	{
		ChunkMock chunk = world.getChunkAt(0, 0);
		Object obj = new Object();
		assertNotEquals(chunk, obj);
	}

}
