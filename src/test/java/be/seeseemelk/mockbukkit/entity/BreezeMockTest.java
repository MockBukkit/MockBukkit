package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class BreezeMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private BreezeMock breeze;

	@BeforeEach
	void prepare()
	{
		this.breeze = new BreezeMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.BREEZE, breeze.getType());
	}

}
