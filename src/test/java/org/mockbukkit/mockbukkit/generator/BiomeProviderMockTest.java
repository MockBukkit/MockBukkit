package org.mockbukkit.mockbukkit.generator;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class BiomeProviderMockTest
{

	@Test
	void getBiome()
	{
		WorldMock world = new WorldMock(Material.DIRT, Biome.BADLANDS, -64, 319, 3);
		assertEquals(Biome.BADLANDS, world.getBiomeProvider().getBiome(world, 0, 0, 0));
		world.setBiome(0, 0, 0, Biome.PLAINS);
		assertEquals(Biome.PLAINS, world.getBiomeProvider().getBiome(world, 0, 0, 0));
	}

	@Test
	void getBiomes()
	{
		WorldMock world = new WorldMock(Material.DIRT, Biome.BADLANDS, -64, 319, 3);
		assertEquals(List.of(Biome.BADLANDS), world.getBiomeProvider().getBiomes(world));
	}

}
