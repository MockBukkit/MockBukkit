package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import java.util.UUID;

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

}
