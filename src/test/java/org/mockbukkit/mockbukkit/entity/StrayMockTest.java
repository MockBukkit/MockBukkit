package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StrayMockTest
{

	private ServerMock server;
	private StrayMock stray;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		stray = new StrayMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetSkeletonType()
	{
		assertEquals(Skeleton.SkeletonType.STRAY, stray.getSkeletonType());
	}

}
