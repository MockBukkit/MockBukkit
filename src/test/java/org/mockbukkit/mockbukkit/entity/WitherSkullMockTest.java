package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class WitherSkullMockTest
{

	@MockBukkitInject
	private WitherSkullMock witherSkull;

	@Test
	void testGetChargedDefault()
	{
		assertFalse(witherSkull.isCharged());
	}

	@Test
	void testSetCharged()
	{
		witherSkull.setCharged(true);
		assertTrue(witherSkull.isCharged());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.WITHER_SKULL, witherSkull.getType());
	}

}
