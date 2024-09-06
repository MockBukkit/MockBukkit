package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LeashHitchMock extends HangingMock implements LeashHitch
{

	/**
	 * Constructs a new {@link LeashHitchMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public LeashHitchMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.LEASH_KNOT;
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return BlockFace.SELF;
	}

	@Override
	public boolean setFacingDirection(@NotNull BlockFace face, boolean force)
	{
		Preconditions.checkNotNull(face, "Face cannot be null");
		Preconditions.checkArgument(face == BlockFace.SELF, "%s is not a valid facing direction");
		return force;
	}

}
