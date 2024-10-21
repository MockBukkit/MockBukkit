package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.entity.WitherSkeletonMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WitherSkeletonMockTest
{

	private ServerMock server;
	private WitherSkeletonMock witherSkeleton;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		witherSkeleton = new WitherSkeletonMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetSkeletonType()
	{
		assertEquals(Skeleton.SkeletonType.WITHER, witherSkeleton.getSkeletonType());
	}

}
