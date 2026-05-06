package org.mockbukkit.mockbukkit.inventory.meta.components;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class EquippableComponentMockTest
{
	private final EquippableComponentMock component = EquippableComponentMock.builder().build();

	@Nested
	class Serialize
	{

		@ParameterizedTest
		@EnumSource(EquipmentSlot.class)
		void givenSlotSerialization(EquipmentSlot equipmentSlot)
		{
			component.setSlot(equipmentSlot);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals(equipmentSlot.name(), actual.get("slot"));
		}

		@Test
		void givenEquipSoundSerialization()
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setEquipSound(Sound.AMBIENT_CAVE);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals("minecraft:ambient.cave", actual.get("equip-sound"));
		}

		@Test
		void givenModelSerialization()
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setModel(NamespacedKey.fromString("mockbukkit:test"));

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals("mockbukkit:test", actual.get("model"));
		}

		@Test
		void givenCameraOverlaySerialization()
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setCameraOverlay(NamespacedKey.fromString("mockbukkit:test"));

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals("mockbukkit:test", actual.get("camera-overlay"));
		}

		@Test
		void givenAllowedEntitiesSerialization()
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setAllowedEntities(Set.of(EntityType.BEE, EntityType.BLAZE));

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals(Set.of("minecraft:blaze", "minecraft:bee"), actual.get("allowed-entities"));
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenDispensableSerialization(boolean value)
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setDispensable(value);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals(value, actual.get("dispensable"));
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenSwappableSerialization(boolean value)
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setSwappable(value);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals(value, actual.get("swappable"));
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenDamageOnHurtSerialization(boolean value)
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setDamageOnHurt(value);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals(value, actual.get("damage-on-hurt"));
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenEquipOnInteractSerialization(boolean value)
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setEquipOnInteract(value);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals(value, actual.get("equip-on-interact"));
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenCanBeSheared(boolean value)
		{
			component.setSlot(EquipmentSlot.HAND);
			component.setCanBeSheared(value);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals(value, actual.get("can-be-sheared"));
		}

		@ParameterizedTest
		@ValueSource(strings = {
			"minecraft:ambient.cave",
			"ambient.underwater.exit"
		})
		void givenShearingSound(String key)
		{
			Sound sound = Registry.SOUND_EVENT.getOrThrow(Objects.requireNonNull(NamespacedKey.fromString(key)));

			component.setSlot(EquipmentSlot.HAND);
			component.setShearingSound(sound);

			Map<String, Object> actual = component.serialize();

			assertNotNull(actual);
			assertEquals("HAND", actual.get("slot"));
			assertEquals(Registry.SOUND_EVENT.getKey(sound).asString(), actual.get("shearing-sound"));
		}

	}

	@Nested
	class Deserialize
	{

		@ParameterizedTest
		@EnumSource(EquipmentSlot.class)
		void givenSlotDeserialization(EquipmentSlot equipmentSlot)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", equipmentSlot.name());

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(equipmentSlot, actual.getSlot());
		}

		@Test
		void givenEquipSoundDeserialization()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("equip-sound", "minecraft:ambient.cave");

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(Sound.AMBIENT_CAVE, actual.getEquipSound());
		}

		@Test
		void givenModelDeserialization()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("model", "mockbukkit:test");

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(NamespacedKey.fromString("mockbukkit:test"), actual.getModel());
		}

		@Test
		void givenCameraOverlayDeserialization()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("camera-overlay", "mockbukkit:test");

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(NamespacedKey.fromString("mockbukkit:test"), actual.getCameraOverlay());
		}

		@Test
		void givenAllowedEntitiesDeserialization()
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("allowed-entities", List.of("minecraft:blaze", "minecraft:bee"));

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(Set.of(EntityType.BEE, EntityType.BLAZE), actual.getAllowedEntities());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenDispensableDeserialization(boolean value)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("dispensable", value);

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(value, actual.isDispensable());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenSwappableDeserialization(boolean value)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("swappable", value);

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(value, actual.isSwappable());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenDamageOnHurtDeserialization(boolean value)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("damage-on-hurt", value);

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(value, actual.isDamageOnHurt());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenEquipOnInteractDeserialization(boolean value)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("equip-on-interact", value);

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(value, actual.isEquipOnInteract());
		}

		@ParameterizedTest
		@ValueSource(booleans = {true, false})
		void givenCanBeShearedDeserialization(boolean value)
		{
			Map<String, Object> input = new LinkedHashMap<>();
			input.put("slot", "HAND");
			input.put("can-be-sheared", value);

			EquippableComponentMock actual = EquippableComponentMock.deserialize(input);

			assertNotNull(actual);
			assertEquals(EquipmentSlot.HAND, actual.getSlot());
			assertEquals(value, actual.canBeSheared());
		}

		@Test
		void givenSerializationAndDeserialization()
		{
			component.setSlot(EquipmentSlot.CHEST);
			component.setEquipSound(Sound.ITEM_ARMOR_EQUIP_DIAMOND);
			component.setDispensable(true);
			component.setDamageOnHurt(true);
			component.setSwappable(true);
			component.setEquipOnInteract(true);
			component.setModel(NamespacedKey.minecraft( "test_model" ));
			component.setCameraOverlay(NamespacedKey.minecraft( "test_camera_overlay" ));
			component.setAllowedEntities(Set.of(EntityType.BLAZE, EntityType.BEE));
			component.setCanBeSheared(true);
			component.setShearingSound(Sound.ITEM_SHEARS_SNIP);

			EquippableComponentMock actual = EquippableComponentMock.deserialize(component.serialize());

			assertNotNull(actual);
			assertEquals(component.getSlot(), actual.getSlot());
			assertEquals(component.getEquipSound(), actual.getEquipSound());
			assertEquals(component.isDispensable(), actual.isDispensable());
			assertEquals(component.isDamageOnHurt(), actual.isDamageOnHurt());
			assertEquals(component.isSwappable(), actual.isSwappable());
			assertEquals(component.isEquipOnInteract(), actual.isEquipOnInteract());
			assertEquals(component.getModel(), actual.getModel());
			assertEquals(component.getCameraOverlay(), actual.getCameraOverlay());
			assertEquals(component.getAllowedEntities(), actual.getAllowedEntities());
			assertEquals(component.canBeSheared(), actual.canBeSheared());
			assertEquals(component.getShearingSound(), actual.getShearingSound());
			assertEquals(component, actual); // other fields not covered by previous assertions
		}

	}

}
