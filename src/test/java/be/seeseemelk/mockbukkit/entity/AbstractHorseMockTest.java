package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Horse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AbstractHorseMockTest
{

	private HorseMock horse;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		horse = new HorseMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testSetVariant()
	{
		assertThrows(UnsupportedOperationException.class, () -> horse.setVariant(Horse.Variant.HORSE));
	}

	@Test
	void testGetDomesticationDefault()
	{
		assertEquals(0, horse.getDomestication());
	}

	@Test
	void testGetMaxDomesticationDefault()
	{
		assertEquals(100, horse.getMaxDomestication());
	}

	@Test
	void testSetDomestication()
	{
		horse.setDomestication(50);
		assertEquals(50, horse.getDomestication());
	}

	@Test
	void testSetDomesticationTooLow()
	{
		assertThrows(IllegalArgumentException.class, () -> horse.setDomestication(-1));
	}

	@Test
	void testSetDomesticationTooHigh()
	{
		assertThrows(IllegalArgumentException.class, () -> horse.setDomestication(101));
	}

	@Test
	void testSetMaxDomestication()
	{
		horse.setMaxDomestication(50);
		assertEquals(50, horse.getMaxDomestication());
	}

	@Test
	void testSetMaxDomesticationTooLow()
	{
		assertThrows(IllegalArgumentException.class, () -> horse.setMaxDomestication(0));
	}

	@Test
	void testSetMaxDomesticationInfluencingSetDomestication()
	{
		horse.setMaxDomestication(50);
		assertThrows(IllegalArgumentException.class, () -> horse.setDomestication(51));
	}

}
