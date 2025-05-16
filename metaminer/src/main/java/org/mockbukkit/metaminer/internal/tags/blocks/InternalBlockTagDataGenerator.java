package org.mockbukkit.metaminer.internal.tags.blocks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class InternalBlockTagDataGenerator implements DataGenerator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(InternalBlockTagDataGenerator.class);

	private final File workDirectory;

	public InternalBlockTagDataGenerator(File dataFolder)
	{
		this.workDirectory = new File(dataFolder, "blocks");
	}

	@Override
	public void generateData() throws IOException
	{
		if (!this.workDirectory.exists() && !this.workDirectory.mkdirs())
		{
			throw new IOException("Could not create directory: " + this.workDirectory);
		}
		JsonArray isSolidValues = new JsonArray();
		JsonArray isNonSolidValues = new JsonArray();
		Block block = new Location(Bukkit.getWorlds().get(0), 10, 10, 10).getBlock();
		for (Material material : Material.values())
		{
			if (!material.isBlock())
			{
				continue;
			}

			try
			{
				block.setType(material);
			}
			catch (IllegalArgumentException e)
			{
				LOGGER.error("Error while processing Material {}.", material, e);
				continue;
			}

			if (block.isSolid())
			{
				isSolidValues.add(material.getKey().toString());
			}
			else
			{
				isNonSolidValues.add(material.getKey().toString());
			}
		}
		JsonObject isSolid = new JsonObject();
		isSolid.add("values", isSolidValues);
		JsonUtil.dump(isSolid, new File(workDirectory, "solid_blocks.json"));
		JsonObject isNonSolid = new JsonObject();
		isNonSolid.add("values", isNonSolidValues);
		JsonUtil.dump(isNonSolid, new File(workDirectory, "non_solid_blocks.json"));
	}

}
