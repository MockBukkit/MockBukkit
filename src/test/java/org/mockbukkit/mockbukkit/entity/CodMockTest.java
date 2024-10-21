package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CodMockTest
{

	private CodMock cod;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		cod = new CodMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetBaseBucketItem()
	{
		assertEquals(Material.COD_BUCKET, cod.getBaseBucketItem().getType());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.COD, cod.getType());
	}

}
