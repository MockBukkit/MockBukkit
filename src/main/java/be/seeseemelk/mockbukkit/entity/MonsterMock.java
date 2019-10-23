package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Monster;

import java.util.UUID;

public class MonsterMock extends CreatureMock implements Monster {
    public MonsterMock(ServerMock server, UUID uuid) {
        super(server, uuid);
    }
}
