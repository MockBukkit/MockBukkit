package be.seeseemelk.mockbukkit.generator;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BiomeProviderMock extends BiomeProvider
{

	@Override
	public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z)
	{
		ServerMock server = (ServerMock) Bukkit.getServer();
		return server.getWorld(worldInfo.getUID()).getBiome(x, y, z);
	}

	@Override
	public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo)
	{
		ServerMock server = (ServerMock) Bukkit.getServer();
		return List.of(((WorldMock) server.getWorld(worldInfo.getUID())).getDefaultBiome());
	}

}
