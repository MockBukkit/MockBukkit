package be.seeseemelk.mockbukkit.entity;

import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EntityEquipment;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class SimpleMobMock extends MobMock
{
	public SimpleMobMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	public SimpleMobMock(ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

	@Override
	public @NotNull EntityType getType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public EntityEquipment getEquipment()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
