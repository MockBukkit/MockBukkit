package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class GolemMockTest
{

	@MockBukkitInject
	private GolemMock golem;

	@Test
	void getAmbientSound()
	{
		assertNull(golem.getAmbientSound());
	}

	@Test
	void getHurtSound()
	{
		assertNull(golem.getHurtSound());
	}

	@Test
	void getDeathSound()
	{
		assertNull(golem.getDeathSound());
	}

}
