package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MockChunkData implements ChunkGenerator.ChunkData
{

	private final Map<Coordinate, BlockData> blocks;
	private final Map<Coordinate, Biome> biomes;

	private final Biome defaultBiome;

	private final int minHeight;
	private final int maxHeight;

	public MockChunkData(@NotNull World world)
	{
		this.minHeight = world.getMinHeight();
		this.maxHeight = world.getMaxHeight();
		blocks = new HashMap<>((15 * 15) * Math.abs((world.getMaxHeight() - world.getMinHeight())), 1.0f);
		if (world instanceof WorldMock mockWorld)
		{
			biomes = mockWorld.getBiomeMap();
			defaultBiome = mockWorld.getDefaultBiome();
		}
		else
		{
			biomes = new HashMap<>(0);
			defaultBiome = Biome.PLAINS;
		}
	}

	@Override
	public int getMinHeight()
	{
		return this.minHeight;
	}

	@Override
	public int getMaxHeight()
	{
		return this.maxHeight;
	}

	@NotNull
	@Override
	public Biome getBiome(int x, int y, int z)
	{
		return biomes.getOrDefault(new Coordinate(x, y, z), defaultBiome);
	}

	@Override
	public void setBlock(int x, int y, int z, @NotNull Material material)
	{
		this.setBlock(x, y, z, new BlockDataMock(material));
	}

	@Override
	public void setBlock(int x, int y, int z, @NotNull MaterialData material)
	{
		this.setBlock(x, y, z, new BlockDataMock(material.getItemType()));
	}

	@Override
	public void setBlock(int x, int y, int z, @NotNull BlockData blockData)
	{
		if (x != (x & 0xf) || y < this.minHeight || y >= this.maxHeight || z != (z & 0xf))
		{
			return;
		}
		blocks.put(new Coordinate(x, y, z), blockData);
	}

	@Override
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull Material material)
	{
		this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, new BlockDataMock(material));
	}

	@Override
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull MaterialData material)
	{
		this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, new BlockDataMock(material.getItemType()));
	}

	@Override
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull BlockData blockData)
	{
		for (int x = xMin; x < xMax; x++)
		{
			for (int y = yMin; y < yMax; y++)
			{
				for (int z = zMin; z < zMax; z++)
				{
					this.setBlock(x, y, z, blockData);
				}
			}
		}
	}

	@NotNull
	@Override
	public Material getType(int x, int y, int z)
	{
		if (x != (x & 0xf) || y < this.minHeight || y >= this.maxHeight || z != (z & 0xf))
		{
			return Material.AIR;
		}

		BlockData data = blocks.get(new Coordinate(x, y, z));
		// shortcut to return air directly instead of creating air block data then unpacking material
		return data == null ? Material.AIR : data.getMaterial();
	}

	@NotNull
	@Override
	public MaterialData getTypeAndData(int x, int y, int z)
	{
		return new MaterialData(this.getType(x, y, z));
	}

	@NotNull
	@Override
	public BlockData getBlockData(int x, int y, int z)
	{
		if (x != (x & 0xf) || y < this.minHeight || y >= this.maxHeight || z != (z & 0xf))
		{
			return new BlockDataMock(Material.AIR);
		}

		BlockData data = blocks.get(new Coordinate(x, y, z));
		return data == null ? new BlockDataMock(Material.AIR) : data;
	}

	@Override
	public byte getData(int x, int y, int z)
	{
		return this.getTypeAndData(x, y, z).getData();
	}

}
