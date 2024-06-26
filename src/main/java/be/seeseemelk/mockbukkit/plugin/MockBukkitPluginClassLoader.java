package be.seeseemelk.mockbukkit.plugin;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.utils.PaperPluginLogger;
import com.google.common.base.Preconditions;
import io.papermc.paper.plugin.configuration.PluginMeta;
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import io.papermc.paper.plugin.provider.classloader.PluginClassLoaderGroup;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

@ApiStatus.Internal
public class MockBukkitPluginClassLoader extends ClassLoader implements ConfiguredPluginClassLoader
{

	private PluginDescriptionFile pluginDescriptionFile;
	private final PluginClassLoaderGroup classLoaderGroup = new MockBukkitPluginClassLoaderGroup();

	public MockBukkitPluginClassLoader(ClassLoader parent)
	{
		super(parent);
	}

	@Override
	public PluginMeta getConfiguration()
	{
		return null;
	}

	@Override
	public Class<?> loadClass(@NotNull String name, boolean resolve, boolean checkGlobal, boolean checkLibraries) throws ClassNotFoundException
	{
		return loadClass("name");
	}

	@Override
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		synchronized (getClassLoadingLock(name))
		{
			Class<?> c = findLoadedClass(name);
			if (c == null)
			{
				c = findClass(name);
			}
			if (resolve)
			{
				resolveClass(c);
			}
			return c;
		}
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		byte[] classData = MockBukkitClassLoader.loadClassData(name, getParent());
		Class<?> pluginClass = super.defineClass(name, classData, 0, classData.length);
		if (pluginClass.isAssignableFrom(JavaPlugin.class))
		{
			try
			{
				this.pluginDescriptionFile = findPluginDescription(pluginClass);
			}
			catch (InvalidDescriptionException | IOException e)
			{
				throw new RuntimeException(e);
			}
		}
		return pluginClass;
	}

	private @NotNull PluginDescriptionFile findPluginDescription(@NotNull Class<?> class1)
			throws IOException, InvalidDescriptionException
	{
		Preconditions.checkNotNull(class1, "Class cannot be null");
		Enumeration<URL> resources = class1.getClassLoader().getResources("plugin.yml");
		while (resources.hasMoreElements())
		{
			URL url = resources.nextElement();
			PluginDescriptionFile description = new PluginDescriptionFile(url.openStream());
			String mainClass = description.getMain();
			if (class1.getName().equals(mainClass))
				return description;
		}
		throw new FileNotFoundException(
				"Could not find file plugin.yml. Maybe forgot to add the 'main' property?");
	}

	@Override
	public void init(JavaPlugin plugin)
	{
		ServerMock server = MockBukkit.getMock();
		if (server == null)
		{
			throw new IllegalStateException("Requires mocking to initialize plugin");
		}
		String name = pluginDescriptionFile.getName();
		String version = pluginDescriptionFile.getVersion();
		try
		{
			File dataFolder = createTemporaryDirectory(name + "-" + version, server);
			File pluginFile = createTemporaryPluginFile(name + "-" + version, server);
			plugin.init(server, this.pluginDescriptionFile, dataFolder, pluginFile, this, getConfiguration(), PaperPluginLogger.getLogger(getConfiguration()));
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public @Nullable JavaPlugin getPlugin()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable PluginClassLoaderGroup getGroup()
	{
		return classLoaderGroup;
	}

	@Override
	public void close() throws IOException
	{
		// No resource needs to be closed
	}


	/**
	 * Tries to create a temporary directory.
	 *
	 * @param name The name of the directory to create.
	 * @return The created temporary directory.
	 * @throws IOException when the directory could not be created.
	 */
	public @NotNull File createTemporaryDirectory(@NotNull String name, ServerMock serverMock) throws IOException
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		PluginManagerMock pluginManager = serverMock.getPluginManager();
		File directory = new File(pluginManager.getParentTemporaryDirectory(), name);
		directory.mkdirs();
		return directory;
	}

	/**
	 * Tries to create a temporary plugin file.
	 *
	 * @param name The name of the plugin.
	 * @return The created temporary file.
	 * @throws IOException when the file could not be created.
	 */
	public @NotNull File createTemporaryPluginFile(@NotNull String name, ServerMock serverMock) throws IOException
	{
		Preconditions.checkNotNull(name, "Name cannot be null");
		PluginManagerMock pluginManager = serverMock.getPluginManager();
		File pluginFile = new File(pluginManager.getParentTemporaryDirectory(), name + ".jar");
		if (!pluginFile.exists() && !pluginFile.createNewFile())
		{
			throw new IOException("Could not create file " + pluginFile.getAbsolutePath());
		}
		return pluginFile;
	}

}
