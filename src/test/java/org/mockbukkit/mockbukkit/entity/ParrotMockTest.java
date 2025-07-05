package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ParrotMockTest
{

	private ParrotMock parrot;
	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		parrot = new ParrotMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.PARROT, parrot.getType());
	}

	@Test
	void testSetVariant()
	{
		Parrot.Variant newVariant = Parrot.Variant.BLUE;
		parrot.setVariant(newVariant);
		assertEquals(newVariant, parrot.getVariant());
	}

	@Test
	void testIsTamed()
	{
		assertFalse(parrot.isTamed());
	}

	@Test
	void testSetTamed()
	{
		parrot.setTamed(true);
		assertTrue(parrot.isTamed());
	}

	@Test
	void testIsSitting()
	{
		assertFalse(parrot.isSitting());
	}

	@Test
	void testSetSitting()
	{
		parrot.setSitting(true);
		assertTrue(parrot.isSitting());
	}

	@Test
	void testSetOwner()
	{
		AnimalTamer owner = server.addPlayer();
		parrot.setOwner(owner);
		assertEquals(owner, parrot.getOwner());
	}

	@Test
	void testGetOwnerUniqueId()
	{
		AnimalTamer owner = server.addPlayer();
		parrot.setOwner(owner);
		assertEquals(owner.getUniqueId(), parrot.getOwnerUniqueId());
	}

}
