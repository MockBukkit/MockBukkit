package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Sound;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockBukkitExtension.class)
class FishMockTest
{

	@MockBukkitInject
	private PufferFishMock fish;

	@Test
	void testIsFromBucketDefault()
	{
		assertFalse(fish.isFromBucket());
	}

	@Test
	void testSetFromBucket()
	{
		fish.setFromBucket(true);
		assertTrue(fish.isFromBucket());
	}

	@Test
	void testGetPickupSound()
	{
		assertEquals(Sound.ITEM_BUCKET_FILL_FISH, fish.getPickupSound());
	}

}
