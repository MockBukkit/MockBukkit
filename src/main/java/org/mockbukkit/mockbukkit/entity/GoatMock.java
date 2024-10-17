package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Goat;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Mock implementation of a {@link Goat}.
 *
 * @see AnimalsMock
 */
public class GoatMock extends AnimalsMock implements Goat
{

	private boolean hasLeftHorn = true;
	private boolean hasRightHorn = true;
	private boolean isScreaming = false;

	private final List<LivingEntity> attackedMobs = new LinkedList<>();

	/**
	 * Constructs a new {@link GoatMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public GoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean hasLeftHorn()
	{
		return this.hasLeftHorn;
	}

	@Override
	public void setLeftHorn(boolean hasHorn)
	{
		this.hasLeftHorn = hasHorn;
	}

	@Override
	public boolean hasRightHorn()
	{
		return this.hasRightHorn;
	}

	@Override
	public void setRightHorn(boolean hasHorn)
	{
		this.hasRightHorn = hasHorn;
	}

	@Override
	public boolean isScreaming()
	{
		return this.isScreaming;
	}

	@Override
	public void setScreaming(boolean screaming)
	{
		this.isScreaming = screaming;
	}

	@Override
	public void ram(@NotNull LivingEntity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		this.attackedMobs.add(entity);
	}

	/**
	 * Asserts that the goat attacked the given entity.
	 *
	 * @param entity The entity to assert.
	 */
	@Deprecated(forRemoval = true)
	public void assertEntityRammed(@NotNull LivingEntity entity)
	{
		if (!hasRammedEntity(entity))
		{
			fail("Expected Goat to have rammed " + entity.getName() + " but it did not!");
		}
	}

	/**
	 * Whether this goat has rammed the specified entity
	 * @param entity The entity that the goat should have rammed
	 * @return True if the goat has rammed the entity
	 */
	public boolean hasRammedEntity(@NotNull LivingEntity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		return this.attackedMobs.contains(entity);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.GOAT;
	}

}
