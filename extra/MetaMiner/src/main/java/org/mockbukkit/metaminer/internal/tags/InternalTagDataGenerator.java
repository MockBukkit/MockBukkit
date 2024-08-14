package org.mockbukkit.metaminer.internal.tags;

import org.mockbukkit.metaminer.DataGenerator;

import java.io.File;
import java.io.IOException;

public class InternalTagDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public InternalTagDataGenerator(File pluginFolder)
	{
		this.dataFolder = new File(pluginFolder, "internal_tags");
	}

	@Override
	public void generateData() throws IOException
	{
		for (InternalTagType tagType : InternalTagType.values())
		{
			DataGenerator dataGenerator = tagType.getConstructor().apply(dataFolder);
			dataGenerator.generateData();
		}
	}

}
