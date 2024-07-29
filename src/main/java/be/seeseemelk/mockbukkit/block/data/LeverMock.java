package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.block.state.BlockStateMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.world.InteractionResult;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Switch;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.FACE;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.FACING;
import static be.seeseemelk.mockbukkit.block.data.BlockDataKey.POWERED;

/**
 * Mock implementation of a {@link Switch}.
 */
public class LeverMock extends BlockDataMock implements Switch
{

	/**
	 * Constructs a new {@link LeverMock} for the provided {@link Material}. Only
	 * supports material {@link Material#LEVER}
	 *
	 * @param type The material this data is for.
	 */
	public LeverMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Material.LEVER);
		super.set(FACE, AttachedFace.WALL);
		super.set(FACING, BlockFace.NORTH);
		super.set(POWERED, false);
	}

	@Override
	public boolean isPowered()
	{
		return super.get(POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		super.set(POWERED, powered);
	}

	@Override
	public @NotNull AttachedFace getAttachedFace()
	{
		return super.get(FACE);
	}

	@Override
	public void setAttachedFace(@NotNull AttachedFace face)
	{
		Preconditions.checkNotNull(face, "AttachedFace cannot be null");
		super.set(FACE, face);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return super.get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkNotNull(facing, "BlockFace cannot be null");
		Preconditions.checkArgument(getFaces().contains(facing), "Invalid face. Must be one of " + getFaces());
		super.set(FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull Face getFace()
	{
		return Face.valueOf(getAttachedFace().name());
	}

	@Override
	public void setFace(@SuppressWarnings("deprecation") @NotNull Face face)
	{
		Preconditions.checkNotNull(face, "BlockFace cannot be null");
		setAttachedFace(AttachedFace.valueOf(face.name()));
	}

	@Override
	public InteractionResult simulateUseWithoutItem(BlockStateMock blockState, Location location, PlayerMock playerMock, BlockFace clickedFace)
	{
		boolean powered = isPowered();
		Block block = blockState.getWorld().getBlockAt(location);

		int old = powered ? 15 : 0;
		int current = !powered ? 15 : 0;

		BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, old, current);
		playerMock.getServer().getPluginManager().callEvent(eventRedstone);

		if ((eventRedstone.getNewCurrent() > 0) == (powered))
		{
			return InteractionResult.SUCCESS;
		}

		pull(blockState.getWorld(), location);
		return InteractionResult.CONSUME;
	}

	private void pull(World world, Location location)
	{
		setPowered(!isPowered());
		float pitch = isPowered() ? 0.6F : 0.5F;
		world.playSound(location, Sound.BLOCK_LEVER_CLICK, 0.3f, pitch);
	}

}
