package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FishMockTest
{

	private PufferFishMock fish;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		fish = new PufferFishMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsFromBucketDefault()
	{
		Assertions.assertFalse(fish.isFromBucket());
	}

	@Test
	void testSetFromBucket()
	{
		fish.setFromBucket(true);
		Assertions.assertTrue(fish.isFromBucket());
	}

	@Test
	void testGetPickupSound()
	{
		assertEquals(Sound.ITEM_BUCKET_FILL_FISH, fish.getPickupSound());
	}

}
