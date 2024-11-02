package org.mockbukkit.mockbukkit.configuration;

import org.mockbukkit.mockbukkit.ServerMock;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.entity.SpawnCategory;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Represents all configuration options that can be retrieved from a {@link Server}.
 * This class is used internally, and should not be considered public API.
 * As such, breaking changes may occur here at any time.
 *
 * @see ServerMock
 */
@ApiStatus.Internal
public class ServerConfiguration
{

	private int viewDistance = 10;
	private LevelType levelType = LevelType.DEFAULT;
	private boolean generateStructures = true;
	private boolean allowEnd = true;
	private boolean allowNether = true;
	private String updateFolder = "update";
	private boolean enforceSecureProfiles = true;
	private boolean onlineMode = true;
	private boolean allowFlight = false;
	private boolean hardcore = false;
	private int maxChainedNeighbourUpdates = 1000000;
	private Component shutdownMessage = Component.text("Server closed");
	private int maxWorldSize = 29999984;
	private int simulationDistance = 10;
	private boolean hideOnlinePlayers = false;
	private String serverIp = "";
	private int serverPort = 25565;
	private boolean pvpEnabled = true;
	private final Object2LongOpenHashMap<SpawnCategory> ticksPerSpawn = new Object2LongOpenHashMap<>();
	private final Object2IntOpenHashMap<SpawnCategory> spawnLimits = new Object2IntOpenHashMap<>();
	private boolean sendChatPreviews = false;

	public ServerConfiguration()
	{
		// Set the default ticks per spawn values.
		ticksPerSpawn.put(SpawnCategory.ANIMAL, 400);
		ticksPerSpawn.put(SpawnCategory.MONSTER, 1);
		ticksPerSpawn.put(SpawnCategory.WATER_AMBIENT, 1);
		ticksPerSpawn.put(SpawnCategory.WATER_UNDERGROUND_CREATURE, 1);
		ticksPerSpawn.put(SpawnCategory.WATER_ANIMAL, 1);
		ticksPerSpawn.put(SpawnCategory.AMBIENT, 1);

		spawnLimits.put(SpawnCategory.MONSTER, 70);
		spawnLimits.put(SpawnCategory.ANIMAL, 10);
		spawnLimits.put(SpawnCategory.WATER_ANIMAL, 5);
		spawnLimits.put(SpawnCategory.WATER_AMBIENT, 20);
		spawnLimits.put(SpawnCategory.WATER_UNDERGROUND_CREATURE, 5);
		spawnLimits.put(SpawnCategory.AMBIENT, 15);
	}

	/**
	 * @return The view distance.
	 * @see ServerMock#getViewDistance()
	 */
	public int getViewDistance()
	{
		return this.viewDistance;
	}

	/**
	 * @param viewDistance The view distance.
	 * @see ServerMock#setViewDistance(int)
	 */
	public void setViewDistance(int viewDistance)
	{
		this.viewDistance = viewDistance;
	}

	/**
	 * @return The level type.
	 * @see ServerMock#getWorldType
	 */
	public LevelType getLevelType()
	{
		return this.levelType;
	}

	/**
	 * @param levelType The level type.
	 * @see ServerMock#setWorldType(LevelType)
	 */
	public void setLevelType(LevelType levelType)
	{
		this.levelType = levelType;
	}

	/**
	 * @return Generate structures.
	 * @see ServerMock#getGenerateStructures()
	 */
	public boolean isGenerateStructures()
	{
		return this.generateStructures;
	}

	/**
	 * @param generateStructures Generate structures.
	 * @see ServerMock#setGenerateStructures(boolean)
	 */
	public void setGenerateStructures(boolean generateStructures)
	{
		this.generateStructures = generateStructures;
	}

	/**
	 * @return Allow end.
	 * @see ServerMock#getAllowEnd()
	 */
	public boolean isAllowEnd()
	{
		return this.allowEnd;
	}

