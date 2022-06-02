package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class AbstractSkeletonMock extends MonsterMock implements AbstractSkeleton
{

	private boolean shouldBurnInDay;

	protected AbstractSkeletonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setSkeletonType(Skeleton.SkeletonType type)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public boolean shouldBurnInDay()
	{
		return shouldBurnInDay;
	}

	@Override
	public void setShouldBurnInDay(boolean shouldBurnInDay)
	{
		this.shouldBurnInDay = shouldBurnInDay;
	}

}
