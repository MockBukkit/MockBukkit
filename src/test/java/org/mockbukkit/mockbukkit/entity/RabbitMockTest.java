package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RabbitMockTest
{

	private RabbitMock rabbit;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		rabbit = new RabbitMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetRabbitType()
	{
		assertEquals(RabbitMock.Type.BLACK, rabbit.getRabbitType());
	}

	@Test
	void testSetRabbitType()
	{
		rabbit.setRabbitType(RabbitMock.Type.BLACK_AND_WHITE);
		assertEquals(RabbitMock.Type.BLACK_AND_WHITE, rabbit.getRabbitType());
	}

	@Test
	void testGetMoreCarrotTicks()
	{
		assertEquals(0, rabbit.getMoreCarrotTicks());
	}

	@Test
	void testSetMoreCarrotTicks()
	{
		rabbit.setMoreCarrotTicks(42);
		assertEquals(42, rabbit.getMoreCarrotTicks());
	}

	@Test
	void testEntityType()
	{
		assertEquals(EntityType.RABBIT, rabbit.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultRabbit()
	{
		assertEquals(0.425D, rabbit.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyRabbit()
	{
		rabbit.setBaby();
		assertEquals(0.2125D, rabbit.getEyeHeight());
	}

}
