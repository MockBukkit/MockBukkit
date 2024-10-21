package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ParrotMock extends AnimalsMock implements Parrot
{

	private @NotNull Parrot.Variant variant = Variant.RED;
	private boolean isSitting = false;
	private boolean isTamed = false;
	private AnimalTamer animalTamer = null;

	/**
	 * Constructs a new {@link ParrotMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ParrotMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(@NotNull Variant variant)
	{
		Preconditions.checkNotNull(variant, "Variant cannot be null");
		this.variant = variant;
	}

	@Override
	public boolean isDancing()
	{
		// TODO Implement when startPlaying in jukebox is implemented
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTamed()
	{
		return this.isTamed;
	}

	@Override
	public void setTamed(boolean tame)
	{
		this.isTamed = tame;
	}

	@Override
	public @Nullable UUID getOwnerUniqueId()
	{
		return this.animalTamer.getUniqueId();
	}

	@Override
	public @Nullable AnimalTamer getOwner()
	{
		return this.animalTamer;
	}

	@Override
	public void setOwner(@Nullable AnimalTamer tamer)
	{
		this.animalTamer = tamer;
	}

	@Override
	public boolean isSitting()
	{
		return this.isSitting;
	}

	@Override
	public void setSitting(boolean sitting)
	{
		this.isSitting = sitting;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PARROT;
	}

}
