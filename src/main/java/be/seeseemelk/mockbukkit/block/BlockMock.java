package be.seeseemelk.mockbukkit.block;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import be.seeseemelk.mockbukkit.block.state.BlockStateMock;
import be.seeseemelk.mockbukkit.metadata.MetadataTable;
import be.seeseemelk.mockbukkit.tags.internal.InternalTag;
import com.destroystokyo.paper.block.BlockSoundGroup;
import com.google.common.base.Preconditions;
import org.bukkit.Chunk;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.bukkit.util.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mock implementation of a {@link Block}.
 */
public class BlockMock implements Block
{

	private final MetadataTable metadataTable = new MetadataTable();

	private final Location location;
	private BlockStateMock state;
	private Material material;
	private byte data;
	private BlockData blockData;

	private byte lightFromSky = 15;
	private byte lightFromBlocks = 0;

	/**
	 * Creates a basic block made of air.
	 */
	public BlockMock()
	{
		this(Material.AIR);
	}

	/**
	 * Creates a basic block made of air at a certain location.
	 *
	 * @param location The location of the block.
	 */
	public BlockMock(@NotNull Location location)
	{
		this(Material.AIR, location);
		Preconditions.checkNotNull(location, "Location cannot be null");
	}

	/**
	 * Creates a basic block with a given material.
	 *
	 * @param material The material to give the block.
	 */
	public BlockMock(@NotNull Material material)
	{
		this(material, null);
	}

