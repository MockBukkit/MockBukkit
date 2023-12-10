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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TNTPrimedMockTest
{
	private TNTPrimedMock tntPrimed;
	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		tntPrimed = new TNTPrimedMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.PRIMED_TNT, tntPrimed.getType());
	}

	@Test
	void testGetFuseTicksDefault()
	{
		assertEquals(80, tntPrimed.getFuseTicks());
	}

	@Test
	void testSetFuseTicks()
	{
		tntPrimed.setFuseTicks(10);
		assertEquals(10, tntPrimed.getFuseTicks());
	}

	@Test
	void testGetSourceDefault()
	{
        assertNull(tntPrimed.getSource());
	}

	@Test
	void testSetSource() {
		PlayerMock playerMock = server.addPlayer();
		tntPrimed.setSource(playerMock);
		assertEquals(playerMock, tntPrimed.getSource());
	}

	@Test
	void testGetYieldDefault()
	{
		assertEquals(4, tntPrimed.getYield());
	}

	@Test
	void testSetYield() {
		tntPrimed.setYield(2);
		assertEquals(2, tntPrimed.getYield());
	}

	@Test
	void testIsIncendiaryDefault()
	{
		assertFalse(tntPrimed.isIncendiary());
	}

	@Test
	void testSetIsIncendiary() {
		tntPrimed.setIsIncendiary(true);
		assertTrue(tntPrimed.isIncendiary());
	}

}
