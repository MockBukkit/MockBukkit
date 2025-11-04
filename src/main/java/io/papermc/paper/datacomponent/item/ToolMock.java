package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.util.TriState;
import org.bukkit.block.BlockType;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ToolMock(float defaultMiningSpeed, @NonNegative int damagePerBlock, List<Rule> rules,
					   boolean canDestroyBlocksInCreative) implements Tool
{

	record RuleMock(RegistryKeySet<BlockType> blocks, @Nullable Float speed, TriState correctForDrops) implements Rule
	{

	}

	static final class BuilderMock implements Builder
	{

		private final ImmutableList.Builder<Rule> rules = ImmutableList.builder();
		private int damage = 1;
		private float miningSpeed = 1.0F;
		private boolean canDestroyBlocksInCreative = true;


		@Override
		public Builder damagePerBlock(@NonNegative int damage)
		{
			Preconditions.checkArgument(damage >= 0, "damage must be non-negative, was %s", damage);
			this.damage = damage;
			return this;
		}

		@Override
		public Builder defaultMiningSpeed(float miningSpeed)
		{
			this.miningSpeed = miningSpeed;
			return this;
		}

		@Override
		public Builder addRule(Rule rule)
		{
			Preconditions.checkNotNull(rule);
			rules.add(rule);
			return this;
		}

		@Override
		public Builder canDestroyBlocksInCreative(boolean canDestroyBlocksInCreative)
		{
			this.canDestroyBlocksInCreative = canDestroyBlocksInCreative;
			return this;
		}

		@Override
		public Builder addRules(Collection<Rule> rules)
		{
			rules.forEach(this::addRule);
			return this;
		}

		@Override
		public Tool build()
		{
			return new ToolMock(miningSpeed, damage, rules.build(), canDestroyBlocksInCreative);
		}

	}

}
