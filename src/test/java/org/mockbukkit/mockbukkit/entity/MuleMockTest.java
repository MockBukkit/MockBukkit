package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Horse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class MuleMockTest
{

	@MockBukkitInject
	private MuleMock mule;

	@Test
	void testVariant()
	{
		assertEquals(Horse.Variant.MULE, mule.getVariant());
	}

	@Test
	void getEyeHeight_GivenDefaultMule()
	{
		assertEquals(1.52D, mule.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyMule()
	{
		mule.setBaby();
		assertEquals(0.75D, mule.getEyeHeight());
	}

}
