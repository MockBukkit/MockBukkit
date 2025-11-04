package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.block.BlockPredicate;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ItemAdventurePredicateMock(List<BlockPredicate> predicates) implements ItemAdventurePredicate
{

	static class BuilderMock implements Builder
	{

		private final List<BlockPredicate> predicates = new ObjectArrayList<>();

		@Override
		public Builder addPredicate(BlockPredicate predicate)
		{
			Preconditions.checkArgument(predicate != null, "predicate is null");
			predicates.add(predicate);
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
			return new ItemAdventurePredicateMock(new ObjectArrayList<>(predicates));
		}

	}

}
