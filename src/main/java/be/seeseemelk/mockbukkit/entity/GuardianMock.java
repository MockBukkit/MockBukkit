package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GuardianMock extends MonsterMock implements Guardian
{

	private boolean elder = false;
	private boolean laser = false;

	public GuardianMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean setLaser(boolean activated)
	{
		return this.laser = activated;
	}

	@Override
	public boolean hasLaser()
	{
		return this.laser;
	}

	@Override
	public boolean isElder()
	{
		return this.elder;
	}

	@Override
	public void setElder(boolean shouldBeElder)
	{
		this.elder = shouldBeElder;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.GUARDIAN;
	}

}
