package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SpectralArrow;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SpectralArrowMock extends AbstractArrowMock implements SpectralArrow
{

	private int glowingTicks = 200;

	/**
	 * Constructs a new {@link SpectralArrowMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SpectralArrowMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getGlowingTicks()
	{
		return glowingTicks;
	}

	@Override
	public void setGlowingTicks(int duration)
	{
		this.glowingTicks = duration;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SPECTRAL_ARROW;
	}

}