	/**
	 * @param allowEnd Allow end.
	 * @see ServerMock#setAllowEnd(boolean)
	 */
	public void setAllowEnd(boolean allowEnd)
	{
		this.allowEnd = allowEnd;
	}

	/**
	 * @return Allow nether.
	 * @see ServerMock#getAllowNether()
	 */
	public boolean isAllowNether()
	{
		return this.allowNether;
	}

	/**
	 * @param allowNether Allow end.
	 * @see ServerMock#setAllowNether(boolean)
	 */
	public void setAllowNether(boolean allowNether)
	{
		this.allowNether = allowNether;
	}

	/**
	 * @return Update folder.
	 */
	public String getUpdateFolder()
	{
		return this.updateFolder;
	}

	/**
	 * @param updateFolder Update folder.
	 * @see ServerMock#setUpdateFolder(String)
	 */
	public void setUpdateFolder(String updateFolder)
	{
		this.updateFolder = updateFolder;
	}

	/**
	 * @return Send chat previews.
	 * @see ServerMock#setShouldSendChatPreviews(boolean)
	 */
	public boolean shouldSendChatPreviews()
	{
		return this.sendChatPreviews;
	}

	/**
	 * @param shouldSendChatPreviews Send chat previews.
	 * @see ServerMock#setShouldSendChatPreviews(boolean)
	 */
	public void setShouldSendChatPreviews(boolean shouldSendChatPreviews)
	{
		this.sendChatPreviews = shouldSendChatPreviews;
	}

	/**
	 * @return Enforce secure profiles.
	 * @see ServerMock#isEnforcingSecureProfiles()
	 */
	public boolean isEnforceSecureProfiles()
	{
		return this.enforceSecureProfiles;
	}

	/**
	 * @param enforceSecureProfiles Enforce secure profiles.
	 * @see ServerMock#setEnforcingSecureProfiles(boolean)
	 */
	public void setEnforceSecureProfiles(boolean enforceSecureProfiles)
	{
		this.enforceSecureProfiles = enforceSecureProfiles;
	}

	/**
	 * @return Online mode.
	 * @see ServerMock#getOnlineMode()
	 */
	public boolean isOnlineMode()
	{
		return this.onlineMode;
	}

	/**
	 * @param onlineMode Online mode.
	 * @see ServerMock#setOnlineMode(boolean)
	 */
	public void setOnlineMode(boolean onlineMode)
	{
		this.onlineMode = onlineMode;
	}

	/**
	 * @return Allow flight.
	 * @see ServerMock#getAllowFlight()
	 */
	public boolean isAllowFlight()
	{
		return this.allowFlight;
	}

	/**
	 * @param allowFlight Allow flight.
	 * @see ServerMock#setAllowFlight(boolean)
	 */
	public void setAllowFlight(boolean allowFlight)
	{
		this.allowFlight = allowFlight;
	}

	/**
	 * @return Hardcore mode.
	 * @see ServerMock#isHardcore()
	 */
	public boolean isHardcore()
	{
		return this.hardcore;
	}

	/**
	 * @param hardcore Hardcore mode.
	 * @see ServerMock#setHardcore(boolean)
	 */
	public void setHardcore(boolean hardcore)
	{
		this.hardcore = hardcore;
	}

	/**
	 * @return Max chained neighbour updates.
	 * @see ServerMock#getMaxChainedNeighborUpdates()
	 */
	public int getMaxChainedNeighbourUpdates()
	{
		return this.maxChainedNeighbourUpdates;
	}

	/**
	 * @param maxChainedNeighbourUpdates Max chained neighbour updates.
	 * @see ServerMock#setMaxChainedNeighborUpdates(int)
	 */
	public void setMaxChainedNeighbourUpdates(int maxChainedNeighbourUpdates)
	{
		this.maxChainedNeighbourUpdates = maxChainedNeighbourUpdates;
	}

	/**
	 * @return Shutdown message.
	 * @see ServerMock#getShutdownMessage()
	 */
	public @NotNull Component getShutdownMessage()
	{
		return shutdownMessage;
	}

