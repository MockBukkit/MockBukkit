package io.papermc.paper.datacomponent.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.keys.SoundEventKeys;
import io.papermc.paper.registry.set.RegistryKeySet;
import net.kyori.adventure.key.Key;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
@SuppressWarnings({ "NonExtendableApiUsage", "UnstableApiUsage" })
public record EquipableMock(EquipmentSlot slot, Key equipSound, @Nullable Key assetId,
							@Nullable Key cameraOverlay, @Nullable RegistryKeySet<EntityType> allowedEntities,
							boolean dispensable, boolean swappable, boolean damageOnHurt,
							boolean equipOnInteract, boolean canBeSheared, Key shearSound
) implements Equippable
{

	@Override
	public Builder toBuilder()
	{
		return new BuilderMock(slot)
				.equipSound(equipSound)
				.assetId(assetId)
				.cameraOverlay(this.cameraOverlay)
				.allowedEntities(this.allowedEntities)
				.dispensable(dispensable)
				.swappable(swappable)
				.damageOnHurt(damageOnHurt)
				.equipOnInteract(equipOnInteract)
				.shearSound(shearSound)
				.canBeSheared(canBeSheared);
	}

	static class BuilderMock implements Builder
	{

		private final EquipmentSlot slot;
		private Key equipSound = SoundEventKeys.ITEM_ARMOR_EQUIP_GENERIC;
		private @Nullable Key assetId;
		private @Nullable Key cameraOverlay;
		private @Nullable RegistryKeySet<EntityType> allowedEntities;
		private boolean dispensable = true;
		private boolean swappable = true;
		private boolean damageOnHurt = true;
		private boolean equipOnInteract;
		private boolean canBeSheared = false;
		private Key shearSound = SoundEventKeys.ITEM_SHEARS_SNIP;

		BuilderMock(EquipmentSlot slot)
		{
			this.slot = slot;
		}

		@Override
		public Builder equipSound(Key sound)
		{
			this.equipSound = Preconditions.checkNotNull(sound);
			return this;
		}

		@Override
		public Builder assetId(@Nullable Key assetId)
		{
			this.assetId = assetId;
			return this;
		}

		@Override
		public Builder cameraOverlay(@Nullable Key cameraOverlay)
		{
			this.cameraOverlay = cameraOverlay;
			return this;
		}

		@Override
		public Builder allowedEntities(@Nullable RegistryKeySet<EntityType> allowedEntities)
		{
			this.allowedEntities = allowedEntities;
			return this;
		}

		@Override
		public Builder dispensable(boolean dispensable)
		{
			this.dispensable = dispensable;
			return this;
		}

		@Override
		public Builder swappable(boolean swappable)
		{
			this.swappable = swappable;
			return this;
		}

		@Override
		public Builder damageOnHurt(boolean damageOnHurt)
		{
			this.damageOnHurt = damageOnHurt;
			return this;
		}

		@Override
		public Builder equipOnInteract(boolean equipOnInteract)
		{
			this.equipOnInteract = equipOnInteract;
			return this;
		}

		@Override
		public Builder canBeSheared(boolean canBeSheared)
		{
			this.canBeSheared = canBeSheared;
			return this;
		}

		@Override
		public Builder shearSound(Key shearSound)
		{
			this.shearSound = Preconditions.checkNotNull(shearSound);
			return this;
		}

		@Override
		public Equippable build()
		{
			return new EquipableMock(slot, equipSound, assetId, cameraOverlay, allowedEntities, dispensable, swappable,
					damageOnHurt, equipOnInteract, canBeSheared, shearSound);
		}

	}

}
