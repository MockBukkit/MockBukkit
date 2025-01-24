package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Strider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class StriderMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private Strider strider;

	@BeforeEach
	void setup()
	{
		strider = new StriderMock(server, UUID.randomUUID());
	}

	@Nested
	class IsShivering
	{
		@Test
		void withDefaultValue()
		{
			assertFalse(strider.isShivering());
		}

		@Test
		void givenColdStrider()
		{
			strider.setShivering(true);
			assertTrue(strider.isShivering());
		}

		@Test
		void givenNormalStrider()
		{
			strider.setShivering(false);
			assertFalse(strider.isShivering());
		}

	}

	@Nested
	class HasSaddle
	{
		@Test
		void withDefaultValue()
		{
			assertFalse(strider.hasSaddle());
		}

		@Test
		void withoutSaddle()
		{
			strider.setSaddle(true);
			assertTrue(strider.hasSaddle());
		}

		@Test
		void withSaddle()
		{
			strider.setSaddle(false);
			assertFalse(strider.hasSaddle());
		}

	}

	@Nested
	class GetBoostTicks
	{

		@Test
		void withDefaultValue()
		{
			assertEquals(0, strider.getBoostTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
		void withValidBoostValue(int value)
		{
			assertDoesNotThrow(() -> strider.setBoostTicks(value));
			assertEquals(value, strider.getBoostTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = {-10, -9, -8, -7, -6, -5, -4, -3, -2, -1})
		void withInvalidBoostValue(int value)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> strider.setBoostTicks(value));
			assertEquals("ticks must be >= 0", e.getMessage());
		}

	}

	@Nested
	class GetCurrentBoostTicks
	{
		private static final int MAX_TICKS = 10;

		@Test
		void withDefaultValue()
		{
			assertEquals(0, strider.getCurrentBoostTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
		void withValidBoostValue(int value)
		{
			strider.setBoostTicks(MAX_TICKS);
			assertDoesNotThrow(() -> strider.setCurrentBoostTicks(value));
			assertEquals(value, strider.getCurrentBoostTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = {-10, -9, -8, -7, -6, -5, -4, -3, -2, -1, 11, 12, 13, 14, 15})
		void withInvalidBoostValue(int value)
		{
			strider.setBoostTicks(MAX_TICKS);
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> strider.setCurrentBoostTicks(value));
			assertEquals("boost ticks must not exceed 0 or 10 (inclusive)", e.getMessage());
		}

	}

	@Test
	void getSteerMaterial()
	{
		assertEquals(Material.WARPED_FUNGUS_ON_A_STICK, strider.getSteerMaterial());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.STRIDER, strider.getType());
	}

}
