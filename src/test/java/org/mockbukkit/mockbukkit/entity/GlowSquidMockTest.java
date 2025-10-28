package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowSquid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
public class GlowSquidMockTest
{

	@MockBukkitInject
	private GlowSquid glowSquid;

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
		assertThrows(IllegalArgumentException.class, () -> glowSquid.setDarkTicksRemaining(-1));
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
