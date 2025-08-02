package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class StrayMockTest
{

	@MockBukkitInject
	private StrayMock stray;

	@Test
	void testGetSkeletonType()
	{
		assertEquals(Skeleton.SkeletonType.STRAY, stray.getSkeletonType());
	}

}
