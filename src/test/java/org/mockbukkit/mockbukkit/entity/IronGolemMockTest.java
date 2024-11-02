package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class IronGolemMockTest
{
	private ServerMock server;
	private IronGolemMock ironGolem;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		ironGolem = new IronGolemMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void isPlayerCreated_GivenDefaultValue()
	{
		assertFalse(ironGolem.isPlayerCreated());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isPlayerCreated_GivenChangesInValue(boolean isPlayerCreated)
	{
		ironGolem.setPlayerCreated(isPlayerCreated);
		assertEquals(isPlayerCreated, ironGolem.isPlayerCreated());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(2.295D, ironGolem.getEyeHeight());
	}

	@Test
	void getDeathSound()
	{
		assertEquals(Sound.ENTITY_IRON_GOLEM_DEATH, ironGolem.getDeathSound());
	}

	@Test
	void getHurtSound()
	{
		assertEquals(Sound.ENTITY_IRON_GOLEM_HURT, ironGolem.getHurtSound());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.IRON_GOLEM, ironGolem.getType());
	}

}
