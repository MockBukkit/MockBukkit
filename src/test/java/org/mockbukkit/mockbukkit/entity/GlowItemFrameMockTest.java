package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlowItemFrameMockTest
{
	private ServerMock server;
	private GlowItemFrameMock itemFrame;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		itemFrame = new GlowItemFrameMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.GLOW_ITEM_FRAME, itemFrame.getType());
	}
}
