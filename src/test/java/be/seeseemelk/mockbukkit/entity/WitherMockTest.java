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
class WitherMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private WitherMock wither;

	@BeforeEach
	void setUp()
	{
		wither = new WitherMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.WITHER, wither.getType());
	}

	@Test
	void getHeight()
	{
		assertEquals(3.5D, wither.getHeight());
	}

	@Test
	void getWidth()
	{
		assertEquals(0.9D, wither.getWidth());
	}

	@Test
	void getEyeHeight()
	{
		assertEquals(2.9750001D, wither.getEyeHeight());
	}

}
