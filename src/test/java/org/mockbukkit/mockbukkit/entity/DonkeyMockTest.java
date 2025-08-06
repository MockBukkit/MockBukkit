package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Horse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class DonkeyMockTest
{

	@MockBukkitInject
	private DonkeyMock donkey;

	@Test
	void testVariant()
	{
		assertEquals(Horse.Variant.DONKEY, donkey.getVariant());
	}

	@Test
	void getEyeHeight_GivenDefaultDonkey()
	{
		assertEquals(1.425D, donkey.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyDonkey()
	{
		donkey.setBaby();
		assertEquals(0.7125D, donkey.getEyeHeight());
	}

}
