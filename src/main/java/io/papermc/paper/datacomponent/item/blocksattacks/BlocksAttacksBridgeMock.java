package io.papermc.paper.datacomponent.item.blocksattacks;

import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "UnstableApiUsage" })
public class BlocksAttacksBridgeMock implements BlocksAttacksBridge
{

	@Override
	public DamageReduction.Builder blocksAttacksDamageReduction()
	{
		return new DamageReductionMock.BuilderMock();
	}

	@Override
	public ItemDamageFunction.Builder blocksAttacksItemDamageFunction()
	{
		return new ItemDamageFunctionMock.BuilderMock();
	}

}
