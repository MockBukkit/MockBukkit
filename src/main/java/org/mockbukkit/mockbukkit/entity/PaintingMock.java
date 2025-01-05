package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Art;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.data.EntityState;

import java.util.Objects;
import java.util.UUID;

/**
 * Mock implementation of a {@link Painting}.
 *
 * @see HangingMock
 */
public class PaintingMock extends HangingMock implements Painting
{
	private @NotNull Art art;

	/**
	 * Constructs a new {@link PaintingMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PaintingMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		this(server, uuid, Art.KEBAB);
	}

	/**
	 * Constructs a new {@link PaintingMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 * @param art	 The art to be used in the painting.
	 */
	public PaintingMock(@NotNull ServerMock server, @NotNull UUID uuid, @NotNull Art art)
	{
		super(server, uuid);
		this.art = Objects.requireNonNull(art, "Art must not be null");
	}

	@Override
	public @NotNull Art getArt()
	{
		return this.art;
	}

	@Override
	public boolean setArt(@NotNull Art art)
	{
		return this.setArt(art, false);
	}

	@Override
	public boolean setArt(@NotNull Art art, boolean force)
	{
		Preconditions.checkArgument(art != null, "Art cannot be null");

		if (!force)
		{
			// TODO: Check if the painting fits the current location, if not return false.
		}

		this.art = art;
		return true;
	}

	@Override
	public double getWidth()
	{
		return getArt().getBlockWidth();
	}

	@Override
	protected double getHeight(@NotNull EntityState state)
	{
		return getArt().getBlockHeight();
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PAINTING;
	}

}
