package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.item.blocksattacks.DamageReduction;
import io.papermc.paper.datacomponent.item.blocksattacks.ItemDamageFunction;
import io.papermc.paper.registry.set.RegistryKeySet;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kyori.adventure.key.Key;
import org.bukkit.damage.DamageType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class BlocksAttacksMock implements BlocksAttacks
{
	private final float blockDelaySeconds;
	private final float disableCooldownScale;
	private final List<DamageReduction> damageReductions;
	private final ItemDamageFunction itemDamage;
	private final @Nullable RegistryKeySet<DamageType> bypassedBy;
	private final @Nullable Key blockSound;
	private final @Nullable Key disableSound;

	private BlocksAttacksMock(BuilderMock builder)
	{
		this.blockDelaySeconds = builder.blockDelaySeconds;
		this.disableCooldownScale = builder.disableCooldownScale;
		this.damageReductions = builder.damageReductions;
		this.itemDamage = builder.itemDamage;
		this.bypassedBy = builder.bypassedBy;
		this.blockSound = builder.blockSound;
		this.disableSound = builder.disableSound;
	}

	@Override
	public float blockDelaySeconds()
	{
		return this.blockDelaySeconds;
	}

	@Override
	public float disableCooldownScale()
	{
		return this.disableCooldownScale;
	}

	@Override
	public List<DamageReduction> damageReductions()
	{
		return Collections.unmodifiableList(this.damageReductions);
	}

	@Override
	public ItemDamageFunction itemDamage()
	{
		return this.itemDamage;
	}

	@Override
	public @Nullable RegistryKeySet<DamageType> bypassedBy()
	{
		return this.bypassedBy;
	}

	@Override
	public @Nullable Key blockSound()
	{
		return this.blockSound;
	}

	@Override
	public @Nullable Key disableSound()
	{
		return this.disableSound;
	}

	static class BuilderMock implements Builder
	{

		private float blockDelaySeconds;
		private float disableCooldownScale = 1.0F;
		private List<DamageReduction> damageReductions = new ObjectArrayList<>();
		private ItemDamageFunction itemDamage = ItemDamageFunction.itemDamageFunction()
				.threshold(1.0F).base(0.0F).factor(1.0F).build();
		private @Nullable RegistryKeySet<DamageType> bypassedBy;
		private @Nullable Key blockSound;
		private @Nullable Key disableSound;

		@Override
		public Builder blockDelaySeconds(float delay)
		{
			Preconditions.checkArgument(delay >= 0.0F, "delay must be non-negative, was %s", delay);
			this.blockDelaySeconds = delay;
			return this;
		}

		@Override
		public Builder disableCooldownScale(float scale)
		{
			Preconditions.checkArgument(scale >= 0.0F, "scale must be non-negative, was %s", scale);
			this.disableCooldownScale = scale;
			return this;
		}

		@Override
		public Builder addDamageReduction(DamageReduction reduction)
		{
			this.damageReductions.add(reduction);
			return this;
		}

		@Override
		public Builder damageReductions(List<DamageReduction> reductions)
		{
			this.damageReductions = new ObjectArrayList<>(reductions);
			return this;
		}

		@Override
		public Builder itemDamage(ItemDamageFunction function)
		{
			this.itemDamage = function;
			return this;
		}

		@Override
		public Builder bypassedBy(@Nullable RegistryKeySet<DamageType> bypassedBy)
		{
			this.bypassedBy = bypassedBy;
			return this;
		}

		@Override
		public Builder blockSound(@Nullable Key sound)
		{
			this.blockSound = sound;
			return this;
		}

		@Override
		public Builder disableSound(@Nullable Key sound)
		{
			this.disableSound = sound;
			return this;
		}

		@Override
		public BlocksAttacks build()
		{
			return new BlocksAttacksMock(this);
		}

	}

}
