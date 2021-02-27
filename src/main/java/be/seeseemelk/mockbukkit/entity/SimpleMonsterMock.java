package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.inventory.EntityEquipment;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class SimpleMonsterMock extends MonsterMock implements Monster
{

	public SimpleMonsterMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	public SimpleMonsterMock(ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

	@Override
	public EntityType getType()
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

}
