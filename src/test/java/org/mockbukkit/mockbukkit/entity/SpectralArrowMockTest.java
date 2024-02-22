package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpectralArrowMockTest
{

	private SpectralArrowMock spectralArrow;

	@BeforeEach
	void setUp()
	{
		ServerMock serverMock = MockBukkit.mock();
		this.spectralArrow = new SpectralArrowMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getDamage_default()
	{
		Assertions.assertEquals(6.0, spectralArrow.getDamage());
	}

	@Test
	void getGlowingTicks_default()
	{
		assertEquals(200, spectralArrow.getGlowingTicks());
	}

	@Test
	void setGlowingTicks()
	{
		spectralArrow.setGlowingTicks(4);
		assertEquals(4, spectralArrow.getGlowingTicks());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.SPECTRAL_ARROW, spectralArrow.getType());
	}

}
