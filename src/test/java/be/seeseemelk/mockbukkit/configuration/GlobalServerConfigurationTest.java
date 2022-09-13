package be.seeseemelk.mockbukkit.configuration;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GlobalServerConfigurationTest
{

	GlobalServerConfiguration config = new GlobalServerConfiguration();

	@Test
	void testGetViewDistanceDefault()
	{
		assertEquals(10, config.getViewDistance());
	}

	@Test
	public void testViewDistance()
	{
		config.setViewDistance(12);
		assertEquals(12, config.getViewDistance());
	}

	@Test
	public void testGetLevelTypeDefault()
	{
		assertEquals(GlobalServerConfiguration.LevelType.DEFAULT, config.getLevelType());
	}

	@Test
	public void testSetLevelType()
	{
		config.setLevelType(GlobalServerConfiguration.LevelType.FLAT);
		assertEquals(GlobalServerConfiguration.LevelType.FLAT, config.getLevelType());
	}

	@Test
	public void testIsGenerateStructuresDefault()
	{
		assertTrue(config.isGenerateStructures());
	}

	@Test
	public void testSetGenerateStructures()
	{
		config.setGenerateStructures(false);
		assertFalse(config.isGenerateStructures());
	}

	@Test
	public void testIsAllowEndDefault()
	{
		assertTrue(config.isAllowEnd());
	}

	@Test
	public void testSetAllowEnd()
	{
		config.setAllowEnd(false);
		assertFalse(config.isAllowEnd());
	}

	@Test
	public void testIsAllowNetherDefault()
	{
		assertTrue(config.isAllowNether());
	}

	@Test
	public void testSetAllowNether()
	{
		config.setAllowNether(false);
		assertFalse(config.isAllowNether());
	}

	@Test
	public void testGetUpdateFolderDefault()
	{
		assertEquals("update", config.getUpdateFolder());
	}

	@Test
	public void testSetUpdateFolder()
	{
		config.setUpdateFolder("test");
		assertEquals("test", config.getUpdateFolder());
	}

	@Test
	public void testGetSimulationDistanceDefault()
	{
		assertEquals(10, config.getSimulationDistance());
	}

	@Test
	public void testSetSimulationDistance()
	{
		config.setSimulationDistance(12);
		assertEquals(12, config.getSimulationDistance());
	}

	@Test
	public void testIsHideOnlinePlayersDefault()
	{
		assertFalse(config.isHideOnlinePlayers());
	}

	@Test
	public void testSetHideOnlinePlayers()
	{
		config.setHideOnlinePlayers(true);
		assertTrue(config.isHideOnlinePlayers());
	}

	@Test
	void testIsShouldSendingChatPreviewsDefault()
	{
		assertFalse(config.shouldSendChatPreviews());
	}

	@Test
	void testSetShouldSendingChatPreviews()
	{
		config.setShouldSendChatPreviews(true);
		assertTrue(config.shouldSendChatPreviews());
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
		assertEquals("minecraft:normal", GlobalServerConfiguration.LevelType.DEFAULT.getKey());
	}

}
