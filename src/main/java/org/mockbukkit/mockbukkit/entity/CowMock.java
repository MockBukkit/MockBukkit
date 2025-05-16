package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link Cow}.
 *
 * @see AnimalsMock
 */
public class CowMock extends AbstractCowMock implements Cow
{

	private @NotNull Variant variant = Variant.TEMPERATE;

	/**
	 * Constructs a new {@link CowMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CowMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.COW;
	}

	@Override
	public @NotNull Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(@NotNull Variant variant)
	{
		Preconditions.checkArgument(variant != null, "Variant cannot be null");
		this.variant = variant;
	}

}
