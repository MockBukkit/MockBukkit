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

class BatMockTest
{

	private BatMock bat;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		bat = new BatMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsAwakeDefault()
	{
		assertTrue(bat.isAwake());
	}

	@Test
	void testSetAwake()
	{
		bat.setAwake(false);
		assertFalse(bat.isAwake());
	}

	@Test
	void testEntityType()
	{
		assertEquals(EntityType.BAT, bat.getType());
	}

}
