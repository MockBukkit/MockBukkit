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

class SalmonMockTest
{

	private SalmonMock salmon;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		salmon = new SalmonMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetBaseBucketItem()
	{
		assertEquals(Material.SALMON_BUCKET, salmon.getBaseBucketItem().getType());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SALMON, salmon.getType());
	}

}
