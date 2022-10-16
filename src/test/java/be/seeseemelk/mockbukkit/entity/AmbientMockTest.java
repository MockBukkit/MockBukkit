package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertSame;

class AmbientMockTest
{

	private ServerMock serverMock;
	private AmbientMock ambient;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();
		ambient = new AmbientMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testToString()
	{
		assertSame("AmbientMock", ambient.toString());
	}

}
