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
import org.mockbukkit.metaminer.tests.ItemStackTestDataGenerator;
import org.mockbukkit.metaminer.translation.TranslationDataGenerator;

import java.io.File;
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
		getServer().shutdown(); // We're done. So just call it quits.
	}

	private List<DataGenerator> getDataGenerators()
	{
		File testFolder = new File(this.getDataFolder(), "src/test/resources");
		File mainFolder = new File(this.getDataFolder(), "src/main/resources");
		return List.of(new KeyedDataGenerator(mainFolder), new InternalTagDataGenerator(mainFolder),
				new PotionDataGenerator(mainFolder), new TagDataGenerator(mainFolder),
				new MaterialDataGenerator(mainFolder), new RegistryKeyClassDataGenerator(mainFolder),
				new MaterialDataGenerator(mainFolder), new TranslationDataGenerator(this.getDataFolder()),
				new RegistryKeyClassDataGenerator(mainFolder), new ItemStackTestDataGenerator(testFolder));
	}

}
