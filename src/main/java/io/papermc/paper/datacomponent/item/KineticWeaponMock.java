package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class KineticWeaponMock implements KineticWeapon
{

	private final int contactCooldownTicks;
	private final int delayTicks;

	private final @Nullable Condition dismountConditions;
	private final @Nullable Condition knockbackConditions;
	private final @Nullable Condition damageConditions;

	private final @Nullable Key sound;
	private final @Nullable Key hitSound;

	private final float damageMultiplier;
	private final float forwardMovement;

	private KineticWeaponMock(BuilderMock builder)
	{
		this.contactCooldownTicks = builder.contactCooldownTicks;
		this.delayTicks = builder.delayTicks;

		this.dismountConditions = builder.dismountConditions;
		this.knockbackConditions = builder.knockbackConditions;
		this.damageConditions = builder.damageConditions;

		this.sound = builder.sound;
		this.hitSound = builder.hitSound;

		this.damageMultiplier = builder.damageMultiplier;
		this.forwardMovement = builder.forwardMovement;
	}

	@Override
	public @NonNegative int contactCooldownTicks()
	{
		return this.contactCooldownTicks;
	}

	@Override
	public @NonNegative int delayTicks()
	{
		return this.delayTicks;
	}

	@Override
	public @Nullable Condition dismountConditions()
	{
		return this.dismountConditions;
	}

	@Override
	public @Nullable Condition knockbackConditions()
	{
		return this.knockbackConditions;
	}

	@Override
	public @Nullable Condition damageConditions()
	{
		return this.damageConditions;
	}

	@Override
	public float damageMultiplier()
	{
		return this.damageMultiplier;
	}

	@Override
	public float forwardMovement()
	{
		return this.forwardMovement;
	}

	@Override
	public @Nullable Key sound()
	{
		return this.sound;
	}

	@Override
	public @Nullable Key hitSound()
	{
		return this.hitSound;
	}

	public record ConditionMock(@NonNegative int maxDurationTicks, float minSpeed,
								float minRelativeSpeed) implements Condition
	{

	}

	static class BuilderMock implements Builder
	{

		private int contactCooldownTicks = 10;
		private int delayTicks = 0;

		private @Nullable Condition dismountConditions;
		private @Nullable Condition knockbackConditions;
		private @Nullable Condition damageConditions;

		private @Nullable Key sound = null;
		private @Nullable Key hitSound = null;

		private float damageMultiplier = 1;
		private float forwardMovement = 0F;

		@Override
		public Builder contactCooldownTicks(@NonNegative int ticks)
		{
			Preconditions.checkArgument(ticks >= 0, "contactCooldownTicks must be non-negative");
			this.contactCooldownTicks = ticks;
			return this;
		}

		@Override
		public Builder delayTicks(@NonNegative int ticks)
		{
			Preconditions.checkArgument(ticks >= 0, "delayTicks must be non-negative");
			this.delayTicks = ticks;
			return this;
		}

		@Override
		public Builder dismountConditions(@Nullable Condition condition)
		{
			this.dismountConditions = condition;
			return this;
		}

		@Override
		public Builder knockbackConditions(@Nullable Condition condition)
		{
			this.knockbackConditions = condition;
			return this;
		}

		@Override
		public Builder damageConditions(@Nullable Condition condition)
		{
			this.damageConditions = condition;
			return this;
		}

		@Override
		public Builder damageMultiplier(float damageMultiplier)
		{
			this.damageMultiplier = damageMultiplier;
			return this;
		}

		@Override
		public Builder forwardMovement(float forwardMovement)
		{
			this.forwardMovement = forwardMovement;
			return this;
		}

		@Override
		public Builder sound(@Nullable Key sound)
		{
			this.sound = sound;
			return this;
		}

		@Override
		public Builder hitSound(@Nullable Key sound)
		{
			this.hitSound = sound;
			return this;
		}

		@Override
		public KineticWeapon build()
		{
			return new KineticWeaponMock(this);
		}

	}

}
