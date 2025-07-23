package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.block.BlockPredicate;

import java.util.List;

public record ItemAdventurePredicateMock(List<BlockPredicate> predicates) implements ItemAdventurePredicate
{


	static class BuilderMock implements Builder
	{

		private final ImmutableList.Builder<BlockPredicate> predicatesBuilder = new ImmutableList.Builder<>();

		@Override
		public Builder addPredicate(BlockPredicate predicate)
		{
			Preconditions.checkNotNull(predicate);
			predicatesBuilder.add(predicate);
			return this;
		}

		@Override
		public Builder addPredicates(List<BlockPredicate> predicates)
		{
			predicates.forEach(this::addPredicate);
			return this;
		}

		@Override
		public ItemAdventurePredicate build()
		{
			return new ItemAdventurePredicateMock(predicatesBuilder.build());
		}

	}

}
