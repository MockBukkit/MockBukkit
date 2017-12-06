package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

public class PlayerMockTest
{
	private PlayerMock player;

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
		player = new PlayerMock("player", UUID.randomUUID());
	}

	@Test
	public void test()
	{
		assertNotNull(player.getInventory());
	}

}
