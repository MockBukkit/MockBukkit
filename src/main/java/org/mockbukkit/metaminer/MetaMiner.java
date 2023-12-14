package org.mockbukkit.metaminer;

import org.bukkit.plugin.java.JavaPlugin;
import org.mockbukkit.metaminer.internal.potion.PotionDataGenerator;
import org.mockbukkit.metaminer.internal.tags.InternalTagDataGenerator;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class MetaMiner extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		this.getLogger().log(Level.INFO, "Generating data for MockBukkit");
		for (DataGenerator dataGenerator : getDataGenerators())
		{
			try
			{
				dataGenerator.generateData();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
		this.getLogger().log(Level.INFO, "Successfully generated data!");
		this.getLogger().log(Level.INFO, String.format("The files can be found at '%s'", this.getDataFolder().getPath()));
		this.getLogger().log(Level.INFO, "Copy these files with their respective directories over to the MockBukkit resources folder.");

	}

	private List<DataGenerator> getDataGenerators()
	{
		return List.of(new KeyedDataGenerator(this.getDataFolder()), new InternalTagDataGenerator(this.getDataFolder()),new PotionDataGenerator(this.getDataFolder()));
	}

}
