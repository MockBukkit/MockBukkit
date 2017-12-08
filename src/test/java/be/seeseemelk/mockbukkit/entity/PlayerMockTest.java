package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

public class PlayerMockTest
{
	private UUID uuid;
	private PlayerMock player;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		uuid = UUID.randomUUID();
		player = new PlayerMock("player", uuid);
	}

	@Test
	public void getInventory_Default_NotNull()
	{
		assertNotNull(player.getInventory());
	}

	@Test
	public void getName_Default_CorrectName()
	{
		assertEquals("player", player.getName());
	}
	
	@Test
	public void getUniqueIdDefault_CorrectUuid()
	{
		assertEquals(uuid, player.getUniqueId());
	}
	
	@Test
	public void sendMessage_Default_nextMessageReturnsMessages()
	{
		player.sendMessage("hello");
		player.sendMessage(new String[]{"my", "world"});
		assertEquals("hello", player.nextMessage());
		assertEquals("my", player.nextMessage());
		assertEquals("world", player.nextMessage());
	}
	
	@Test
	public void equals_SameUUID_Equal()
	{
		PlayerMock player2 = new PlayerMock("player", uuid);
		assertTrue("Two player objects are not equal", player.equals(player2));
	}
	
	@Test
	public void equals_DifferentUUID_Different()
	{
		PlayerMock player2 = new PlayerMock("differentPlayer", UUID.randomUUID());
		assertFalse("Two player objects detected as equal", player.equals(player2));
	}
}


















