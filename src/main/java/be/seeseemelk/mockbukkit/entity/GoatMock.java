package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Goat;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

public class GoatMock extends AnimalsMock implements Goat
{

	private boolean hasLeftHorn = true;
	private boolean hasRightHorn = true;
	private boolean isScreaming = false;

	private final List<LivingEntity> attackedMobs = new LinkedList<>();

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
	 * @param entity The entity to assert.
	 */
	public void assertEntityRammed(@NotNull LivingEntity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		if (!this.attackedMobs.contains(entity))
		{
			fail("Expected Goat to have rammed " + entity.getName() + " but it did not!");
		}
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.GOAT;
	}

}
