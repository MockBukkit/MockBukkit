package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zoglin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Zoglin}.
 *
 * @see MonsterMock
 */
public class ZoglinMock extends MonsterMock implements Zoglin
{
	private boolean isAdult = true;

	/**
	 * Constructs a new {@link ZoglinMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ZoglinMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isBaby()
	{
		return !isAdult();
	}

	@Override
	public void setBaby(boolean isBaby)
	{
		if (isBaby)
		{
			this.setBaby();
		} else
		{
			this.setAdult();
		}
	}

	@Override
	public int getAge()
	{
		return this.isBaby() ? -1 : 0;
	}

	@Override
	public void setAge(int age)
	{
		if (age < 0)
		{
			this.setBaby();
		} else
		{
			this.setAdult();
		}
	}

	@Override
	public void setAgeLock(boolean lock)
	{
		// No operation in paper
	}

	@Override
	public boolean getAgeLock()
	{
		// No operation in paper
		return false;
	}

	@Override
	public void setBaby()
	{
		this.isAdult = false;
	}

	@Override
	public void setAdult()
	{
		this.isAdult = true;
	}

	@Override
	public boolean isAdult()
	{
		return this.isAdult;
	}

	@Override
	public boolean canBreed()
	{
		// No operation in paper
		return false;
	}

	@Override
	public void setBreed(boolean breed)
	{
		// No operation in paper
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ZOGLIN;
	}

}
