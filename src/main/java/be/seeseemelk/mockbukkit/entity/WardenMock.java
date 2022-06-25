package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Warden;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WardenMock extends MonsterMock implements Warden
{

	private final Map<Entity, Integer> angerPerEntity = new HashMap<>();

	public WardenMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getAnger(@NotNull Entity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		return angerPerEntity.getOrDefault(entity, 0);
	}

	@Override
	public void increaseAnger(@NotNull Entity entity, int increase)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");

		if (angerPerEntity.containsKey(entity))
		{
			int newAnger = angerPerEntity.get(entity) + increase;

			Preconditions.checkState(newAnger <= 150, "Anger can't be higher than 150");

			angerPerEntity.put(entity, newAnger);
		}
		else
		{
			if (increase > 150)
			{
				throw new IllegalStateException("Anger can't be higher than 150");
			}
			angerPerEntity.put(entity, increase);
		}
	}

	@Override
	public void setAnger(@NotNull Entity entity, int anger)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		Preconditions.checkArgument(anger <= 150, "Anger can't be higher than 150");
		angerPerEntity.put(entity, anger);
	}

}
