package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class OcelotMockTest
{

	@MockBukkitInject
	private OcelotMock ocelot;

	@Test
	void testIsTrusting()
	{
		assertFalse(ocelot.isTrusting());
	}

	@Test
	void setTrusting()
	{
		ocelot.setTrusting(true);
		assertTrue(ocelot.isTrusting());
	}

	@Test
	void testGetOcelotType()
	{
		assertEquals(Ocelot.Type.WILD_OCELOT, ocelot.getCatType());
	}

	@Test
	void testSetOcelotType()
	{
		assertThrows(UnsupportedOperationException.class, () -> ocelot.setCatType(Ocelot.Type.BLACK_CAT));
	}

	@Test
	void testEntityType()
	{
		assertEquals(EntityType.OCELOT, ocelot.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultOcelot()
	{
		assertEquals(0.595D, ocelot.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyOcelot()
	{
		ocelot.setBaby();
		assertEquals(0.2975D, ocelot.getEyeHeight());
	}

}