	/**
	 * Creates a basic block with a given material that is also linked to a specific location.
	 *
	 * @param material The material of the block.
	 * @param location The location of the block. Can be {@code null} if not needed.
	 */
	public BlockMock(@NotNull Material material, @Nullable Location location)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		Preconditions.checkArgument(material.isBlock(), "Material has to be a block");
		this.material = material;
		this.location = location;
		this.state = BlockStateMock.mockState(this);
		this.blockData = BlockDataMock.mock(material);
	}

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
	@Deprecated(since = "1.6.2")
	public byte getData()
	{
		return data;
	}

	@Override
	public @NotNull Block getRelative(int modX, int modY, int modZ)
	{
		int x = location.getBlockX() + modX;
		int y = location.getBlockY() + modY;
		int z = location.getBlockZ() + modZ;
		return location.getWorld().getBlockAt(x, y, z);
	}

	@Override
	public @NotNull Block getRelative(@NotNull BlockFace face)
	{
		return getRelative(face, 1);
	}

	@Override
	public @NotNull Block getRelative(@NotNull BlockFace face, int distance)
	{
		Preconditions.checkNotNull(face, "Face cannot be null");
		return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
	}

	/**
	 * Assets that the material type of the block is equal to a given type.
	 *
	 * @param material The material type that the block should have.
	 * @throws AssertionError Thrown if the material type of the block does not equal the given material type.
	 */
	public void assertType(@NotNull Material material)
	{
		if (this.material != material)
		{
			fail(String.format("Block material type is <%s>, but <%s> was expected.", this.material, material));
		}
	}

	@Override
	public @NotNull Material getType()
	{
		return material;
	}

	@Override
	public byte getLightLevel()
	{
		return (byte) Math.max(getLightFromSky(), getLightFromBlocks());
	}

	@Override
	public byte getLightFromSky()
	{
		return lightFromSky;
	}

	/**
	 * Sets the light level received from sky.
	 *
	 * @param lightFromSky Value between 0 and 15.
	 */
	public void setLightFromSky(byte lightFromSky)
	{
		Preconditions.checkArgument(lightFromSky >= 0 && lightFromSky <= 15, "Light level should be between 0 and 15.");

		this.lightFromSky = lightFromSky;
	}

	@Override
	public byte getLightFromBlocks()
	{
		return lightFromBlocks;
	}

	/**
	 * Sets the light level received from other blocks.
	 *
	 * @param lightFromBlocks Value between 0 and 15.
	 */
	public void setLightFromBlocks(byte lightFromBlocks)
	{
		Preconditions.checkArgument(lightFromBlocks >= 0 && lightFromBlocks <= 15,
				"Light level should be between 0 and 15.");

		this.lightFromBlocks = lightFromBlocks;
	}

	@Override
	public @NotNull World getWorld()
	{
		return location.getWorld();
	}

	@Override
	public int getX()
	{
		return location.getBlockX();
	}

	@Override
	public int getY()
	{
		return location.getBlockY();
	}

	@Override
	public int getZ()
	{
		return location.getBlockZ();
	}

	@Override
	@Deprecated(since = "1.18")
	public long getBlockKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Location getLocation()
	{
		return location;
	}

	@Override
	public Location getLocation(@Nullable Location loc)
	{
		if (loc != null)
		{
			loc.setWorld(this.getWorld());
			loc.setX(location.getX());
			loc.setY(location.getY());
			loc.setZ(location.getZ());
			loc.setYaw(0);
			loc.setPitch(0);
		}

		return loc;
	}

	@Override
	public @NotNull Chunk getChunk()
	{
		return location.getWorld().getChunkAt(this);
	}

	@Override
	public void setType(@NotNull Material type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		material = type;
		state = BlockStateMock.mockState(this);
		blockData = BlockDataMock.mock(type);
	}

	@Override
	public void setType(@NotNull Material type, boolean applyPhysics)
	{
		setType(type);
	}

	@Override
	public BlockFace getFace(@NotNull Block block)
	{
		Preconditions.checkNotNull(block, "Block cannot be null");

		for (BlockFace face : BlockFace.values())
		{
			if ((this.getX() + face.getModX() == block.getX()) && (this.getY() + face.getModY() == block.getY())
					&& (this.getZ() + face.getModZ() == block.getZ()))
			{
				return face;
			}
		}

		return null;
	}

	@Override
	public @NotNull BlockState getState()
	{
		// This will always return a snapshot of the BlockState, not the actual state.
		// This is optional with Paper but for Spigot it simply works like that.
		return state.getSnapshot();
	}

	@Override
	public @NotNull Biome getBiome()
	{
		return getWorld().getBiome(getLocation());
	}

	public @NotNull Biome getComputedBiome()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull float getDestroySpeed(@NotNull ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockState getState(boolean useSnapshot)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isValidTool(@NotNull ItemStack itemStack)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setBiome(@NotNull Biome bio)
	{
		Preconditions.checkNotNull(bio, "Biome cannot be null");
		getWorld().setBiome(getLocation(), bio);
	}

	@Override
	public boolean isBlockPowered()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBlockIndirectlyPowered()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBlockFacePowered(@NotNull BlockFace face)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isBlockFaceIndirectlyPowered(@NotNull BlockFace face)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getBlockPower(@NotNull BlockFace face)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getBlockPower()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isEmpty()
	{
		return material.isAir();
	}

	@Override
	public boolean isBurnable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isSolid()
	{
		if (InternalTag.SOLID_BLOCKS.isTagged(this.getType()))
		{
			return true;
		}
		else if (InternalTag.NON_SOLID_BLOCKS.isTagged(this.getType()))
		{
			return false;
		}
		throw new UnimplementedOperationException("Block type '" + this.getType() + "' has not been implemented yet");
	}

	@Override
	public boolean isCollidable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isReplaceable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean isLiquid()
	{
		return material == Material.LAVA || material == Material.WATER || material == Material.BUBBLE_COLUMN;
	}

	@Override
	public boolean isBuildable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	@Deprecated(forRemoval = true)
	public @NotNull BlockSoundGroup getSoundGroup()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public @NotNull SoundGroup getBlockSoundGroup()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	@Deprecated(forRemoval = true)
	public @NotNull String getTranslationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull float getDestroySpeed(@NotNull ItemStack itemStack, boolean considerEnchants)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean breakNaturally(@NotNull ItemStack tool, boolean triggerEffect)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void fluidTick()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void randomTick()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean breakNaturally(boolean triggerEffect)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean breakNaturally(@NotNull ItemStack tool, boolean triggerEffect, boolean dropExperience)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public boolean breakNaturally(boolean triggerEffect, boolean dropExperience)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public double getTemperature()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public double getHumidity()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PistonMoveReaction getPistonMoveReaction()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean breakNaturally()
	{
		if (this.isEmpty())
		{
			return false;
		}
		this.setType(Material.AIR);
		return true;
	}

	@Override
	public boolean breakNaturally(@Nullable ItemStack tool)
	{
		return this.breakNaturally();
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
	public @NotNull BlockData getBlockData()
	{
		return blockData;
	}

	@Override
	public void setBlockData(@NotNull BlockData data)
	{
		this.material = data.getMaterial();
		this.blockData = data;
	}

	@Override
	public void setBlockData(@NotNull BlockData data, boolean applyPhysics)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPassable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public RayTraceResult rayTrace(@NotNull Location start, @NotNull Vector direction, double maxDistance,
			@NotNull FluidCollisionMode fluidCollisionMode)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BoundingBox getBoundingBox()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@NotNull
	@Override
	public VoxelShape getCollisionShape()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<ItemStack> getDrops(@NotNull ItemStack tool, Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * This method sets the current {@link BlockState} to the provided {@link BlockStateMock}.
	 * <strong>Do not call this method directly, use {@link BlockState#update()} instead.</strong>
	 *
	 * @param state The {@link BlockState} that should be set.
	 */
	public void setState(@NotNull BlockStateMock state)
	{
		Preconditions.checkNotNull(state, "The BlockState cannot be null");

		this.state = state;
	}

	@Override
	public boolean applyBoneMeal(@NotNull BlockFace face)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isPreferredTool(@NotNull ItemStack tool)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public float getBreakSpeed(@NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canPlace(@NotNull BlockData data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String translationKey()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
