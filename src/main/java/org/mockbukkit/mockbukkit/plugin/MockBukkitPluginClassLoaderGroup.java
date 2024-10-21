package org.mockbukkit.mockbukkit.plugin;

import io.papermc.paper.plugin.provider.classloader.ClassLoaderAccess;
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader;
import io.papermc.paper.plugin.provider.classloader.PluginClassLoaderGroup;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class MockBukkitPluginClassLoaderGroup implements PluginClassLoaderGroup
{

	private final Set<ConfiguredPluginClassLoader> classLoaders = new HashSet<>();

	@Override
	public @Nullable Class<?> getClassByName(String name, boolean resolve, ConfiguredPluginClassLoader requester)
	{
		for (ConfiguredPluginClassLoader cl : classLoaders)
		{
			try
			{
				return cl.loadClass(name, resolve, true, true);
			}
			catch (ClassNotFoundException ignored)
			{
			}
		}
		return null;
	}

	@Override
	public void remove(ConfiguredPluginClassLoader configuredPluginClassLoader)
	{
		classLoaders.remove(configuredPluginClassLoader);
	}

	@Override
	public void add(ConfiguredPluginClassLoader configuredPluginClassLoader)
	{
		classLoaders.add(configuredPluginClassLoader);
	}

	@Override
	public ClassLoaderAccess getAccess()
	{
		return classLoader -> true;
	}

}
