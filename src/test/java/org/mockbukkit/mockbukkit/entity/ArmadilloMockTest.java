package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Armadillo;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class ArmadilloMockTest
{

	@MockBukkitInject
	private Armadillo armadillo;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.ARMADILLO, armadillo.getType());
	}

}
