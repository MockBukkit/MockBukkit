package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Warden;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public int getAnger()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getAnger(@NotNull Entity entity)
	{
		Preconditions.checkNotNull(entity, "Entity cannot be null");
		return angerPerEntity.getOrDefault(entity, 0);
	}

	@Override
	public int getHighestAnger()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
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
			Preconditions.checkState(increase <= 150, "Anger can't be higher than 150");
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

	@Override
	public void clearAnger(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable LivingEntity getEntityAngryAt()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDisturbanceLocation(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull AngerLevel getAngerLevel()
	{
		if (getAnger() <= 39)
			return AngerLevel.CALM;
		else if (getAnger() <= 79)
			return AngerLevel.AGITATED;
		else
			return AngerLevel.ANGRY;
	}

}
