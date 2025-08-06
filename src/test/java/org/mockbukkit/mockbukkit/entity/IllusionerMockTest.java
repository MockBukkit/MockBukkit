package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class IllusionerMockTest
{

	@MockBukkitInject
	private IllusionerMock illusioner;

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
