package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record WeaponMock(int itemDamagePerAttack, float disableBlockingForSeconds) implements Weapon
{
	static class BuilderMock implements Weapon.Builder
	{
		private int itemDamagePerAttack = 1;
		private float disableBlockingForSeconds;

		@Override
		public Builder itemDamagePerAttack(int damage)
		{
			Preconditions.checkArgument(damage >= 0, "damage must be non-negative, was %s", damage);
			this.itemDamagePerAttack = damage;
			return this;
		}

		@Override
		public Builder disableBlockingForSeconds(float seconds)
		{
			Preconditions.checkArgument(seconds >= 0.0F, "seconds must be non-negative, was %s", seconds);
			this.disableBlockingForSeconds = seconds;
			return this;
		}

		@Override
		public Weapon build()
		{
			return new WeaponMock(this.itemDamagePerAttack, this.disableBlockingForSeconds);
		}

	}

}
