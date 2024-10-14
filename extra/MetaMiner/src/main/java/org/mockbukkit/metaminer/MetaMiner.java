package org.mockbukkit.metaminer;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.mockbukkit.metaminer.internal.MaterialDataGenerator;
import org.mockbukkit.metaminer.internal.potion.PotionDataGenerator;
import org.mockbukkit.metaminer.internal.tags.InternalTagDataGenerator;
import org.mockbukkit.metaminer.keyed.KeyedDataGenerator;
import org.mockbukkit.metaminer.keyed.RegistryKeyClassDataGenerator;
import org.mockbukkit.metaminer.tags.TagDataGenerator;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

public class MetaMiner extends JavaPlugin
{

	@Override
	public void onEnable()
	{
		this.getLogger().log(Level.INFO, "Generating data for MockBukkit");
		try
		{
			FileUtils.deleteDirectory(this.getDataFolder());
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
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
		this.getLogger().log(Level.INFO, Bukkit.getServer().createBlockData(Material.LEGACY_WOOD).getMaterial().toString());
		getServer().shutdown(); // We're done. So just call it quits.
	}

	private List<DataGenerator> getDataGenerators()
	{
		return List.of(new KeyedDataGenerator(this.getDataFolder()), new InternalTagDataGenerator(this.getDataFolder()),
				new PotionDataGenerator(this.getDataFolder()), new TagDataGenerator(this.getDataFolder()),
				new MaterialDataGenerator(this.getDataFolder()), new RegistryKeyClassDataGenerator(this.getDataFolder()));
	}

}
