package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Horse;
import org.bukkit.entity.SkeletonHorse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SkeletonHorseMock extends AbstractHorseMock implements SkeletonHorse
{

	private boolean isTrapped = false;
	private int trapTime = 0;

	public SkeletonHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isTrapped()
	{
		return this.isTrapped;
	}

	@Override
	public void setTrapped(boolean trapped)
	{
		this.isTrapped = trapped;
	}

	@Override
	public int getTrapTime()
	{
		return this.trapTime;
	}

	@Override
	public void setTrapTime(int trapTime)
	{
		this.trapTime = trapTime;
	}

	@Override
	@Deprecated
	public boolean isTrap()
	{
		return this.isTrapped;
	}

	@Override
	@Deprecated
	public void setTrap(boolean trap)
	{
		this.isTrapped = trap;
	}

	@Override
	@Deprecated
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.SKELETON_HORSE;
	}

}
