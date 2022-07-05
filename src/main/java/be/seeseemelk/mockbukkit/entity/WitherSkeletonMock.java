package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.WitherSkeleton;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class WitherSkeletonMock extends AbstractSkeletonMock implements WitherSkeleton
{

	public WitherSkeletonMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public Skeleton.@NotNull SkeletonType getSkeletonType()
	{
		return Skeleton.SkeletonType.WITHER;
	}

}
