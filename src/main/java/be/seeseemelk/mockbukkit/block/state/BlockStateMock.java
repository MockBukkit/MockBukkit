package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;
import com.destroystokyo.paper.MaterialTags;
import com.google.common.base.Preconditions;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Mock implementation of a {@link BlockState}.
 * Also manages the creation of new BlockStates with the appropriate mock class.
 */
public class BlockStateMock implements BlockState
{

	private final @NotNull MetadataTable metadataTable;
	private BlockData blockData;
	private @Nullable Block block;
	private Material material;

	/**
	 * Constructs a new {@link BlockStateMock} for the provided {@link Material}.
	 *
	 * @param material The material this state is for.
	 */
	public BlockStateMock(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		this.metadataTable = new MetadataTable();
		this.material = material;
		this.blockData = BlockDataMock.mock(material);
	}

	/**
	 * Constructs a new {@link BlockStateMock} for the provided {@link Block}.
	 *
	 * @param block The block this state is for.
	 */
	protected BlockStateMock(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");
		this.metadataTable = new MetadataTable();
		this.block = block;
		this.material = block.getType();
		this.blockData = BlockDataMock.mock(material);
	}

	/**
	 * Constructs a new {@link BlockStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BlockStateMock(@NotNull BlockStateMock state)
	{
		Preconditions.checkNotNull(state, "BlockStateMock cannot be null");
		this.metadataTable = new MetadataTable(state.metadataTable);
		this.material = state.getType();
		this.block = state.isPlaced() ? state.getBlock() : null;
		this.blockData = state.blockData.clone();
	}

	// region Type Checking

	/**
	 * Ensures the provided material is one of the expected materials provided.
	 *
	 * @param material The material to test.
	 * @param expected The expected materials.
	 */
	protected void checkType(@NotNull Material material, @NotNull Material @NotNull... expected)
	{
		Preconditions.checkArgument(Arrays.stream(expected).anyMatch(m -> material == m),
				"Cannot create a " + getClass().getSimpleName() + " from " + material);
	}

	/**
	 * Ensures the provided block type is one of the expected materials provided.
	 *
	 * @param block    The block to test.
	 * @param expected The expected materials.
	 */
	protected void checkType(@NotNull Block block, @NotNull Material... expected)
	{
		checkType(block.getType(), expected);
	}

	/**
	 * Ensures the provided material is contained in the {@link Tag}.
	 *
	 * @param material The material to test.
	 * @param expected The expected tag.
	 */
	protected void checkType(@NotNull Material material, @NotNull Tag<Material> expected)
	{
		Preconditions.checkArgument(expected.isTagged(material),
				"Cannot create a " + getClass().getSimpleName() + " from " + material);
	}

	/**
	 * Ensures the provided block type is contained in the {@link Tag}.
	 *
	 * @param block    The material to test.
	 * @param expected The expected tag.
	 */
	protected void checkType(@NotNull Block block, @NotNull Tag<Material> expected)
	{
		checkType(block.getType(), expected);
	}
	// endregion

	@Override
	public void setMetadata(String metadataKey, @NotNull MetadataValue newMetadataValue)
	{
		metadataTable.setMetadata(metadataKey, newMetadataValue);
	}

	@Override
	public @NotNull List<MetadataValue> getMetadata(String metadataKey)
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
	@Deprecated(since = "1.18")
	public org.bukkit.material.@NotNull MaterialData getData()
	{
		return new org.bukkit.material.MaterialData(material);
	}

	@Override
	public @NotNull Material getType()
	{
		return material;
	}

	@Override
	public byte getLightLevel()
	{
		return getBlock().getLightLevel();
	}

	@Override
	public @NotNull World getWorld()
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
	public @NotNull Location getLocation()
	{
		return getBlock().getLocation();
	}

	@Override
	public Location getLocation(Location loc)
	{
		return getBlock().getLocation(loc);
	}

	@Override
	public @NotNull Chunk getChunk()
	{
		return getBlock().getChunk();
	}

	@Override
	@Deprecated(since = "1.18")
	public void setData(@NotNull org.bukkit.material.MaterialData data)
	{
		this.material = data.getItemType();
		this.blockData = BlockDataMock.mock(this.material);
	}

	@Override
	public void setType(Material type)
	{
		this.material = type;
		this.blockData = BlockDataMock.mock(type);
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

		b.setBlockData(blockData);

		if (b instanceof BlockMock bm)
		{
			bm.setState(this);
		}

		return true;
	}

	@Override
	@Deprecated(since = "1.6.2")
	public byte getRawData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(since = "1.6.2")
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
	public @NotNull Collection<ItemStack> getDrops()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<ItemStack> getDrops(@Nullable ItemStack tool)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<ItemStack> getDrops(@NotNull ItemStack tool, @Nullable Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockData getBlockData()
	{
		return this.blockData.clone();
	}

	@Override
	public @NotNull BlockState copy()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBlockData(BlockData data)
	{
		this.material = data.getMaterial();
		this.blockData = data.clone();
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
		hash = prime * hash + (this.getBlockData() != null ? this.getBlockData().hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof BlockStateMock other))
		{
			return false;
		}
		if (this.getBlockData() != other.getBlockData()
				&& (this.getBlockData() == null || !this.getBlockData().equals(other.getBlockData())))
		{
			return false;
		}
		return !this.isPlaced() || this.getLocation() == other.getLocation()
				|| (this.getLocation() != null && this.getLocation().equals(other.getLocation()));

	}

	/**
	 * Attempts to construct a BlockStateMock by the provided block.
	 * Will return a basic {@link BlockStateMock} if no implementation is found.
	 *
	 * @param block The block to create the BlockState from.
	 * @return The BlockState.
	 */
	@NotNull
	public static BlockStateMock mockState(@NotNull Block block)
	{
		// Special cases
		if (Tag.BANNERS.isTagged(block.getType()))
		{
			return new BannerMock(block);
		}
		else if (MaterialTags.SHULKER_BOXES.isTagged(block.getType()))
		{
			return new ShulkerBoxMock(block);
		}
		else if (MaterialTags.SIGNS.isTagged(block.getType()))
		{
			return new SignMock(block);
		}
		else if (MaterialTags.BEDS.isTagged(block))
		{
			return new BedMock(block);
		}
		else if (MaterialTags.SKULLS.isTagged(block))
		{
			return new SkullMock(block);
		}
		switch (block.getType())
		{
		case STRUCTURE_BLOCK:
			return new StructureMock(block);
		case SMOKER:
			return new SmokerMock(block);
		case END_GATEWAY:
			return new EndGatewayMock(block);
		case SCULK_CATALYST:
			return new SculkCatalystMock(block);
		case SCULK_SHRIEKER:
			return new SculkShriekerMock(block);
		case SCULK_SENSOR:
			return new SculkSensorMock(block);
		case BEACON:
			return new BeaconMock(block);
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
		case COMMAND_BLOCK, CHAIN_COMMAND_BLOCK, REPEATING_COMMAND_BLOCK:
			return new CommandBlockMock(block);
		case CAMPFIRE, SOUL_CAMPFIRE:
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
		case CHEST, TRAPPED_CHEST:
			return new ChestMock(block);
		case ENDER_CHEST:
			return new EnderChestMock(block);
		default:
			return new BlockStateMock(block);
		}
	}

}
