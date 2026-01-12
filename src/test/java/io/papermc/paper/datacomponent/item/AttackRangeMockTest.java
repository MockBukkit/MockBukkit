package io.papermc.paper.datacomponent.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("UnstableApiUsage")
@ExtendWith(MockBukkitExtension.class)
class AttackRangeMockTest
{

	@Test
	void givenBasicCreation()
	{
		AttackRange component = AttackRange.attackRange()
				.hitboxMargin(0.5F)
				.minReach(10)
				.maxReach(20)
				.minCreativeReach(30)
				.maxCreativeReach(40)
				.mobFactor(2)
				.build();

		assertEquals(0.5F, component.hitboxMargin());
		assertEquals(10, component.minReach());
		assertEquals(20, component.maxReach());
		assertEquals(30, component.minCreativeReach());
		assertEquals(40, component.maxCreativeReach());
		assertEquals(2, component.mobFactor());
	}

}
