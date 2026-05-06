package org.mockbukkit.mockbukkit.inventory.meta.components;

import com.google.common.base.Preconditions;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.NotNullByDefault;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.inventory.SerializableMeta;
import org.mockbukkit.mockbukkit.util.NbtParser;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@NotNullByDefault
@EqualsAndHashCode
@SerializableAs("Equippable")
@SuppressWarnings("UnstableApiUsage")
public class EquippableComponentMock implements EquippableComponent
{

	private @Nullable NamespacedKey model;
	private @Nullable NamespacedKey cameraOverlay;
	private @Nullable Set<EntityType> allowedEntities;
	private @Nullable Sound sound;
	private @Nullable Sound shearingSound;

	private EquipmentSlot equipmentSlot;
	private boolean isDispensable;
	private boolean isSwappable;
	private boolean isDamageOnHurt;
	private boolean isEquipOnInteract;
	private boolean isShearable;

	private EquippableComponentMock(@Nullable NamespacedKey model,
								   @Nullable NamespacedKey cameraOverlay,
								   @Nullable Set<EntityType> allowedEntities,
								   @Nullable Sound sound,
								   @Nullable Sound shearingSound,
								   EquipmentSlot equipmentSlot,
								   boolean isDispensable,
								   boolean isSwappable,
								   boolean isDamageOnHurt,
								   boolean isEquipOnInteract,
								   boolean isShearable)
	{
		this.model = model;
		this.cameraOverlay = cameraOverlay;
		this.allowedEntities = allowedEntities != null ? Set.copyOf(allowedEntities) : null;
		this.sound = sound;
		this.shearingSound = shearingSound;
		this.equipmentSlot = equipmentSlot;
		this.isDispensable = isDispensable;
		this.isSwappable = isSwappable;
		this.isDamageOnHurt = isDamageOnHurt;
		this.isEquipOnInteract = isEquipOnInteract;
		this.isShearable = isShearable;
	}

	@Override
	public EquipmentSlot getSlot()
	{
		return this.equipmentSlot;
	}

	@Override
	public void setSlot(EquipmentSlot slot)
	{
		this.equipmentSlot = slot;
	}

	@Override
	public Sound getEquipSound()
	{
		return (this.sound != null ? sound : Sound.ITEM_ARMOR_EQUIP_GENERIC);
	}

	@Override
	public void setEquipSound(@Nullable Sound sound)
	{
		this.sound = sound;
	}

	@Override
	public @Nullable NamespacedKey getModel()
	{
		return this.model;
	}

	@Override
	public void setModel(@Nullable NamespacedKey key)
	{
		this.model = key;
	}

	@Override
	public @Nullable NamespacedKey getCameraOverlay()
	{
		return this.cameraOverlay;
	}

	@Override
	public void setCameraOverlay(@Nullable NamespacedKey key)
	{
		this.cameraOverlay = key;
	}

	@Override
	public @Nullable Collection<EntityType> getAllowedEntities()
	{
		if (this.allowedEntities == null)
		{
			return null;
		}
		else
		{
			return Collections.unmodifiableSet(this.allowedEntities);
		}
	}

	@Override
	public void setAllowedEntities(@Nullable EntityType entities)
	{
		setAllowedEntities(entities != null ? List.of(entities) : null);
	}

	@Override
	public void setAllowedEntities(@Nullable Collection<EntityType> entities)
	{
		this.allowedEntities = (entities != null ? new HashSet<>(entities) : null);
	}

	@Override
	public void setAllowedEntities(@Nullable Tag<EntityType> tag)
	{
		setAllowedEntities(tag != null ? tag.getValues() : null);
	}

	@Override
	public boolean isDispensable()
	{
		return this.isDispensable;
	}

	@Override
	public void setDispensable(boolean dispensable)
	{
		this.isDispensable = dispensable;
	}

	@Override
	public boolean isSwappable()
	{
		return this.isSwappable;
	}

	@Override
	public void setSwappable(boolean swappable)
	{
		this.isSwappable = swappable;
	}

	@Override
	public boolean isDamageOnHurt()
	{
		return this.isDamageOnHurt;
	}

	@Override
	public void setDamageOnHurt(boolean damage)
	{
		this.isDamageOnHurt = damage;
	}

	@Override
	public boolean isEquipOnInteract()
	{
		return this.isEquipOnInteract;
	}

	@Override
	public void setEquipOnInteract(boolean equip)
	{
		this.isEquipOnInteract = equip;
	}

	@Override
	public boolean canBeSheared()
	{
		return this.isShearable;
	}

	@Override
	public void setCanBeSheared(boolean sheared)
	{
		this.isShearable = sheared;
	}

