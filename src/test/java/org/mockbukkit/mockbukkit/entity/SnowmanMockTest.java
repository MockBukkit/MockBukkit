package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnowmanMockTest
{
	private ServerMock server;
	private SnowmanMock snowman;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		snowman = new SnowmanMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void isDerp_GivenDefaultValue()
	{
		assertFalse(snowman.isDerp());
	}

	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void isDerp_GivenChangesInValue(boolean isPlayerCreated)
	{
		snowman.setDerp(isPlayerCreated);
		assertEquals(isPlayerCreated, snowman.isDerp());
	}

	@Test
	void shear()
	{
		assertFalse(snowman.isDerp());
		snowman.shear();
		assertTrue(snowman.isDerp());
	}

	@ParameterizedTest
	@CsvSource({
			"true,  false, true",
			"true,  true,  false",
			"false, true,  false",
			"false, false, false",
	})
	void readyToBeSheared(boolean isAlive, boolean isDerp, boolean expectedValue)
	{
		snowman.setDerp(isDerp);
		snowman.setHealth(isAlive ? 1 : 0);
		assertEquals(expectedValue, snowman.readyToBeSheared());
	}

	@Test
	void getAmbientSound()
	{
		assertEquals(Sound.ENTITY_SNOW_GOLEM_AMBIENT, snowman.getAmbientSound());
	}

	@Test
	void getHurtSound()
	{
		assertEquals(Sound.ENTITY_SNOW_GOLEM_HURT, snowman.getHurtSound());
	}

	@Test
	void getDeathSound()
	{
		assertEquals(Sound.ENTITY_SNOW_GOLEM_DEATH, snowman.getDeathSound());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.SNOW_GOLEM, snowman.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultValue()
	{
		assertEquals(1.7D, snowman.getEyeHeight());
	}
}
