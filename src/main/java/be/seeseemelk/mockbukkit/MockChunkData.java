package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

public class MockChunkData implements ChunkGenerator.ChunkData
{

	private final BlockData[][][] blocks;

	// unused but will be useful in 1.17/1.18
	private final int minHeight;
	private final int maxHeight;

	public MockChunkData(@NotNull World world)
	{
		this.minHeight = world.getMinHeight();
		this.maxHeight = world.getMaxHeight();
		blocks = new BlockData[16][this.maxHeight][16];
	}

	@Override
	public int getMaxHeight()
	{
		return this.maxHeight;
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
		if (x >= 0 && x < 16 &&
		        y >= 0 && y < this.maxHeight &&
		        z >= 0 && z < 16
		   )
		{
			blocks[x][y][z] = blockData;
		}
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
		if (x >= 0 && x < 16 &&
		        y >= 0 && y < this.maxHeight &&
		        z >= 0 && z < 16
		   )
		{
			BlockData data = blocks[x][y][z];
			// shortcut to return air directly instead of creating air block data then unpacking material
			return data == null ? Material.AIR : data.getMaterial();
		}

		return Material.AIR;
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
		if (x >= 0 && x < 16 &&
		        y >= 0 && y < this.maxHeight &&
		        z >= 0 && z < 16
		   )
		{
			BlockData data = blocks[x][y][z];
			return data == null ? new BlockDataMock(Material.AIR) : data;
		}

		return new BlockDataMock(Material.AIR);
	}

	@Override
	public byte getData(int x, int y, int z)
	{
		return this.getTypeAndData(x, y, z).getData();
	}
}
