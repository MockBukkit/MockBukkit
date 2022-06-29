package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.entity.EntityMock;
import be.seeseemelk.mockbukkit.entity.SimpleEntityMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class RickRollMockTest
{

	private ServerMock server;
	private RickRollMock rickRoll;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testRickRoll()
	{
		rickRoll = new RickRollMock();
		EntityMock entity = new SimpleEntityMock(server, UUID.randomUUID());
		rickRoll.rickRoll(entity);
		Assertions.assertTrue(rickRoll.isRickRolled(entity));
	}

}
