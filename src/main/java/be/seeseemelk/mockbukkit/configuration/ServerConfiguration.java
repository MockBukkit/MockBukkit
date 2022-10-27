package be.seeseemelk.mockbukkit.configuration;

import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
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
	private boolean sendChatPreviews = false;
	private boolean enforceSecureProfiles = true;
	private boolean onlineMode = true;
	private boolean allowFlight = false;
	private boolean hardcore = false;
	private int maxChainedNeighbourUpdates = 1000000;
	private Component shutdownMessage = Component.text("Server closed");
	private int maxWorldSize = 29999984;
	private int simulationDistance = 10;
	private boolean hideOnlinePlayers = false;

	public int getViewDistance()
	{
		return this.viewDistance;
	}

	public void setViewDistance(int viewDistance)
	{
		this.viewDistance = viewDistance;
	}

	public LevelType getLevelType()
	{
		return this.levelType;
	}

	public void setLevelType(LevelType levelType)
	{
		this.levelType = levelType;
	}

	public boolean isGenerateStructures()
	{
		return this.generateStructures;
	}

	public void setGenerateStructures(boolean generateStructures)
	{
		this.generateStructures = generateStructures;
	}

	public boolean isAllowEnd()
	{
		return this.allowEnd;
	}

	public void setAllowEnd(boolean allowEnd)
	{
		this.allowEnd = allowEnd;
	}

	public boolean isAllowNether()
	{
		return this.allowNether;
	}

	public void setAllowNether(boolean allowNether)
	{
		this.allowNether = allowNether;
	}

	public String getUpdateFolder()
	{
		return this.updateFolder;
	}

	public void setUpdateFolder(String updateFolder)
	{
		this.updateFolder = updateFolder;
	}

	public boolean shouldSendChatPreviews()
	{
		return this.sendChatPreviews;
	}

	public void setShouldSendChatPreviews(boolean shouldSendChatPreviews)
	{
		this.sendChatPreviews = shouldSendChatPreviews;
	}

	public boolean isEnforceSecureProfiles()
	{
		return this.enforceSecureProfiles;
	}

	public void setEnforceSecureProfiles(boolean enforceSecureProfiles)
	{
		this.enforceSecureProfiles = enforceSecureProfiles;
	}

	public boolean isOnlineMode()
	{
		return this.onlineMode;
	}

	public void setOnlineMode(boolean onlineMode)
	{
		this.onlineMode = onlineMode;
	}

	public boolean isAllowFlight()
	{
		return this.allowFlight;
	}

	public void setAllowFlight(boolean allowFlight)
	{
		this.allowFlight = allowFlight;
	}

	public boolean isHardcore()
	{
		return this.hardcore;
	}

	public void setHardcore(boolean hardcore)
	{
		this.hardcore = hardcore;
	}

	public int getMaxChainedNeighbourUpdates()
	{
		return this.maxChainedNeighbourUpdates;
	}

	public void setMaxChainedNeighbourUpdates(int maxChainedNeighbourUpdates)
	{
		this.maxChainedNeighbourUpdates = maxChainedNeighbourUpdates;
	}

	public @NotNull Component getShutdownMessage()
	{
		return shutdownMessage;
	}

	public void setShutdownMessage(@NotNull Component shutdownMessage)
	{
		this.shutdownMessage = shutdownMessage;
	}

	public int getMaxWorldSize()
	{
		return maxWorldSize;
	}

	public void setMaxWorldSize(int maxWorldSize)
	{
		this.maxWorldSize = maxWorldSize;
	}

	public int getSimulationDistance()
	{
		return simulationDistance;
	}

	public void setSimulationDistance(int simulationDistance)
	{
		this.simulationDistance = simulationDistance;
	}

	public boolean isHideOnlinePlayers()
	{
		return hideOnlinePlayers;
	}

	public void setHideOnlinePlayers(boolean hideOnlinePlayers)
	{
		this.hideOnlinePlayers = hideOnlinePlayers;
	}

	public enum LevelType
	{
		DEFAULT("minecraft:normal"),
		FLAT("minecraft:flat"),
		LARGE_BIOMES("minecraft:large_biomes"),
		AMPLIFIED("minecraft:amplified"),
		SINGLE_BIOME_SURFACE("minecraft:single_biome_surface"),
		DEFAULT_1_1("default_1_1"),
		CUSTOMIZED("customized");

		private final String key;

		LevelType(String key)
		{
			this.key = key;
		}

		public String getKey()
		{
			return key;
		}
	}

}
