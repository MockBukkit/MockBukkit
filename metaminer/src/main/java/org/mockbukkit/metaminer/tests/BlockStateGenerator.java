package org.mockbukkit.metaminer.tests;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class BlockStateGenerator implements DataGenerator
{

	private static final Logger LOG = LoggerFactory.getLogger(BlockStateGenerator.class);

	private final File folder;

	public BlockStateGenerator(File testFolder)
	{
		this.folder = Objects.requireNonNull(testFolder, "test folder cannot be null");
	}

	@Override
	public void generateData() throws IOException
	{
		World world = Bukkit.getWorlds().get(0);
		world.setType(0, 63, 0, Material.BEDROCK);

		File blockFolder = new File(folder, "blocks");
		File output = new File(blockFolder, "block_states.csv");
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
					var block = world.getBlockAt(0, 64, 0);
					block.setType(material);
					@NotNull BlockState state = block.getState();
					@NotNull Class<?>[] interfaces = state.getClass().getInterfaces();
					if (interfaces.length == 0)
					{
						continue;
					}

					String className = interfaces[0].getName();
					writer.println(String.format("%s, %s", material.name(), className));
				}
				catch (Exception e)
				{
					LOG.error("Error while processing material {}", material.name(), e);
				}
			}

		}
	}

}
