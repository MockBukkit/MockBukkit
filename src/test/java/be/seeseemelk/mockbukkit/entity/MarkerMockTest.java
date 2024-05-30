package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MarkerMockTest
{

	private MarkerMock marker;
	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		marker = new MarkerMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testType()
	{
		assertEquals(EntityType.MARKER, marker.getType());
	}

	@Test
	void testCannotAddPassenger()
	{
		assertFalse(marker.addPassenger(new SimpleEntityMock(server)));
		assertEquals(0, marker.getPassengers().size());
	}
}
