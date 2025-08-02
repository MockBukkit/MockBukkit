package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class SpectralArrowMockTest
{

	@MockBukkitInject
	private SpectralArrowMock spectralArrow;

	@Test
	void getDamage_default()
	{
		assertEquals(6.0, spectralArrow.getDamage());
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
