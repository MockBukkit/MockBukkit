package org.mockbukkit.metaminer.tests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class BlockDataGenerator implements DataGenerator
{
	private static final Logger LOG = LoggerFactory.getLogger(BlockDataGenerator.class);

	private final File folder;

	public BlockDataGenerator(File testFolder)
	{
		this.folder = Objects.requireNonNull(testFolder, "test folder cannot be null");
	}

	@Override
	public void generateData() throws IOException
	{
		World world = Bukkit.getWorlds().get(0);
		world.setType(0, 63, 0, Material.BEDROCK);

		File blockFolder = new File(folder, "blocks");
		File output = new File(blockFolder, "block_data_as_string.csv");
		blockFolder.mkdirs();

		try (PrintWriter writer = new PrintWriter(output))
		{
			for (Material material : Registry.MATERIAL)
			{
				if (!material.isBlock())
				{
					continue;
				}

				try
				{
					@NotNull BlockData data = material.createBlockData();
					String value = data.getAsString(false);
					writer.println(String.format("%s, \"%s\"", material.name(), value));
				}
				catch (Exception e)
				{
					LOG.error("Error while processing material {}", material.name(), e);
				}
			}

		}
	}

}
