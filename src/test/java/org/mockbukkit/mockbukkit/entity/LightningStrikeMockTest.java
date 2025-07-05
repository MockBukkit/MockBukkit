package org.mockbukkit.mockbukkit.entity;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class LightningStrikeMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private LightningStrikeMock lightning;

	@BeforeEach
	void setUp()
	{
		lightning = new LightningStrikeMock(server, UUID.randomUUID());
	}

	@Nested
	class SetEffect
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(lightning.isEffect());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean expectedValue)
		{
			lightning.setEffect(expectedValue);
			assertEquals(expectedValue, lightning.isEffect());
		}

	}

	@Nested
	class SetFlashes
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(3, lightning.getFlashes());
		}

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3 })
		void givenPossibleValues(int expectedValue)
		{
			lightning.setFlashes(expectedValue);
			assertEquals(expectedValue, lightning.getFlashes());
		}

		@ParameterizedTest
		@ValueSource(ints = { -5, -4, -3, -2, -1, 0 })
		void givenImpossibleValues(int expectedValue)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lightning.setFlashes(expectedValue));
			assertEquals("flashes must be >= 1", e.getMessage());
		}

	}

	@Nested
	class SetLifeTicks
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(0, lightning.getLifeTicks());
		}

		@ParameterizedTest
		@ValueSource(ints = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 })
		void givenPossibleValues(int expectedValue)
		{
			lightning.setLifeTicks(expectedValue);
			assertEquals(expectedValue, lightning.getLifeTicks());
		}

	}

	@Nested
	class SetCausingPlayer
	{

		@Test
		void givenDefaultValue()
		{
			assertNull(lightning.getCausingPlayer());
			assertNull(lightning.getCausingEntity());
		}

		@Test
		void givenChangeInValue()
		{
			PlayerMock player = server.addPlayer("test");
			lightning.setCausingPlayer(player);
			assertEquals(player, lightning.getCausingPlayer());
			assertEquals(player, lightning.getCausingEntity());
		}

	}

	@Nested
	class SetFlashCount
	{

		@Test
		void givenDefaultValue()
		{
			assertEquals(3, lightning.getFlashCount());
		}

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3 })
		void givenPossibleValues(int expectedValue)
		{
			lightning.setFlashCount(expectedValue);
			assertEquals(expectedValue, lightning.getFlashCount());
		}

		@ParameterizedTest
		@ValueSource(ints = { -5, -4, -3, -2, -1, 0 })
		void givenImpossibleValues(int expectedValue)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> lightning.setFlashCount(expectedValue));
			assertEquals("flashes must be >= 1", e.getMessage());
		}

	}

}
