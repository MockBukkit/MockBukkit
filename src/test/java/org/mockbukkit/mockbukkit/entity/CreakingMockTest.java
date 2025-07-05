package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CreakingMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private CreakingMock creaking;

	@BeforeEach
	void setup()
	{
		creaking = new CreakingMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.CREAKING, creaking.getType());
	}

	@Nested
	class Activate
	{

		@Test
		void givenNullPlayer_thenIllegalArgumentException()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> creaking.activate(null));
			assertEquals("player cannot be null", e.getMessage());
		}

		@Test
		void givenValidPlayer_thenCreakingIsActivated()
		{
			assertFalse(creaking.isActive());

			PlayerMock player = server.addPlayer();
			creaking.activate(player);

			assertTrue(creaking.isActive());
		}

	}

	@Test
	void deactivate()
	{
		PlayerMock player = server.addPlayer();
		creaking.activate(player);
		assertTrue(creaking.isActive());

		creaking.deactivate();

		assertFalse(creaking.isActive());
	}

	@Nested
	class GetHome
	{

		@Test
		void givenDefaultValue_ShouldReturnNull()
		{
			assertNull(creaking.getHome());
		}

		@Test
		void givenChangeInValue_ShouldReturnTheValue()
		{
			World world = server.addSimpleWorld("world");
			Location location = new Location(world, 0, 0, 0);
			creaking.setHomeLocation(location);

			assertEquals(location, creaking.getHome());
			assertNotSame(location, creaking.getHome());
		}

	}

}
