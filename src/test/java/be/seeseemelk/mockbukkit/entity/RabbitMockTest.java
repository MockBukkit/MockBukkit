package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RabbitMockTest
{

	private RabbitMock rabbit;

	@BeforeEach
	public void setUp()
	{
		ServerMock server = MockBukkit.mock();
		rabbit = new RabbitMock(server, UUID.randomUUID());
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void testGetRabbitType()
	{
		assertEquals(RabbitMock.Type.BLACK, rabbit.getRabbitType());
	}

	@Test
	public void testSetRabbitType()
	{
		rabbit.setRabbitType(RabbitMock.Type.BLACK_AND_WHITE);
		assertEquals(RabbitMock.Type.BLACK_AND_WHITE, rabbit.getRabbitType());
	}

	@Test
	public void testGetMoreCarrotTicks()
	{
		assertEquals(0, rabbit.getMoreCarrotTicks());
	}

	@Test
	public void testSetMoreCarrotTicks()
	{
		rabbit.setMoreCarrotTicks(42);
		assertEquals(42, rabbit.getMoreCarrotTicks());
	}

	@Test
	public void testEntityType()
	{
		assertEquals(EntityType.RABBIT, rabbit.getType());
	}

}
