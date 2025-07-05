package org.mockbukkit.mockbukkit.plugin;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A simple plugin that does nothing.
 */
public class PluginMock extends JavaPlugin
{

	/**
	 * CraftBukkit constructor.
	 */
	public PluginMock()
	{
	}

	/**
	 * MockBukkit constructor.
	 *
	 * @param loader      The plugin loader.
	 * @param description The plugin description file.
	 * @param dataFolder  The plugins data folder.
	 * @param file        The plugins file.
	 */
	protected PluginMock(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file)
	{
		super(loader, description, dataFolder, file);
	}

	/**
	 * Create a builder for mock plugins, allows for on enable, on disable code execution and so on
	 *
	 * @return A builder for mock plugin
	 */
	public static Builder builder()
	{
		return new Builder();
	}

	public static class Builder
	{

		private Optional<Consumer<PluginMock>> onLoad = Optional.empty();
		private Optional<Consumer<PluginMock>> onEnable = Optional.empty();
		private Optional<Consumer<PluginMock>> onDisable = Optional.empty();
		private String pluginName = "PluginMock";
		private String pluginVersion = "1.0.0";

		private Builder()
		{
		}

		/**
		 * @param onLoad What to run on load
		 * @return This builder
		 */
		public Builder withOnLoad(@NotNull Consumer<PluginMock> onLoad)
		{
			this.onLoad = Optional.of(onLoad);
			return this;
		}

		/**
		 * @param onLoad What to run on load
		 * @return This builder
		 */
		public Builder withOnLoad(@NotNull Runnable onLoad)
		{
			this.onLoad = Optional.of(ignored -> onLoad.run());
			return this;
		}

		/**
		 * @param onEnable What to run on enable
		 * @return This builder
		 */
		public Builder withOnEnable(@NotNull Consumer<PluginMock> onEnable)
		{
			this.onEnable = Optional.of(onEnable);
			return this;
		}

		/**
		 * @param onEnable What to run on load
		 * @return This builder
		 */
		public Builder withOnEnable(@NotNull Runnable onEnable)
		{
			this.onEnable = Optional.of(ignored -> onEnable.run());
			return this;
		}

		/**
		 * @param onDisable What to run on disable
		 * @return This builder
		 */
		public Builder withOnDisable(@NotNull Consumer<PluginMock> onDisable)
		{
			this.onDisable = Optional.of(onDisable);
			return this;
		}

		/**
		 * @param onDisable What to run on load
		 * @return This builder
		 */
		public Builder withOnDisable(@NotNull Runnable onDisable)
		{
			this.onDisable = Optional.of(ignored -> onDisable.run());
			return this;
		}

		/**
		 * @param name The name of the plugin to build
		 * @return This builder
		 */
		public Builder withPluginName(@NotNull String name)
		{
			Preconditions.checkNotNull(name);
			this.pluginName = name;
			return this;
		}

		/**
		 * @param version The version of the blugin to build
		 * @return This builder
		 */
		public Builder withPluginVersion(@NotNull String version)
		{
			Preconditions.checkNotNull(version);
			this.pluginVersion = version;
			return this;
		}

		/**
		 * Build and initiate a new PluginMock instance
		 *
		 * @return A mock plugin instance
		 */
		public PluginMock build()
		{
			MockBukkit.ensureMocking();

			PluginDescriptionFile description = new PluginDescriptionFile(pluginName, pluginVersion, InternalPluginMock.class.getName());
			ServerMock mock = MockBukkit.getMock();
			JavaPlugin instance = mock.getPluginManager().loadPlugin(InternalPluginMock.class, description, new Object[]{ onEnable, onDisable, onLoad });
			mock.getPluginManager().enablePlugin(instance);
			return (PluginMock) instance;
		}

	}

	@ApiStatus.Internal
	public static class InternalPluginMock extends PluginMock
	{

		private final Optional<Consumer<PluginMock>> onEnable;
		private final Optional<Consumer<PluginMock>> onDisable;
		private final Optional<Consumer<PluginMock>> onLoad;

		public InternalPluginMock(Optional<Consumer<PluginMock>> onEnable, Optional<Consumer<PluginMock>> onDisable, Optional<Consumer<PluginMock>> onLoad)
		{
			this.onEnable = onEnable;
			this.onDisable = onDisable;
			this.onLoad = onLoad;
		}

		@Override
		public void onEnable()
		{
			onEnable.ifPresent((consumer) -> consumer.accept(this));
		}

		@Override
		public void onDisable()
		{
			onDisable.ifPresent((consumer) -> consumer.accept(this));
		}

		@Override
		public void onLoad()
		{
			onLoad.ifPresent((consumer) -> consumer.accept(this));
		}

	}

}
