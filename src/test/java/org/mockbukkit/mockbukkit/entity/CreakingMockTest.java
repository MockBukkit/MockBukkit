package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

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
	private CreakingMock creaking;

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
		void givenValidPlayer_thenCreakingIsActivated(@MockBukkitInject PlayerMock player)
		{
			assertFalse(creaking.isActive());

			creaking.activate(player);

			assertTrue(creaking.isActive());
		}

	}

	@Test
	void deactivate(@MockBukkitInject PlayerMock player)
	{
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
		void givenChangeInValue_ShouldReturnTheValue(@MockBukkitInject Location location)
		{
			creaking.setHomeLocation(location);

			assertEquals(location, creaking.getHome());
			assertNotSame(location, creaking.getHome());
		}

	}

}
