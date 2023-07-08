package be.seeseemelk.mockbukkit.generator;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BiomeProviderMockTest
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

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
