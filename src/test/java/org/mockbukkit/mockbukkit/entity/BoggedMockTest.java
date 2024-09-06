package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BoggedMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private BoggedMock bogged;

	@BeforeEach
	void setUp()
	{
		bogged = new BoggedMock(server, UUID.randomUUID());
	}

	@Test
	void shear_GivenIllegalArgument()
	{
		NullPointerException e = assertThrows(NullPointerException.class, () -> bogged.shear(null));
		assertEquals("The source cannot be null", e.getMessage());
	}

	@Test
	void shear_GivenShearSequence()
	{
		assertFalse(bogged.isSheared());

		bogged.shear(Sound.Source.PLAYER);
		assertTrue(bogged.isSheared());

		bogged.setSheared(false);
		assertFalse(bogged.isSheared());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.BOGGED, bogged.getType());
	}

	@Test
	void getHeight_GivenDefaultHusk()
	{
		assertEquals(1.99D, bogged.getHeight());
	}

	@Test
	void getWidth_GivenDefaultHusk()
	{
		assertEquals(0.6D, bogged.getWidth());
	}

	@Test
	void getEyeHeight_GivenDefaultHusk()
	{
		assertEquals(1.74D, bogged.getEyeHeight());
	}

}
