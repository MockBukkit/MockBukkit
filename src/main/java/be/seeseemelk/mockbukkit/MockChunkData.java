package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

public class MockChunkData implements ChunkGenerator.ChunkData {

	private final BlockData[][][] blocks;

	private final int maxHeight;
	private final int minHeight; // unused but will be useful in 1.17/1.18

	public MockChunkData(World world) {
		this.maxHeight = world.getMaxHeight();
		this.minHeight = world.getMinHeight();
		blocks = new BlockData[16][this.maxHeight][16];
	}

	@Override
	public int getMaxHeight() {
		return this.maxHeight;
	}

	@Override
	public void setBlock(int x, int y, int z, @NotNull Material material) {
		this.setBlock(x, y, z, new BlockDataMock(material));
	}

	@Override
	public void setBlock(int x, int y, int z, @NotNull MaterialData material) {
		this.setBlock(x, y, z, new BlockDataMock(material.getItemType()));
	}

	@Override
	public void setBlock(int x, int y, int z, @NotNull BlockData blockData) {
		try {
			blocks[x][y][z] = blockData;
		} catch (ArrayIndexOutOfBoundsException ignored) {
		}
	}

	@Override
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull Material material) {
		this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, new BlockDataMock(material));
	}

	@Override
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull MaterialData material) {
		this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, new BlockDataMock(material.getItemType()));
	}

	@Override
	public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, @NotNull BlockData blockData) {
		for (int x = xMin; x < xMax; x++) {
			for (int y = yMin; y < yMax; y++) {
				for (int z = zMin; z < zMax; z++) {
					this.setBlock(x, y, z, blockData);
				}
			}
		}
	}

	@NotNull
	@Override
	public Material getType(int x, int y, int z) {
		try {
			BlockData data = blocks[x][y][z];
			// shortcut to return air directly instead of creating air block data then unpacking material
			return data == null ? Material.AIR : data.getMaterial();
		} catch (ArrayIndexOutOfBoundsException e) {
			return Material.AIR;
		}
	}

	@NotNull
	@Override
	public MaterialData getTypeAndData(int x, int y, int z) {
		return new MaterialData(this.getType(x, y, z));
	}

	@NotNull
	@Override
	public BlockData getBlockData(int x, int y, int z) {
		try {
			BlockData data = blocks[x][y][z];
			return data == null ? new BlockDataMock(Material.AIR) : data;
		} catch (ArrayIndexOutOfBoundsException e) {
			return new BlockDataMock(Material.AIR);
		}
	}

	@Override
	public byte getData(int x, int y, int z) {
		return this.getTypeAndData(x, y, z).getData();
	}
}
