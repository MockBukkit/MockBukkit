package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HuskMockTest
{
	private ServerMock server;
	private HuskMock husk;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		husk = new HuskMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.HUSK, husk.getType());
	}

	@Test
	void getHeight_GivenDefaultHusk()
	{
		assertEquals(1.95D, husk.getHeight());
	}

	@Test
	void getHeight_GivenBabyHusk()
	{
		husk.setBaby();
		assertEquals(0.975D, husk.getHeight());
	}

	@Test
	void getWidth_GivenDefaultHusk()
	{
		assertEquals(0.6D, husk.getWidth());
	}

	@Test
	void getWidth_GivenBabyHusk()
	{
		husk.setBaby();
		assertEquals(0.3D, husk.getWidth());
	}

	@Test
	void getEyeHeight_GivenDefaultHusk()
	{
		assertEquals(1.74D, husk.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyHusk()
	{
		husk.setBaby();
		assertEquals(0.93D, husk.getEyeHeight());
	}

}
