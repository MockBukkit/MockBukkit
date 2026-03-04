package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class AmbientMockTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	@MockBukkitInject
	private AmbientMock ambient;

	@Test
	void testToString()
	{
		assertSame("AmbientMock", ambient.toString());
	}

}
