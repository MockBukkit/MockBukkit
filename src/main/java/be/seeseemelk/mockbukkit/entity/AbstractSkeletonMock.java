package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.AbstractSkeleton;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class AbstractSkeletonMock extends MonsterMock implements AbstractSkeleton
{

	public AbstractSkeletonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setSkeletonType(Skeleton.SkeletonType type)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

}
