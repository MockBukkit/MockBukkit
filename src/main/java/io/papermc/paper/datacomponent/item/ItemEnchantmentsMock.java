package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.enchantments.Enchantment;
import org.checkerframework.common.value.qual.IntRange;
import org.jspecify.annotations.NullMarked;

import java.util.HashMap;
import java.util.Map;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record ItemEnchantmentsMock(Map<Enchantment, Integer> enchantments) implements ItemEnchantments
{

	static class BuilderMock implements Builder
	{

		private static final int MAX_LEVEL = 255;

		Map<Enchantment, Integer> enchantments = new HashMap<>();

		@Override
		public Builder add(Enchantment enchantment, @IntRange(from = 1L, to = 255L) int level)
		{
			Preconditions.checkArgument(
					level >= 1 && level <= MAX_LEVEL,
					"level must be between %s and %s, was %s",
					1, MAX_LEVEL,
					level
			);
			this.enchantments.put(enchantment, level);
			return this;
		}

		@Override
		public Builder addAll(Map<Enchantment, @IntRange(from = 1L, to = 255L) Integer> enchantments)
		{
			enchantments.forEach(this::add);
			return this;
		}

		@Override
		public ItemEnchantments build()
		{
			return new ItemEnchantmentsMock(ImmutableMap.copyOf(enchantments));
		}

	}

}
