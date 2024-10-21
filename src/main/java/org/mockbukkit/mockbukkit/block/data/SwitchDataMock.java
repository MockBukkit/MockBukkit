package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Switch;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACE;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.POWERED;

/**
 * Mock implementation of a {@link Switch}.
 */
public class SwitchDataMock extends BlockDataMock implements Switch
{

	/**
	 * Constructs a new {@link SwitchDataMock} for the provided {@link Material}. Only
	 * supports materials in {@link Tag#BUTTONS} and {@link Material#LEVER}
	 *
	 * @param type The material this data is for.
	 */
	public SwitchDataMock(@NotNull Material type)
	{
		super(type);
		Set<Material> possibleTypes = new HashSet<>(Tag.BUTTONS.getValues());
		possibleTypes.add(Material.LEVER);
		checkType(type, possibleTypes.toArray(new Material[0]));
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

}
