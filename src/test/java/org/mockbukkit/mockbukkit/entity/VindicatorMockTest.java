package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockBukkitExtension.class)
class VindicatorMockTest
{

	@MockBukkitInject
	private VindicatorMock vindicator;

	@Test
	void isJohnny_GivenDefaultValue()
	{
		assertFalse(vindicator.isJohnny());
	}

	@ParameterizedTest
	@ValueSource(booleans = { true, false })
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
