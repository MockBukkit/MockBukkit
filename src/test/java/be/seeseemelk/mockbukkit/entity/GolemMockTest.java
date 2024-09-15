package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GolemMockTest
{
	private ServerMock server;
	private GolemMock golem;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		golem = new GolemMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getAmbientSound()
	{
		assertNull(golem.getAmbientSound());
	}

	@Test
	void getHurtSound()
	{
		assertNull(golem.getHurtSound());
	}

	@Test
	void getDeathSound()
	{
		assertNull(golem.getDeathSound());
	}

}
