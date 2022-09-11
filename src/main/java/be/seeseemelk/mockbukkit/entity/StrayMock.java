package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Stray;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class StrayMock extends AbstractSkeletonMock implements Stray
{

	public StrayMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated
	public Skeleton.@NotNull SkeletonType getSkeletonType()
	{
		return Skeleton.SkeletonType.STRAY;
	}

}
