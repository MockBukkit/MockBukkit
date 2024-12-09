package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.metadata.MetadataTable;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;
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
	protected void checkType(@NotNull Material material, @NotNull Material @NotNull ... expected)
	{
		Preconditions.checkArgument(Arrays.stream(expected).anyMatch(m -> material == m), "Cannot create a " + getClass().getSimpleName() + " from " + material);
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
		Preconditions.checkArgument(expected.isTagged(material), "Cannot create a " + getClass().getSimpleName() + " from " + material);
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

	/**
	 * This returns a copy of this {@link BlockStateMock}. Inheriters of this class must override this method!
	 *
	 * @return A copy of this {@link BlockStateMock}.
	 */
	@Override
	public @NotNull BlockStateMock copy()
	{
		if (this.getClass() != BlockStateMock.class)
		{
			throw new UnimplementedOperationException(this.getClass().getSimpleName() +
					" does not provide a .copy() implementation! This is a bug.");
		}
		return new BlockStateMock(this);
	}

	@Override
	public @NotNull BlockState copy(@NotNull Location location)
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
	 * This returns a copy of this {@link BlockStateMock}. Inheriters of this class must override this method!
	 *
	 * @return A snapshot of this {@link BlockStateMock}.
	 */
	@NotNull
	public BlockStateMock getSnapshot()
	{
		if (this.getClass() != BlockStateMock.class)
		{
			throw new UnimplementedOperationException(this.getClass().getSimpleName() +
					" does not provide a .getSnapshot() implementation! This is a bug.");
		}
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
		if (this.getBlockData() != other.getBlockData() && (this.getBlockData() == null || !this.getBlockData().equals(other.getBlockData())))
		{
			return false;
		}
		return !this.isPlaced() || this.getLocation() == other.getLocation() || (this.getLocation() != null && this.getLocation().equals(other.getLocation()));

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
			return new BannerStateMock(block);
		}
		else if (MaterialTags.SHULKER_BOXES.isTagged(block.getType()))
		{
			return new ShulkerBoxStateMock(block);
		}
		else if (MaterialTags.SIGNS.isTagged(block.getType()))
		{
			return new SignStateMock(block);
		}
		else if (MaterialTags.BEDS.isTagged(block))
		{
			return new BedStateMock(block);
		}
		else if (MaterialTags.SKULLS.isTagged(block))
		{
			return new SkullStateMock(block);
		}

		return switch (block.getType())
		{
			case STRUCTURE_BLOCK -> new StructureStateMock(block);
			case SMOKER -> new SmokerStateMock(block);
			case END_GATEWAY -> new EndGatewayStateMock(block);
			case SCULK_CATALYST -> new SculkCatalystStateMock(block);
			case SCULK_SHRIEKER -> new SculkShriekerStateMock(block);
			case SCULK_SENSOR -> new SculkSensorStateMock(block);
			case BEACON -> new BeaconStateMock(block);
			case BEEHIVE -> new BeehiveStateMock(block);
			case BREWING_STAND -> new BrewingStandStateMock(block);
			case BLAST_FURNACE -> new BlastFurnaceStateMock(block);
			case COMPARATOR -> new ComparatorStateMock(block);
			case CONDUIT -> new ConduitStateMock(block);
			case ENCHANTING_TABLE -> new EnchantingTableStateMock(block);
			case JIGSAW -> new JigsawStateMock(block);
			case JUKEBOX -> new JukeboxStateMock(block);
			case SPAWNER -> new CreatureSpawnerStateMock(block);
			case DAYLIGHT_DETECTOR -> new DaylightDetectorStateMock(block);
			case COMMAND_BLOCK, CHAIN_COMMAND_BLOCK, REPEATING_COMMAND_BLOCK -> new CommandBlockStateMock(block);
			case CAMPFIRE, SOUL_CAMPFIRE -> new CampfireStateMock(block);
			case BELL -> new BellStateMock(block);
			case LECTERN -> new LecternStateMock(block);
			case HOPPER -> new HopperStateMock(block);
			case BARREL -> new BarrelStateMock(block);
			case DISPENSER -> new DispenserStateMock(block);
			case DROPPER -> new DropperStateMock(block);
			case CHEST, TRAPPED_CHEST -> new ChestStateMock(block);
			case ENDER_CHEST -> new EnderChestStateMock(block);
			default -> new BlockStateMock(block);
		};
	}

	@NotNull
	public static BlockStateMock mockState(@NotNull Material material)
	{
		// Special cases
		if (Tag.BANNERS.isTagged(material))
		{
			return new BannerStateMock(material);
		}
		else if (MaterialTags.SHULKER_BOXES.isTagged(material))
		{
			return new ShulkerBoxStateMock(material);
		}
		else if (MaterialTags.SIGNS.isTagged(material))
		{
			return new SignStateMock(material);
		}
		else if (MaterialTags.BEDS.isTagged(material))
		{
			return new BedStateMock(material);
		}
		else if (MaterialTags.SKULLS.isTagged(material))
		{
			return new SkullStateMock(material);
		}

		return switch (material)
		{
			case STRUCTURE_BLOCK -> new StructureStateMock(material);
			case SMOKER -> new SmokerStateMock(material);
			case END_GATEWAY -> new EndGatewayStateMock(material);
			case SCULK_CATALYST -> new SculkCatalystStateMock(material);
			case SCULK_SHRIEKER -> new SculkShriekerStateMock(material);
			case SCULK_SENSOR -> new SculkSensorStateMock(material);
			case BEACON -> new BeaconStateMock(material);
			case BEEHIVE -> new BeehiveStateMock(material);
			case BREWING_STAND -> new BrewingStandStateMock(material);
			case BLAST_FURNACE -> new BlastFurnaceStateMock(material);
			case COMPARATOR -> new ComparatorStateMock(material);
			case CONDUIT -> new ConduitStateMock(material);
			case ENCHANTING_TABLE -> new EnchantingTableStateMock(material);
			case JIGSAW -> new JigsawStateMock(material);
			case JUKEBOX -> new JukeboxStateMock(material);
			case SPAWNER -> new CreatureSpawnerStateMock(material);
			case DAYLIGHT_DETECTOR -> new DaylightDetectorStateMock(material);
			case COMMAND_BLOCK, CHAIN_COMMAND_BLOCK, REPEATING_COMMAND_BLOCK -> new CommandBlockStateMock(material);
			case CAMPFIRE, SOUL_CAMPFIRE -> new CampfireStateMock(material);
			case BELL -> new BellStateMock(material);
			case LECTERN -> new LecternStateMock(material);
			case HOPPER -> new HopperStateMock(material);
			case BARREL -> new BarrelStateMock(material);
			case DISPENSER -> new DispenserStateMock(material);
			case DROPPER -> new DropperStateMock(material);
			case CHEST, TRAPPED_CHEST -> new ChestStateMock(material);
			case ENDER_CHEST -> new EnderChestStateMock(material);
			default -> new BlockStateMock(material);
		};
	}

}
