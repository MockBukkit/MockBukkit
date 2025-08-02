package org.mockbukkit.mockbukkit.entity;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class LeashHitchMockTest
{

	@MockBukkitInject
	private LeashHitch leashHitch;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.LEASH_KNOT, leashHitch.getType());
	}

	@Test
	void testGetFacing()
	{
		assertEquals(BlockFace.SELF, leashHitch.getFacing());
	}

	@Test
	void testSetFacingDirection()
	{
		assertTrue(leashHitch.setFacingDirection(BlockFace.SELF, true));
	}

	@Test
	void testSetFacingDirectionFalse()
	{
		assertFalse(leashHitch.setFacingDirection(BlockFace.SELF, false));
	}

	@Test
	void testSetFacingDirectionNull()
	{
		assertThrows(NullPointerException.class, () -> leashHitch.setFacingDirection(null, true));
	}

	@Test
	void testSetFacingDirectionInvalidDirection()
	{
		assertThrows(IllegalArgumentException.class, () -> leashHitch.setFacingDirection(BlockFace.DOWN, true));
	}

}
