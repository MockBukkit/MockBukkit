package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of a {@link ChunkGenerator.ChunkData}.
 */
public class MockChunkData implements ChunkGenerator.ChunkData
{

	private final @NotNull Map<Coordinate, BlockData> blocks;
	private final @NotNull Map<Coordinate, Biome> biomes;

	private final Biome defaultBiome;

	private final int minHeight;
	private final int maxHeight;

	/**
	 * Constructs a new {@link MockChunkData} for the provided {@link World}.
	 *
	 * @param world The world the chunk is in.
	 */
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
		Preconditions.checkNotNull(material, "Material cannot be null");
		this.setBlock(x, y, z, BlockDataMock.mock(material));
	}

	@Override
	@Deprecated(since = "1.18")
	public void setBlock(int x, int y, int z, @NotNull MaterialData material)
	{
		Preconditions.checkNotNull(material, "MaterialData cannot be null");
		this.setBlock(x, y, z, BlockDataMock.mock(material.getItemType()));
	}

	@Override
	public void setBlock(int x, int y, int z, @NotNull BlockData blockData)
	{
		checkCoords(x, y, z);
		blocks.put(new Coordinate(x, y, z), blockData);
	}

	@Override
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, BlockDataMock.mock(material));
	}

	@Override
	@Deprecated(since = "1.18")
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull MaterialData material)
	{
		Preconditions.checkNotNull(material, "MaterialData cannot be null");
		this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, BlockDataMock.mock(material.getItemType()));
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
		checkCoords(x, y, z);

		BlockData data = blocks.get(new Coordinate(x, y, z));
		// shortcut to return air directly instead of creating air block data then
		// unpacking material
		return data == null ? Material.AIR : data.getMaterial();
	}

	@NotNull
	@Override
	@Deprecated(since = "1.18")
	public MaterialData getTypeAndData(int x, int y, int z)
	{
		return new MaterialData(this.getType(x, y, z));
	}

	@NotNull
	@Override
	public BlockData getBlockData(int x, int y, int z)
	{
		checkCoords(x, y, z);

		BlockData data = blocks.get(new Coordinate(x, y, z));
		return data == null ? new BlockDataMock(Material.AIR) : data;
	}

	@Override
	@Deprecated(since = "1.8.8")
	public byte getData(int x, int y, int z)
	{
		return this.getTypeAndData(x, y, z).getData();
	}

	/**
	 * Ensures that the X and Y coordinates are within the chunks 0-15 range, and the height is withing the min and max height.
	 *
	 * @param x The X coordinate.
	 * @param y The Y coordinate.
	 * @param z The Z coordinate.
	 */
	private void checkCoords(int x, int y, int z)
	{
		Preconditions.checkArgument(x == (x & 0xf) && y >= this.minHeight && y < this.maxHeight && z == (z & 0xf),
				"Coordinates are out-of-bounds");
	}

}
