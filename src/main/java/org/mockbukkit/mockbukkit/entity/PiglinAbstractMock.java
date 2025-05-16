package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.World;
import org.bukkit.entity.PiglinAbstract;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link PiglinAbstract}.
 *
 * @see MonsterMock
 */
public abstract class PiglinAbstractMock extends MonsterMock implements PiglinAbstract
{

	private boolean isImmuneToZombification = false;
	private boolean isBaby = false;
	private int conversionTime = -1;

	/**
	 * Constructs a new {@link PiglinAbstractMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected PiglinAbstractMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isImmuneToZombification()
	{
		return this.isImmuneToZombification;
	}

	@Override
	public void setImmuneToZombification(boolean immuneToZombification)
	{
		this.isImmuneToZombification = immuneToZombification;
	}

	@Override
	public int getConversionTime()
	{
		Preconditions.checkState(this.isConverting(), "Entity not converting");
		return this.conversionTime;
	}

	@Override
	public void setConversionTime(int time)
	{
		if (time < 0)
		{
			this.conversionTime = -1;
			this.setImmuneToZombification(false);
		}
		else
		{
			this.conversionTime = time;
		}
	}

	@Override
	public boolean isConverting()
	{
		World world = getWorld();
		Preconditions.checkState(world != null, "Entity is not in a world.");
		return !world.isPiglinSafe() && !this.isImmuneToZombification() && this.hasAI();
	}

	@Override
	public boolean isBaby()
	{
		return this.isBaby;
	}

	@Override
	public void setBaby(boolean baby)
	{
		this.isBaby = baby;
	}

	@Override
	public int getAge()
	{
		return isBaby() ? -1 : 0;
	}

	@Override
	public void setAge(int age)
	{
		setBaby(age < 0);
	}

	@Override
	public void setAgeLock(boolean lock)
	{
		// Nothing to do here
	}

	@Override
	public boolean getAgeLock()
	{
		// Nothing to do here
		return false;
	}

	@Override
	public void setBaby()
	{
		setBaby(true);
	}

	@Override
	public void setAdult()
	{
		setBaby(false);
	}

	@Override
	public boolean isAdult()
	{
		return !isBaby();
	}

	@Override
	public boolean canBreed()
	{
		// Nothing to do here
		return false;
	}

	@Override
	public void setBreed(boolean breed)
	{
		// Nothing to do here
	}

}
