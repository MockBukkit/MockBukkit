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

class VindicatorMockTest
{
	private ServerMock server;
	private VindicatorMock vindicator;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		vindicator = new VindicatorMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void isJohnny_GivenDefaultValue()
	{
		assertFalse(vindicator.isJohnny());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void getSpell_GivenValidValues(boolean isJohnny)
	{
		vindicator.setJohnny(isJohnny);
		assertEquals(isJohnny, vindicator.isJohnny());
	}

	@Test
	void getCelebrationSound()
	{
		assertEquals(Sound.ENTITY_VINDICATOR_CELEBRATE, vindicator.getCelebrationSound());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(1.6575D, vindicator.getEyeHeight());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.VINDICATOR, vindicator.getType());
	}
}
