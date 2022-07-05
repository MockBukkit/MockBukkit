package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
