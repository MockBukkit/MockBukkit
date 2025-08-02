package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class RabbitMockTest
{

	@MockBukkitInject
	private RabbitMock rabbit;

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
