package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoggedMockTest
{
	private ServerMock server;
	private BoggedMock bogged;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		bogged = new BoggedMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
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
