package be.seeseemelk.mockbukkit.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
public class MockBukkitClassLoader extends ClassLoader
{

	private final Map<String, MockBukkitPluginClassLoader> classLoaders;
	private final ClassLoader bootstrapClassLoader;

	public MockBukkitClassLoader(ClassLoader parent)
	{
		super(parent);
		classLoaders = new HashMap<>();
		this.bootstrapClassLoader = getSystemClassLoader().getParent();
	}

	@Override
	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException
	{
		synchronized (getClassLoadingLock(name))
		{
			// First, check if the class has already been loaded
			Class<?> c = findLoadedClass(name);
			if (c == null)
			{
				if (classLoaders.containsKey(name))
				{
					c = classLoaders.get(name).loadClass(name);
				}
				else
				{
					c = loadClassWithChildFirstStrategy(name);
				}
			}
			if (resolve)
			{
				resolveClass(c);
			}
			return c;
		}
	}

	private Class<?> loadClassWithChildFirstStrategy(String name) throws ClassNotFoundException
	{
		try
		{
			return bootstrapClassLoader.loadClass(name);
		}
		catch (ClassNotFoundException ignored)
		{

		}
		return findClass(name);
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException
	{
		byte[] classData = loadClassData(name, getParent());
		Class<?> aClass = defineClass(name, classData, 0, classData.length);
		if (aClass.isAssignableFrom(JavaPlugin.class))
		{
			MockBukkitPluginClassLoader pluginClassLoader = new MockBukkitPluginClassLoader(getParent());
			Class<?> pluginClass = pluginClassLoader.loadClass(name);
			classLoaders.put(name, pluginClassLoader);
			return pluginClass;
		}
		return aClass;
	}

	static byte[] loadClassData(String name, ClassLoader classLoader) throws ClassNotFoundException
	{
		try (InputStream inputStream = classLoader.getResourceAsStream(name.replace('.', '/').concat(".class")))
		{
			if (inputStream == null)
			{
				throw new IOException("Could not find class: " + name);
			}
			return inputStream.readAllBytes();
		}
		catch (IOException e)
		{
			throw new ClassNotFoundException(e.getMessage());
		}
	}

}
