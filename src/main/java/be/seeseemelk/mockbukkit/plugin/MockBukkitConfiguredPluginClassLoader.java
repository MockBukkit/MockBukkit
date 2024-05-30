package be.seeseemelk.mockbukkit.plugin;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.utils.PaperPluginLogger;
import io.papermc.paper.plugin.configuration.PluginMeta;
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import io.papermc.paper.plugin.provider.classloader.PluginClassLoaderGroup;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class MockBukkitConfiguredPluginClassLoader extends ClassLoader implements ConfiguredPluginClassLoader
{

	private final ServerMock server;
	private final PluginDescriptionFile description;
	private final File dataFolder;
	private final File pluginFile;
	private JarFile jarFile = null;
	private final PluginClassLoaderGroup classLoaderGroup = new MockBukkitPluginClassLoaderGroup();

	public MockBukkitConfiguredPluginClassLoader(ServerMock server, PluginDescriptionFile description, File dataFolder,
			File pluginFile)
	{
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
	public Class<?> loadClass(@NotNull String name, boolean resolve, boolean checkGlobal, boolean checkLibraries)
			throws ClassNotFoundException
	{
		return loadClass(name, resolve);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		try
		{
			if (jarFile == null)
				throw new IllegalStateException("No jar file selected");
			ZipEntry entry = jarFile.getEntry(name.replace('.', '/') + ".class");
			InputStream inputStream = jarFile.getInputStream(entry);
			byte[] array = inputStream.readAllBytes();
			return defineClass(name, array, 0, array.length);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	public Class<? extends JavaPlugin> loadProxyClass(Class<? extends JavaPlugin> target)
	{
		DynamicType.Unloaded<? extends JavaPlugin> dynamicType = new ByteBuddy()
				.subclass(target, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
				.name(target.getSimpleName() + "Proxy").make();
		return dynamicType.load(this, ClassLoadingStrategy.Default.INJECTION).getLoaded();
	}

	@Override
	public void init(JavaPlugin plugin)
	{
		plugin.init(server, description, dataFolder, pluginFile, this, getConfiguration(),
				PaperPluginLogger.getLogger(getConfiguration()));
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
		// TODO Auto-generated method stub
		return classLoaderGroup;
	}

	@Override
	public void close() throws IOException
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
