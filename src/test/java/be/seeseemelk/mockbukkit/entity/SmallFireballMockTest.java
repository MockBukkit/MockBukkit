package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SmallFireball;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SmallFireballMockTest
{

	private SmallFireball smallFireball;

	@BeforeEach
	public void setUp()
	{
		ServerMock server = MockBukkit.mock();
		smallFireball = new SmallFireballMock(server, UUID.randomUUID());
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SMALL_FIREBALL, smallFireball.getType());
	}

}
