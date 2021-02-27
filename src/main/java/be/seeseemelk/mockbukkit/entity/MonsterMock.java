package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;

import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;

public abstract class MonsterMock extends CreatureMock implements Monster
{
	public MonsterMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setAware(boolean aware)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isAware()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void attack(@NotNull Entity target)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void swingMainHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void swingOffHand()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Set<UUID> getCollidableExemptions()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Spigot spigot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}
}
