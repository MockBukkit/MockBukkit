package org.mockbukkit.metaminer;

import org.bukkit.plugin.java.JavaPlugin;
import org.mockbukkit.metaminer.internal.potion.PotionDataGenerator;
import org.mockbukkit.metaminer.internal.tags.InternalTagDataGenerator;

import java.io.IOException;
import java.util.List;

public class MetaMiner extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		for (DataGenerator dataGenerator : getDataGenerators())
		{
			try
			{
				dataGenerator.generateData();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

	}

	private List<DataGenerator> getDataGenerators()
	{
		return List.of(new KeyedDataGenerator(this.getDataFolder()), new InternalTagDataGenerator(this.getDataFolder()),new PotionDataGenerator(this.getDataFolder()));
	}

}
