package io.papermc.paper.datacomponent.item;

import net.kyori.adventure.key.Key;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public class PiercingWeaponMock implements PiercingWeapon
{

	private final boolean dealsKnockback;
	private final boolean dismounts;
	private final @Nullable Key sound;
	private final @Nullable Key hitSound;

	private PiercingWeaponMock(BuilderMock builder)
	{
		this.dealsKnockback = builder.dealsKnockback;
		this.dismounts = builder.dismounts;
		this.sound = builder.sound;
		this.hitSound = builder.hitSound;
	}

	@Override
	public boolean dealsKnockback()
	{
		return this.dealsKnockback;
	}

	@Override
	public boolean dismounts()
	{
		return this.dismounts;
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

	static class BuilderMock implements Builder
	{

		private boolean dealsKnockback;
		private boolean dismounts;
		private @Nullable Key sound;
		private @Nullable Key hitSound;

		@Override
		public Builder dealsKnockback(boolean dealsKnockback)
		{
			this.dealsKnockback = dealsKnockback;
			return this;
		}

		@Override
		public Builder dismounts(boolean dismounts)
		{
			this.dismounts = dismounts;
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
		public PiercingWeapon build()
		{
			return new PiercingWeaponMock(this);
		}

	}

}
