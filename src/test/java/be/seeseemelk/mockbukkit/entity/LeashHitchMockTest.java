package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LeashHitch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@ExtendWith(MockBukkitExtension.class)
public class LeashHitchMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private LeashHitch leashHitch;

	@BeforeEach
	public void setUp()
	{
		leashHitch = new LeashHitchMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		Assertions.assertEquals(EntityType.LEASH_HITCH,leashHitch.getType());
	}

}
