package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SlimeMock extends MobMock implements Slime
{

	private int size = 1;
	private boolean canWander = true;

	/**
	 * Constructs a new {@link SlimeMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SlimeMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getSize()
	{
		return this.size;
	}

	@Override
	public void setSize(int size)
	{
		if (size < 1)
		{
			throw new IllegalArgumentException("Size cannot be less than 1");
		}
		if (size > 127)
		{
			throw new IllegalArgumentException("Size cannot be greater than 127");
		}
		if (this.getHealth() > 0)
		{
			this.getAttribute(Attribute.MAX_HEALTH).setBaseValue(size * size);
			this.setHealth(this.getMaxHealth());
		}
		this.size = size;
	}

	@Override
	public boolean canWander()
	{
		return this.canWander;
	}

	@Override
	public void setWander(boolean canWander)
	{
		this.canWander = canWander;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SLIME;
	}

}
