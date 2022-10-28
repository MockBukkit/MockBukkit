package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuardianMockTest
{

	private GuardianMock guardian;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		guardian = new GuardianMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testHasLaserDefault()
	{
		assertFalse(guardian.hasLaser());
	}

	@Test
	void testSetLaser()
	{
		guardian.setLaser(true);
		assertTrue(guardian.hasLaser());
	}

	@Test
	void testIsElderDefault()
	{
		assertFalse(guardian.isElder());
	}

	@Test
	void testSetElder()
	{
		guardian.setElder(true);
		assertTrue(guardian.isElder());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.GUARDIAN, guardian.getType());
	}

}
