package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ParrotMockTest
{

	@MockBukkitInject
	private ParrotMock parrot;

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
	void testSetOwner(@MockBukkitInject AnimalTamer owner)
	{
		parrot.setOwner(owner);
		assertEquals(owner, parrot.getOwner());
	}

	@Test
	void testGetOwnerUniqueId(@MockBukkitInject AnimalTamer owner)
	{
		parrot.setOwner(owner);
		assertEquals(owner.getUniqueId(), parrot.getOwnerUniqueId());
	}

}
