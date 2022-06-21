package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class BlockStateMock implements BlockState
{

	private final MetadataTable metadataTable;
	private Block block;
	private Material material;

	public BlockStateMock(@NotNull Material material)
	{
		this.metadataTable = new MetadataTable();
		this.material = material;
	}

	protected BlockStateMock(@NotNull Block block)
	{
		this.metadataTable = new MetadataTable();
		this.block = block;
		this.material = block.getType();
	}

	protected BlockStateMock(@NotNull BlockStateMock state)
	{
		this.metadataTable = new MetadataTable(state.metadataTable);
		this.material = state.getType();
		this.block = state.isPlaced() ? state.getBlock() : null;
	}

	@Override
	public void setMetadata(String metadataKey, MetadataValue newMetadataValue)
	{
		metadataTable.setMetadata(metadataKey, newMetadataValue);
	}

	@Override
	public List<MetadataValue> getMetadata(String metadataKey)
	{
		return metadataTable.getMetadata(metadataKey);
	}

	@Override
	public boolean hasMetadata(String metadataKey)
	{
		return metadataTable.hasMetadata(metadataKey);
	}

	@Override
	public void removeMetadata(String metadataKey, Plugin owningPlugin)
	{
		metadataTable.removeMetadata(metadataKey, owningPlugin);
	}

	@Override
	public @NotNull Block getBlock()
	{
		if (block == null)
		{
			throw new IllegalStateException("This BlockState has not been placed!");
		}
		else
		{
			return block;
		}
	}

	@Override
	@Deprecated
	public org.bukkit.material.MaterialData getData()
	{
		return new org.bukkit.material.MaterialData(material);
	}

	@Override
	public Material getType()
	{
		return material;
	}

	@Override
	public byte getLightLevel()
	{
		return getBlock().getLightLevel();
	}

	@Override
	public World getWorld()
	{
		return getBlock().getWorld();
	}

	@Override
	public int getX()
	{
		return getBlock().getX();
	}

	@Override
	public int getY()
	{
		return getBlock().getY();
	}

	@Override
	public int getZ()
	{
		return getBlock().getZ();
	}

	@Override
	public Location getLocation()
	{
		return getBlock().getLocation();
	}

	@Override
	public Location getLocation(Location loc)
	{
		return getBlock().getLocation(loc);
	}

	@Override
	public Chunk getChunk()
	{
		return getBlock().getChunk();
	}

	@Override
	@Deprecated
	public void setData(@NotNull org.bukkit.material.MaterialData data)
	{
		this.material = data.getItemType();
	}

	@Override
	public void setType(Material type)
	{
		this.material = type;
	}

	@Override
	public boolean update()
	{
		return update(false);
	}

	@Override
	public boolean update(boolean force)
	{
		return update(force, true);
	}

	@Override
	public boolean update(boolean force, boolean applyPhysics)
	{
		if (!isPlaced())
		{
			return true;
		}

		Block b = getBlock();

		if (b.getType() != this.getType() && !force)
		{
			return false;
		}

		b.setType(this.getType());

		if (b instanceof BlockMock bm)
		{
			bm.setState(this);
		}

		return true;
	}

	@Override
	@Deprecated
	public byte getRawData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated
	public void setRawData(byte data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPlaced()
	{
		return block != null;
	}

	@Override
	public boolean isCollidable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public BlockData getBlockData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBlockData(BlockData data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * This returns a copy of this {@link BlockStateMock}. Inheritents of this class should override this method!
	 *
	 * @return A snapshot of this {@link BlockStateMock}.
	 */
	@NotNull
	public BlockState getSnapshot()
	{
		return new BlockStateMock(this);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = 1;
		hash = prime * hash + (this.isPlaced() ? this.getWorld().hashCode() : 0);
		hash = prime * hash + (this.isPlaced() ? this.getLocation().hashCode() : 0);
//		hash = prime * hash + (this.getBlockData() != null ? this.getBlockData().hashCode() : 0); Not implemented
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BlockStateMock other))
		{
			return false;
		}
		if (this.isPlaced() && this.getWorld() != other.getWorld() && (this.getWorld() == null || !this.getWorld().equals(other.getWorld()))) {
			return false;
		}
		if (this.isPlaced() && this.getLocation() != other.getLocation() && (this.getLocation() == null || !this.getLocation().equals(other.getLocation()))) {
			return false;
		}
//		if (this.getBlockData() != other.getBlockData() && (this.getBlockData() == null || !this.getBlockData().equals(other.getBlockData()))) {
//			return false; Not implemented
//		}
		return true;
	}

	@NotNull
	public static BlockStateMock mockState(@NotNull Block block)
	{
		// Special cases
		if (Tag.BANNERS.isTagged(block.getType()))
		{
			return new BannerMock(block);
		}
		else if (Tag.SHULKER_BOXES.isTagged(block.getType()))
		{
			return new ShulkerBoxMock(block);
		}
		else if (Tag.SIGNS.isTagged(block.getType()))
		{
			return new SignMock(block);
		}
		switch (block.getType())
		{
		case BEEHIVE:
			return new BeehiveMock(block);
		case BREWING_STAND:
			return new BrewingStandMock(block);
		case BLAST_FURNACE:
			return new BlastFurnaceMock(block);
		case COMPARATOR:
			return new ComparatorMock(block);
		case CONDUIT:
			return new ConduitMock(block);
		case ENCHANTING_TABLE:
			return new EnchantingTableMock(block);
		case JIGSAW:
			return new JigsawMock(block);
		case JUKEBOX:
			return new JukeboxMock(block);
		case SPAWNER:
			return new CreatureSpawnerMock(block);
		case DAYLIGHT_DETECTOR:
			return new DaylightDetectorMock(block);
		case COMMAND_BLOCK:
		case CHAIN_COMMAND_BLOCK:
		case REPEATING_COMMAND_BLOCK:
			return new CommandBlockMock(block);
		case CAMPFIRE:
		case SOUL_CAMPFIRE:
			return new CampfireMock(block);
		case BELL:
			return new BellMock(block);
		case LECTERN:
			return new LecternMock(block);
		case HOPPER:
			return new HopperMock(block);
		case BARREL:
			return new BarrelMock(block);
		case DISPENSER:
			return new DispenserMock(block);
		case DROPPER:
			return new DropperMock(block);
		case CHEST:
		case TRAPPED_CHEST:
			return new ChestMock(block);
		case ENDER_CHEST:
			return new EnderChestMock(block);
		default:
			return new BlockStateMock(block);
		}
	}

}
