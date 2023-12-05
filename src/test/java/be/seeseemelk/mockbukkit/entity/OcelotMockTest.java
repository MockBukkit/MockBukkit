package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OcelotMockTest
{

	private OcelotMock ocelot;

	@BeforeEach
	void setUp() {
		ServerMock server = MockBukkit.mock();
		ocelot = new OcelotMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown() {
		MockBukkit.unmock();
	}

	@Test
	void testIsTrusting() {
		assertFalse(ocelot.isTrusting());
	}

	@Test
	void setTrusting() {
		ocelot.setTrusting(true);
		assertTrue(ocelot.isTrusting());
	}

	@Test
	void testGetOcelotType() {
		assertEquals(Ocelot.Type.WILD_OCELOT, ocelot.getCatType());
	}

	@Test
	void testSetOcelotType() {
		assertThrows(UnsupportedOperationException.class, () -> ocelot.setCatType(Ocelot.Type.BLACK_CAT));
	}

	@Test
	void testEntityType() {
		assertEquals(EntityType.OCELOT, ocelot.getType());
	}

}
