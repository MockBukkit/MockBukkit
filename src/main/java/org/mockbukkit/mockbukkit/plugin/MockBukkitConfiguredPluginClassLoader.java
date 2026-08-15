package org.mockbukkit.mockbukkit.plugin;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import io.papermc.paper.plugin.configuration.PluginMeta;
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import io.papermc.paper.plugin.provider.classloader.PluginClassLoaderGroup;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.PluginClassNotFoundException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class MockBukkitConfiguredPluginClassLoader extends URLClassLoader implements ConfiguredPluginClassLoader
{

	private final ServerMock server;
	private final PluginDescriptionFile description;
	private final File dataFolder;
	private final File pluginFile;
	private JarFile jarFile = null;
	private final PluginClassLoaderGroup classLoaderGroup = new MockBukkitPluginClassLoaderGroup();

	public MockBukkitConfiguredPluginClassLoader(
			ServerMock server,
			PluginDescriptionFile description,
			File dataFolder,
			File pluginFile
	)
	{
		super(new URL[0]);
		this.server = server;
		this.description = description;
		this.dataFolder = dataFolder;
		this.pluginFile = pluginFile;
	}

	public void setJarFile(JarFile jarFile)
	{
		this.jarFile = jarFile;
	}

	@Override
	public PluginMeta getConfiguration()
	{
		return description;
	}

	@Override
	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		Class<?> groupLoadedClass = classLoaderGroup.getClassByName(name, resolve, this);
		if (groupLoadedClass == null)
		{
			return super.loadClass(name, resolve);
		}
		else
		{
			return groupLoadedClass;
		}
	}

	@Override
	public Class<?> loadClass(@NotNull String name, boolean resolve, boolean checkGlobal, boolean checkLibraries) throws ClassNotFoundException
	{
		return loadClass(name, resolve);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		// No jar file backs a class-based plugin load (MockBukkit.load(Class)). ByteBuddy probes
		// findClass as an existence check before defining a class, so honor the ClassLoader.findClass
		// contract and throw ClassNotFoundException instead of an unchecked NullPointerException.
		if (jarFile == null)
		{
			throw new ClassNotFoundException(name);
		}
		ZipEntry entry = jarFile.getEntry(name.replace('.', '/') + ".class");
		if (entry == null)
		{
			throw new ClassNotFoundException(name);
		}
		try (InputStream inputStream = jarFile.getInputStream(entry))
		{
			byte[] array = inputStream.readAllBytes();
			return defineClass(name, array, 0, array.length);
		}
		catch (IOException e)
		{
			throw new PluginClassNotFoundException(e);
		}
	}

	public Class<? extends Plugin> loadProxyClass(Class<? extends Plugin> target)
	{
		DynamicType.Unloaded<? extends Plugin> dynamicType = new ByteBuddy()
				.subclass(target, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
				.name(target.getSimpleName() + "Proxy")
				.make();
		return dynamicType
				.load(this, new DirectDefinitionStrategy())
				.getLoaded();
	}

	private Class<?> defineType(@NotNull String name, byte @NotNull [] bytes)
	{
		return defineClass(name, bytes, 0, bytes.length);
	}

	@Override
	public void init(JavaPlugin plugin)
	{
		plugin.init(server, description, dataFolder, pluginFile, this, getConfiguration(), PaperPluginLogger.getLogger(getConfiguration()));
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
	public void close()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * A {@link ClassLoadingStrategy} that defines the generated types directly on the
	 * {@link MockBukkitConfiguredPluginClassLoader} they are loaded with.
	 * <p>
	 * {@link ClassLoadingStrategy.Default#INJECTION} can not be used for this, as it reflects into the internals of
	 * {@link ClassLoader} through {@code sun.misc.Unsafe}. Byte Buddy disables that access by default as of Java 26,
	 * which would leave it unable to define the plugin proxy at all.
	 */
	private static class DirectDefinitionStrategy implements ClassLoadingStrategy<MockBukkitConfiguredPluginClassLoader>
	{

		@Override
		public Map<TypeDescription, Class<?>> load(MockBukkitConfiguredPluginClassLoader classLoader, Map<TypeDescription, byte[]> types)
		{
			Map<TypeDescription, Class<?>> loadedTypes = new LinkedHashMap<>();
			for (Map.Entry<TypeDescription, byte[]> entry : types.entrySet())
			{
				loadedTypes.put(entry.getKey(), classLoader.defineType(entry.getKey().getName(), entry.getValue()));
			}
			return loadedTypes;
		}

	}

}
