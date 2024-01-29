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
class SilverfishMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private SilverfishMock silverfish;

	@BeforeEach
	void setUp()
	{
		silverfish = new SilverfishMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SILVERFISH, silverfish.getType());
	}

}
