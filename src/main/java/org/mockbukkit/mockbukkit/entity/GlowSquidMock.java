package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowSquid;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GlowSquidMock extends SquidMock implements GlowSquid
{

	private int darkTicksRemaining = 0;

	/**
	 * Constructs a new {@link GlowSquidMock}  on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public GlowSquidMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getDarkTicksRemaining()
	{
		return this.darkTicksRemaining;
	}

	@Override
	public void setDarkTicksRemaining(int darkTicksRemaining)
	{
		Preconditions.checkArgument(darkTicksRemaining >= 0, "darkTicksRemaining must be >= 0");
		this.darkTicksRemaining = darkTicksRemaining;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.GLOW_SQUID;
	}

}
