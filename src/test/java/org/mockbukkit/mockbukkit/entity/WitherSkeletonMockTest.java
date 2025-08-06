package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class WitherSkeletonMockTest
{

	@MockBukkitInject
	private WitherSkeletonMock witherSkeleton;

	@Test
	void testGetSkeletonType()
	{
		assertEquals(Skeleton.SkeletonType.WITHER, witherSkeleton.getSkeletonType());
	}

}
