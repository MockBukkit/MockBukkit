package be.seeseemelk.mockbukkit;

import com.google.common.base.Preconditions;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * A simple plugin that does nothing.
 */
public class MockPlugin extends JavaPlugin
{

	/**
	 * CraftBukkit constructor.
	 */
	public MockPlugin()
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
	protected MockPlugin(@NotNull JavaPluginLoader loader, @NotNull PluginDescriptionFile description, @NotNull File dataFolder, @NotNull File file)
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

		private Optional<Consumer<MockPlugin>> onLoad = Optional.empty();
		private Optional<Consumer<MockPlugin>> onEnable = Optional.empty();
		private Optional<Consumer<MockPlugin>> onDisable = Optional.empty();
		private String pluginName = "MockPlugin";
		private String pluginVersion = "1.0.0";

		private Builder()
		{
		}

		/**
		 * @param onLoad What to run on load
		 * @return This builder
		 */
		public Builder withOnLoad(@NotNull Consumer<MockPlugin> onLoad)
		{
			this.onLoad = Optional.of(onLoad);
			return this;
		}

		/**
		 * @param onEnable What to run on enable
		 * @return This builder
		 */
		public Builder withOnEnable(@NotNull Consumer<MockPlugin> onEnable)
		{
			this.onEnable = Optional.of(onEnable);
			return this;
		}

		/**
		 * @param onDisable What to run on disable
		 * @return This builder
		 */
		public Builder withOnDisable(@NotNull Consumer<MockPlugin> onDisable)
		{
			this.onDisable = Optional.of(onDisable);
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
		 * Build and initiate a new MockPlugin instance
		 *
		 * @return A mock plugin instance
		 */
		public MockPlugin build()
		{
			MockBukkit.ensureMocking();

			PluginDescriptionFile description = new PluginDescriptionFile(pluginName, pluginVersion, InternalMockPlugin.class.getName());
			ServerMock mock = MockBukkit.getMock();
			JavaPlugin instance = mock.getPluginManager().loadPlugin(InternalMockPlugin.class, description, new Object[]{ onEnable, onDisable, onLoad });
			mock.getPluginManager().enablePlugin(instance);
			return (MockPlugin) instance;
		}

	}

	@ApiStatus.Internal
	public static class InternalMockPlugin extends MockPlugin
	{

		private final Optional<Consumer<MockPlugin>> onEnable;
		private final Optional<Consumer<MockPlugin>> onDisable;
		private final Optional<Consumer<MockPlugin>> onLoad;

		public InternalMockPlugin(Optional<Consumer<MockPlugin>> onEnable, Optional<Consumer<MockPlugin>> onDisable, Optional<Consumer<MockPlugin>> onLoad)
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