	/**
	 * @param shutdownMessage Shutdown message.
	 * @see ServerMock#setShutdownMessage(Component)
	 */
	public void setShutdownMessage(@NotNull Component shutdownMessage)
	{
		this.shutdownMessage = shutdownMessage;
	}

	/**
	 * @return Max world size.
	 * @see ServerMock#getMaxWorldSize()
	 */
	public int getMaxWorldSize()
	{
		return maxWorldSize;
	}

	/**
	 * @param maxWorldSize Max world size.
	 * @see ServerMock#setMaxWorldSize(int)
	 */
	public void setMaxWorldSize(int maxWorldSize)
	{
		this.maxWorldSize = maxWorldSize;
	}

	/**
	 * @return Simulation distance.
	 * @see ServerMock#getSimulationDistance()
	 */
	public int getSimulationDistance()
	{
		return simulationDistance;
	}

	/**
	 * @param simulationDistance Simulation distance.
	 * @see ServerMock#setSimulationDistance(int)
	 */
	public void setSimulationDistance(int simulationDistance)
	{
		this.simulationDistance = simulationDistance;
	}

	/**
	 * @return Hide online players.
	 * @see ServerMock#getHideOnlinePlayers()
	 */
	public boolean isHideOnlinePlayers()
	{
		return hideOnlinePlayers;
	}

	/**
	 * @param hideOnlinePlayers Hide online players
	 * @see ServerMock#setHideOnlinePlayers(boolean)
	 */
	public void setHideOnlinePlayers(boolean hideOnlinePlayers)
	{
		this.hideOnlinePlayers = hideOnlinePlayers;
	}

	/**
	 * @return The server listen IP
	 * @see ServerMock#getIp
	 */
	public String getServerIp()
	{
		return serverIp;
	}

	/**
	 * @param serverIp The server listen IP
	 * @see ServerMock#setIp
	 */
	public void setServerIp(String serverIp)
	{
		this.serverIp = serverIp;
	}

	/**
	 * @return The server listen port
	 * @see ServerMock#getPort
	 */
	public int getServerPort()
	{
		return serverPort;
	}

	/**
	 * @param serverPort The server listen port
	 * @see ServerMock#setPort
	 */
	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	/**
	 * @return PVP enabled
	 */
	public boolean isPvpEnabled()
	{
		return this.pvpEnabled;
	}

	/**
	 * @param pvpEnabled PVP enabled
	 */
	public void setPvpEnabled(boolean pvpEnabled)
	{
		this.pvpEnabled = pvpEnabled;
	}

	/**
	 * @return The ticks per spawn for each spawn category.
	 */
	public Object2LongOpenHashMap<SpawnCategory> getTicksPerSpawn()
	{
		return ticksPerSpawn;
	}

	/**
	 * @return The spawn limits for each spawn category.
	 */
	public Object2IntOpenHashMap<SpawnCategory> getSpawnLimits()
	{
		return spawnLimits;
	}

	/**
	 * Enum representing all different world types.
	 * <a href="https://minecraft.wiki/w/World_preset">Wiki</a>
	 */
	public enum LevelType
	{
		/**
		 * Regular.
		 */
		DEFAULT("minecraft:normal"),
		/**
		 * Super-flat.
		 */
		FLAT("minecraft:flat"),
		/**
		 * Large biomes.
		 */
		LARGE_BIOMES("minecraft:large_biomes"),
		/**
		 * Amplified.
		 */
		AMPLIFIED("minecraft:amplified"),
		/**
		 * Single biome.
		 */
		SINGLE_BIOME_SURFACE("minecraft:single_biome_surface"),
		/**
		 * Debug.
		 */
		DEFAULT_1_1("default_1_1"),
		/**
		 * Custom.
		 */
		CUSTOMIZED("customized");

		private final String key;

		LevelType(String key)
		{
			this.key = key;
		}

		/**
		 * @return The type key.
		 */
		public String getKey()
		{
			return key;
		}
	}

}
