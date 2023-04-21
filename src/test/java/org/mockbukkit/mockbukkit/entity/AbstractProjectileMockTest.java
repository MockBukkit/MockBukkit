package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AbstractProjectileMockTest
{

	private AbstractProjectileMock abstractProjectile;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
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

}
