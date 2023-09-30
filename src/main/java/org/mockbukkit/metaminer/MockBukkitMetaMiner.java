package org.mockbukkit.metaminer;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class MockBukkitMetaMiner extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		try
		{
			new KeyedDataGenerator(this.getDataFolder()).generateData();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
