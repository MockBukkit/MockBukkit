package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class FrogMockTest
{

	@MockBukkitInject
	private FrogMock frog;

	@Test
	void testGetTongueTargetDefault()
	{
		assertNull(frog.getTongueTarget());
	}

	@Test
	void testSetTongueTarget(@MockBukkitInject PlayerMock playerMock)
	{
		frog.setTongueTarget(playerMock);
		assertEquals(playerMock, frog.getTongueTarget());
	}

	@Test
	void testGetVariantDefault()
	{
		assertEquals(Frog.Variant.TEMPERATE, frog.getVariant());
	}

	@Test
	void testSetVariant()
	{
		frog.setVariant(Frog.Variant.COLD);
		assertEquals(Frog.Variant.COLD, frog.getVariant());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.FROG, frog.getType());
	}

}
