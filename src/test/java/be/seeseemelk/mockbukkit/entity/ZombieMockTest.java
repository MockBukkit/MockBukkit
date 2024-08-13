package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.data.EntitySubType;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZombieMockTest
{

	private ServerMock server;
	private ZombieMock zombie;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		zombie = new ZombieMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getType() {
		assertEquals(EntityType.ZOMBIE, zombie.getType());
	}

	@Test
	void getSubType_GivenDefaultZombie() {
		assertEquals(EntitySubType.DEFAULT, zombie.getSubType());
	}

	@Test
	void getSubType_GivenBabyZombie() {
		zombie.setBaby();
		assertEquals(EntitySubType.BABY, zombie.getSubType());
	}

	@Test
	void getEyeHeight_GivenDefaultZombie()
	{
		assertEquals(1.6575D, zombie.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyZombie()
	{
		zombie.setBaby();
		assertEquals(0.82875D, zombie.getEyeHeight());
	}

}
