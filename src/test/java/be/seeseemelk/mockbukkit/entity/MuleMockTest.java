package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Horse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MuleMockTest
{

	private ServerMock server;
	private MuleMock mule;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		mule = new MuleMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testVariant()
	{
		assertEquals(Horse.Variant.MULE, mule.getVariant());
	}

	@Test
	void getEyeHeight_GivenDefaultMule()
	{
		assertEquals(1.36D, mule.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyMule()
	{
		mule.setBaby();
		assertEquals(0.68D, mule.getEyeHeight());
	}

}
