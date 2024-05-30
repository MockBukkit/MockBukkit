package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class LeashHitchMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private LeashHitch leashHitch;

	@BeforeEach
	void setUp()
	{
		leashHitch = new LeashHitchMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.LEASH_HITCH, leashHitch.getType());
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
