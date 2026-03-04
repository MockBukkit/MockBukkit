package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.bukkit.entity.EntityType.ELDER_GUARDIAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ElderGuardianMockTest
{

	@MockBukkitInject
	private ElderGuardianMock guardian;

	@Test
	void testGetType()
	{
		assertEquals(ELDER_GUARDIAN, guardian.getType());
	}

	@Test
	void testIsElder()
	{
		assertTrue(guardian.isElder());
	}

}
