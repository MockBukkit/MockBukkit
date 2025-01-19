package org.mockbukkit.metaminer.tests;

import org.mockbukkit.metaminer.DataGenerator;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public class ItemStackTestDataGenerator implements DataGenerator
{

	private final File folder;

	public ItemStackTestDataGenerator(File pluginDataFolder)
	{
		this.folder = new File(pluginDataFolder, "itemstack");
	}

	@Override
	public void generateData() throws IOException
	{
		getGenerators().forEach(dataGenerator ->
		{
			try
			{
				dataGenerator.generateData();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
	}

	private Stream<DataGenerator> getGenerators()
	{
		return Stream.of(new ItemStackSetTypeTestDataGenerator(folder),
				new ItemStackMetaDataGenerator(folder));
	}

}
