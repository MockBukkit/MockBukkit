package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParrotMockTest
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
	public void testSetVariant()
	{
		Parrot.Variant newVariant = Parrot.Variant.BLUE;
		parrot.setVariant(newVariant);
		assertEquals(newVariant, parrot.getVariant());
	}
	@Test
	public void testIsDancing()
	{
		assertFalse(parrot.isDancing());
	}

	@Test
	public void testIsTamed()
	{
		assertFalse(parrot.isTamed());
	}
	@Test
	public void testSetTamed()
	{
		parrot.setTamed(true);
		assertTrue(parrot.isTamed());
	}
	@Test
	public void testIsSitting()
	{
		assertFalse(parrot.isSitting());
	}
	@Test
	public void testSetSitting()
	{
		parrot.setSitting(true);
		assertTrue(parrot.isSitting());
	}
	@Test
	public void testSetOwner()
	{
		AnimalTamer owner = server.addPlayer();
		parrot.setOwner(owner);
		assertEquals(owner, parrot.getOwner());
	}


}