	@Override
	public @Nullable Sound getShearingSound()
	{
		return this.shearingSound;
	}

	@Override
	public void setShearingSound(@Nullable Sound sound)
	{
		this.shearingSound = sound;
	}

	@Override
	public Map<String, Object> serialize()
	{
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("slot", this.getSlot().name());
		result.put("equip-sound", Registry.SOUND_EVENT.getKeyOrThrow(this.getEquipSound()).asString());
		NamespacedKey model = this.getModel();
		if (model != null)
		{
			result.put("model", model.asString());
		}

		NamespacedKey cameraOverlay = this.getCameraOverlay();
		if (cameraOverlay != null)
		{
			result.put("camera-overlay", cameraOverlay.asString());
		}

		Optional.ofNullable(this.getAllowedEntities()).ifPresent(entities ->
		{
			var rawEntities = entities.stream().map(EntityType::getKey).map(NamespacedKey::asString).collect(Collectors.toSet());
			result.put("allowed-entities", rawEntities);
		});
		result.put("dispensable", this.isDispensable());
		result.put("swappable", this.isSwappable());
		result.put("damage-on-hurt", this.isDamageOnHurt());
		result.put("equip-on-interact", this.isEquipOnInteract());
		result.put("can-be-sheared", this.canBeSheared());

		Optional.ofNullable(this.getShearingSound()).ifPresent(s ->
				result.put("shearing-sound", Registry.SOUND_EVENT.getKeyOrThrow(s).toString()));

		return result;
	}

	public static EquippableComponentMock deserialize(Map<String, Object> map)
	{
		EquipmentSlot slot = EquipmentSlot.valueOf(SerializableMeta.getString(map, "slot", false));
		Sound equipSound = null;
		String equipSoundKey = SerializableMeta.getString(map, "equip-sound", true);
		if (equipSoundKey != null)
		{
			NamespacedKey soundKey = NamespacedKey.fromString(equipSoundKey);
			Preconditions.checkNotNull(soundKey, "The sound key `equip-sound` is not valid!");
			equipSound = Registry.SOUNDS.get(soundKey);
		}
		Sound shearingSound = null;
		String shearingSoundKey = SerializableMeta.getString(map, "shearing-sound", true);
		if (shearingSoundKey != null)
		{
			NamespacedKey soundKey = NamespacedKey.fromString(shearingSoundKey);
			Preconditions.checkNotNull(soundKey, "The sound key `shearing-sound` is not valid!");
			shearingSound = Registry.SOUNDS.get(soundKey);
		}

		String model = SerializableMeta.getString(map, "model", true);
		String cameraOverlay = SerializableMeta.getString(map, "camera-overlay", true);
		Set<EntityType> allowedEntities = null;
		Object allowed = SerializableMeta.getObject(Object.class, map, "allowed-entities", true);
		if (allowed != null)
		{
			allowedEntities = new LinkedHashSet<>(NbtParser.parseList(allowed, o ->
			{
				Preconditions.checkArgument(o instanceof String, "The entity type `allowed-entities` should be a string!");
				NamespacedKey key = NamespacedKey.fromString((String) o);
				return RegistryAccess.registryAccess().getRegistry(RegistryKey.ENTITY_TYPE).get(key);
			}));
		}

		Boolean dispensable = SerializableMeta.getObject(Boolean.class, map, "dispensable", true);
		Boolean swappable = SerializableMeta.getObject(Boolean.class, map, "swappable", true);
		Boolean damageOnHurt = SerializableMeta.getObject(Boolean.class, map, "damage-on-hurt", true);
		Boolean equipOnInteract = SerializableMeta.getObject(Boolean.class, map, "equip-on-interact", true);
		Boolean canBeSheared = SerializableMeta.getObject(Boolean.class, map, "can-be-sheared", true);

		return EquippableComponentMock.builder()
				.equipmentSlot(slot)
				.sound(equipSound != null ? equipSound : Sound.ITEM_ARMOR_EQUIP_GENERIC)
				.shearingSound(shearingSound)
				.model(Optional.ofNullable(model).map(NamespacedKey::fromString).orElse(null))
				.cameraOverlay(Optional.ofNullable(cameraOverlay).map(NamespacedKey::fromString).orElse(null))
				.allowedEntities(allowedEntities)
				.isDispensable(dispensable != null ? dispensable : true)
				.isSwappable(swappable != null ? swappable : true)
				.isDamageOnHurt(damageOnHurt != null ? damageOnHurt : true)
				.isEquipOnInteract(equipOnInteract != null ? equipOnInteract : false)
				.isShearable(canBeSheared != null ? canBeSheared : false)
				.build();
	}

	public static EquippableComponent useDefault()
	{
		return builder().build();
	}


}
