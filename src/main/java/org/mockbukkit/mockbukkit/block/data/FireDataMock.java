package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fire;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.AGE_KEY;

public class FireDataMock extends BlockDataMock implements Fire
{

	public FireDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected FireDataMock(@NotNull FireDataMock other)
	{
		super(other);
	}

	@Override
	public int getAge()
	{
		return this.get(AGE_KEY);
	}

	@Override
	public void setAge(int age)
	{
		Preconditions.checkArgument(age >= 0 && age <= this.getMaximumAge(), "The age must be between 0 and %s", this.getMaximumAge());
		this.set(AGE_KEY, age);
	}

	@Override
	public int getMaximumAge()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_AGE);
	}

	@Override
	public boolean hasFace(@NotNull BlockFace face)
	{
		Preconditions.checkArgument(getAllowedFaces().contains(face), "Illegal facing: " + face);
		return MultipleFacingDataMock.toKey(face)
				.map(super::get)
				.map(Boolean.class::cast)
				.orElse(false);
	}

	@Override
	public void setFace(@NotNull BlockFace face, boolean has)
	{
		Preconditions.checkArgument(getAllowedFaces().contains(face), "Illegal facing: " + face);
		MultipleFacingDataMock.toKey(face).ifPresent(faceKey -> super.set(faceKey, has));
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
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull FireDataMock clone()
	{
		return new FireDataMock(this);
	}

}
