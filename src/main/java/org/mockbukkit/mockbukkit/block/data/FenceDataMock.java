package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fence;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FenceDataMock extends BlockDataMock implements Fence
{

	/**
	 * Constructs a new {@link FenceDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public FenceDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public boolean hasFace(@NotNull BlockFace face)
	{
		Preconditions.checkArgument(getAllowedFaces().contains(face), "Illegal facing: " + face);
		return toKey(face)
				.map(super::get)
				.map(object -> (boolean) object)
				.orElse(false);
	}

	@Override
	public void setFace(@NotNull BlockFace face, boolean has)
	{
		Preconditions.checkArgument(getAllowedFaces().contains(face), "Illegal facing: " + face);
		toKey(face).ifPresent(faceKey -> super.set(faceKey, has));
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return getAllowedFaces().stream()
				.filter(this::hasFace)
				.collect(Collectors.toSet());
	}

	@Override
	public @NotNull Set<BlockFace> getAllowedFaces()
	{
		return Set.of(BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH);
	}

	@Override
	public boolean isWaterlogged()
	{
		return super.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		super.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	private Optional<BlockDataKey> toKey(BlockFace blockFace)
	{
		try
		{
			return Optional.of(BlockDataKey.valueOf(blockFace.name()));
		}
		catch (IllegalArgumentException e)
		{
			return Optional.empty();
		}
	}

}
