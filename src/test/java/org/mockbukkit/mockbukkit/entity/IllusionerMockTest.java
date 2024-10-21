package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IllusionerMockTest
{

	private ServerMock server;
	private IllusionerMock illusioner;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		illusioner = new IllusionerMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getCelebrationSound()
	{
		assertEquals(Sound.ENTITY_ILLUSIONER_AMBIENT, illusioner.getCelebrationSound());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(1.6575D, illusioner.getEyeHeight());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ILLUSIONER, illusioner.getType());
	}

}
