package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.MultipleFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MultipleFacingDataMock extends BlockDataMock implements MultipleFacing
{

	public MultipleFacingDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected MultipleFacingDataMock(@NotNull MultipleFacingDataMock other)
	{
		super(other);
	}

	@Override
	public boolean hasFace(@NotNull BlockFace face)
	{
		Preconditions.checkArgument(getAllowedFaces().contains(face), "Illegal facing: " + face);
		return toKey(face).map(super::get).map(object -> (boolean) object).orElse(false);
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
		return getAllowedFaces().stream().filter(this::hasFace).collect(Collectors.toSet());
	}

	@Override
	public @NotNull Set<BlockFace> getAllowedFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	@SuppressWarnings({ "MethodDoesntCallSuperMethod", "java:S2975", "java:S1182" })
	public @NotNull MultipleFacingDataMock clone()
	{
		return new MultipleFacingDataMock(this);
	}

	public static Optional<BlockDataKey> toKey(@Nullable BlockFace blockFace)
	{
		return switch (blockFace)
		{
			case null -> Optional.empty();
			case NORTH -> Optional.of(BlockDataKey.NORTH);
			case SOUTH -> Optional.of(BlockDataKey.SOUTH);
			case EAST -> Optional.of(BlockDataKey.EAST);
			case WEST -> Optional.of(BlockDataKey.WEST);
			case UP -> Optional.of(BlockDataKey.UP);
			case DOWN -> Optional.of(BlockDataKey.DOWN);
			default -> throw new UnsupportedOperationException(String.format("Unsupported block facing: %s", blockFace));
		};
	}

}
