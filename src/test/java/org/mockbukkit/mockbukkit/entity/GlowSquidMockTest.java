package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowSquid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
public class GlowSquidMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private GlowSquid glowSquid;

	@BeforeEach
	void setUp()
	{
		glowSquid = new GlowSquidMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.GLOW_SQUID, glowSquid.getType());
	}

	@Test
	void testGetDarkTicksRemaining_Default()
	{
		assertEquals(0, glowSquid.getDarkTicksRemaining());
	}

	@Test
	void testSetDarkTicksRemaining_Negative_ThrowsException()
	{
		Assertions.assertThrows(IllegalArgumentException.class, () -> glowSquid.setDarkTicksRemaining(-1));
	}

	@Test
	void testSetDarkTicksRemaining_Zero()
	{
		glowSquid.setDarkTicksRemaining(0);
		assertEquals(0, glowSquid.getDarkTicksRemaining());
	}

	@Test
	void testSetDarkTicksRemaining_Positive()
	{
		glowSquid.setDarkTicksRemaining(10);
		assertEquals(10, glowSquid.getDarkTicksRemaining());
	}

}
