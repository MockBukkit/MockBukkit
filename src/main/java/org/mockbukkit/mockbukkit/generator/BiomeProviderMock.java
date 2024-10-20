package org.mockbukkit.mockbukkit.generator;

import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Mock implementation of a {@link BiomeProvider}.
 */
public class BiomeProviderMock extends BiomeProvider
{

	@Override
	public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z)
	{
		if (!(worldInfo instanceof WorldMock world))
			throw new UnsupportedOperationException("Can only get biomes from WorldMock");
		return world.getBiome(x, y, z);
	}

	@Override
	public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo)
	{
		if (!(worldInfo instanceof WorldMock world))
			throw new UnsupportedOperationException("Can only get biomes from WorldMock");
		return List.of(world.getDefaultBiome());
	}

}
