package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Armadillo;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class ArmadilloMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private Armadillo armadillo;

	@BeforeEach
	void setup()
	{
		this.armadillo = new ArmadilloMock(server, UUID.randomUUID());
	}


	@Test
	void testGetType()
	{
		assertEquals(EntityType.ARMADILLO, armadillo.getType());
	}



}
