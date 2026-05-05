package io.papermc.paper.datacomponent.item.blocksattacks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SuppressWarnings("UnstableApiUsage")
class BlocksAttacksBridgeMockTest
{

	private final BlocksAttacksBridgeMock bridge = new BlocksAttacksBridgeMock();

	@Test
	void givenDamageReduction()
	{
		DamageReduction.Builder actual = bridge.blocksAttacksDamageReduction();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

	@Test
	void givenItemDamageFunction()
	{
		ItemDamageFunction.Builder actual = bridge.blocksAttacksItemDamageFunction();
		assertNotNull(actual);
		assertNotNull(actual.build());
	}

}
