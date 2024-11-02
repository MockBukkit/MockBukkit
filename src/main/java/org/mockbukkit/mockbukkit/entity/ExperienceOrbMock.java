package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of an {@link ExperienceOrb}.
 *
 * @see EntityMock
 */
public class ExperienceOrbMock extends EntityMock implements ExperienceOrb
{

	private int experience;

	/**
	 * Constructs a new {@link ExperienceOrbMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ExperienceOrbMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		this(server, uuid, 0);
	}

	/**
	 * Constructs a new {@link ExperienceOrbMock} on the provided {@link ServerMock} with a specified {@link UUID} an experience amount.
	 *
	 * @param server     The server to create the entity on.
	 * @param uuid       The UUID of the entity.
	 * @param experience The amount of experience the orb has.
	 */
	public ExperienceOrbMock(@NotNull ServerMock server, @NotNull UUID uuid, int experience)
	{
		super(server, uuid);

		this.experience = experience;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.EXPERIENCE_ORB;
	}

	@Override
	public int getExperience()
	{
		return experience;
	}

	@Override
	public void setExperience(int value)
	{
		this.experience = value;
	}

	@Override
	public int getCount()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCount(int i)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable UUID getTriggerEntityId()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable UUID getSourceEntityId()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull SpawnReason getSpawnReason()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
