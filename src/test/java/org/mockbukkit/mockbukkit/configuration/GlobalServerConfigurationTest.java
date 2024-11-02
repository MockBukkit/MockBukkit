package org.mockbukkit.mockbukkit.configuration;

import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.configuration.ServerConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalServerConfigurationTest
{

	ServerConfiguration config = new ServerConfiguration();

	@Test
	void testGetViewDistanceDefault()
	{
		assertEquals(10, config.getViewDistance());
	}

	@Test
	void testViewDistance()
	{
		config.setViewDistance(12);
		assertEquals(12, config.getViewDistance());
	}

	@Test
	void testGetLevelTypeDefault()
	{
		assertEquals(ServerConfiguration.LevelType.DEFAULT, config.getLevelType());
	}

	@Test
	void testSetLevelType()
	{
		config.setLevelType(ServerConfiguration.LevelType.FLAT);
		assertEquals(ServerConfiguration.LevelType.FLAT, config.getLevelType());
	}

	@Test
	void testIsGenerateStructuresDefault()
	{
		assertTrue(config.isGenerateStructures());
	}

	@Test
	void testSetGenerateStructures()
	{
		config.setGenerateStructures(false);
		assertFalse(config.isGenerateStructures());
	}

	@Test
	void testIsAllowEndDefault()
	{
		assertTrue(config.isAllowEnd());
	}

	@Test
	void testSetAllowEnd()
	{
		config.setAllowEnd(false);
		assertFalse(config.isAllowEnd());
	}

	@Test
	void testIsAllowNetherDefault()
	{
		assertTrue(config.isAllowNether());
	}

	@Test
	void testSetAllowNether()
	{
		config.setAllowNether(false);
		assertFalse(config.isAllowNether());
	}

	@Test
	void testGetUpdateFolderDefault()
	{
		assertEquals("update", config.getUpdateFolder());
	}

	@Test
	void testSetUpdateFolder()
	{
		config.setUpdateFolder("test");
		assertEquals("test", config.getUpdateFolder());
	}

	@Test
	void testGetSimulationDistanceDefault()
	{
		assertEquals(10, config.getSimulationDistance());
	}

	@Test
	void testSetSimulationDistance()
	{
		config.setSimulationDistance(12);
		assertEquals(12, config.getSimulationDistance());
	}

	@Test
	void testIsHideOnlinePlayersDefault()
	{
		assertFalse(config.isHideOnlinePlayers());
	}

	@Test
	void testSetHideOnlinePlayers()
	{
		config.setHideOnlinePlayers(true);
		assertTrue(config.isHideOnlinePlayers());
	}

	@Test
	void testGetOnlineModeDefault()
	{
		assertTrue(config.isOnlineMode());
	}

	@Test
	void testSetOnlineMode()
	{
		config.setOnlineMode(false);
		assertFalse(config.isOnlineMode());
	}

	@Test
	void testIsEnforcingSecureProfiles()
	{
		assertTrue(config.isEnforceSecureProfiles());
	}

	@Test
	void testSetEnforcingSecureProfiles()
	{
		config.setEnforceSecureProfiles(false);
		assertFalse(config.isEnforceSecureProfiles());
	}

	@Test
	void testIsAllowFlight()
	{
		assertFalse(config.isAllowFlight());
	}

	@Test
	void testSetAllowFlight()
	{
		config.setAllowFlight(true);
		assertTrue(config.isAllowFlight());
	}

	@Test
	void testIsHardcoreDefault()
	{
		assertFalse(config.isHardcore());
	}

	@Test
	void testSetHardCore()
	{
		config.setHardcore(true);
		assertTrue(config.isHardcore());
	}

	@Test
	void testGetMaxChainedNeighborUpdatesDefault()
	{
		assertEquals(1000000, config.getMaxChainedNeighbourUpdates());
	}

	@Test
	void testSetMaxChainedNeighborUpdates()
	{
		config.setMaxChainedNeighbourUpdates(1);
		assertEquals(1, config.getMaxChainedNeighbourUpdates());
	}

	@Test
	void testGetShutdownMessageDefault()
	{
		assertEquals(Component.text("Server closed"), config.getShutdownMessage());
	}

	@Test
	void testSetShutdownMessage()
	{
		config.setShutdownMessage(Component.text("Test"));
		assertEquals(Component.text("Test"), config.getShutdownMessage());
	}

	@Test
	void testGetMaxWorldSizeDefault()
	{
		assertEquals(29999984, config.getMaxWorldSize());
	}

	@Test
	void testSetMaxWorldSize()
	{
		config.setMaxWorldSize(42);
		assertEquals(42, config.getMaxWorldSize());
	}

	@Test
	void testLevelTypeGetKey()
	{
		assertEquals("minecraft:normal", ServerConfiguration.LevelType.DEFAULT.getKey());
	}

	@Test
	void testIsPVPEnabledDefault()
	{
		assertTrue(config.isPvpEnabled());
	}

	@Test
	void testSetPVPEnabled()
	{
		config.setPvpEnabled(false);
		assertFalse(config.isPvpEnabled());
	}

}
