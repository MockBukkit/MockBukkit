package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.DyeColor;
import org.bukkit.entity.Cushion;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link Cushion}.
 *
 * @see EntityMock
 */
@NullMarked
public class CushionMock extends EntityMock implements Cushion
{
	private @Nullable DyeColor color = null;

	/**
	 * Constructs a new {@link EntityMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CushionMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable DyeColor getColor()
	{
		return this.color;
	}

	@Override
	public void setColor(DyeColor color)
	{
		Preconditions.checkNotNull(color, "color cannot be null");
		this.color = color;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.CUSHION;
	}

}
