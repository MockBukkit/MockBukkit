package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AbstractProjectileMockTest
{

	private AbstractProjectileMock abstractProjectile;
	private ServerMock server;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		abstractProjectile = new FireworkMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testDoesBounceDefault()
	{
		assertThrows(UnsupportedOperationException.class, () -> abstractProjectile.doesBounce());
	}

	@Test
	void testSetBounce()
	{
		assertThrows(UnsupportedOperationException.class, () -> abstractProjectile.setBounce(true));
	}

	@Nested
	class SetShooter
	{

		@Test
		void givenDefaultValue()
		{
			assertNull(abstractProjectile.getShooter());
			assertNull(abstractProjectile.getOwnerUniqueId());
		}

		@Test
		void givenPossibleValue()
		{
			SkeletonMock source = new SkeletonMock(server, UUID.randomUUID());

			abstractProjectile.setShooter(source);

			assertEquals(source, abstractProjectile.getShooter());
			assertEquals(source.getUniqueId(), abstractProjectile.getOwnerUniqueId());
		}

	}

	@Nested
	class SetHasLeftShooter
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(abstractProjectile.hasLeftShooter());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean value)
		{
			abstractProjectile.setHasLeftShooter(value);

			assertEquals(value, abstractProjectile.hasLeftShooter());
		}

	}

	@Nested
	class SetHasBeenShot
	{

		@Test
		void givenDefaultValue()
		{
			assertFalse(abstractProjectile.hasBeenShot());
		}

		@ParameterizedTest
		@ValueSource(booleans = { true, false })
		void givenPossibleValues(boolean value)
		{
			abstractProjectile.setHasBeenShot(value);

			assertEquals(value, abstractProjectile.hasBeenShot());
		}

	}

}
