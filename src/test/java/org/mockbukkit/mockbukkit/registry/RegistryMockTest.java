package org.mockbukkit.mockbukkit.registry;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import io.papermc.paper.registry.TypedKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.exception.InternalDataLoadException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class RegistryMockTest
{

	@Test
	void get_Structure()
	{
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(RegistryKey.STRUCTURE);
		assertNotNull(structureRegistryMock.get(Structure.MANSION.getKey()));
	}

	@Test
	void getOrThrow_StructureThrows()
	{
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(RegistryKey.STRUCTURE);
		assertThrows(NoSuchElementException.class, () -> structureRegistryMock.getOrThrow(NamespacedKey.minecraft("invalid")));
	}

	@ParameterizedTest
	@MethodSource("getStructures")
	void getStructureType_NonNull(Structure structure)
	{
		assertNotNull(structure.getStructureType());
	}

	@ParameterizedTest
	@MethodSource("getValues")
	void stream(RegistryKey<? extends Keyed> key)
	{
		assertNotEquals(0, RegistryAccess.registryAccess().getRegistry(key).stream().count());
	}

	@ParameterizedTest
	@MethodSource("getValues")
	void iterator(RegistryKey<? extends Keyed> key)
	{
		assertTrue(RegistryAccess.registryAccess().getRegistry(key).iterator().hasNext());
	}

	@ParameterizedTest
	@MethodSource("getItems")
	void testGetOrThrow(ItemType item)
	{
		assertDoesNotThrow(() -> Registry.ITEM.getOrThrow(item.key()));
		assertThrows(NoSuchElementException.class, () -> Registry.ITEM.getOrThrow(NamespacedKey.fromString("minecraft:yolo")));
	}

	@Test
	void namespaced_key_NotNull()
	{
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(RegistryKey.STRUCTURE);
		assertThrows(NullPointerException.class, () -> structureRegistryMock.get((NamespacedKey) null));
	}
	@Test
	void typed_key_NotNull()
	{
		RegistryMock<Structure> structureRegistryMock = new RegistryMock<>(RegistryKey.STRUCTURE);
		assertThrows(NullPointerException.class, () -> structureRegistryMock.get((TypedKey<Structure>) null));
	}

	@Test
	void key_Invalid()
	{
		assertNull(new RegistryMock<>(RegistryKey.STRUCTURE).get(NamespacedKey.minecraft("invalid")));
	}

	@Test
	void entityType_Registry_LoadsCorrectly() {
		Registry<?> registry = new RegistryMock<>(RegistryKey.ENTITY_TYPE);
		assertNotNull(registry.get(NamespacedKey.minecraft("zombie")));
		assertEquals(org.bukkit.entity.EntityType.ZOMBIE, registry.get(NamespacedKey.minecraft("zombie")));
	}

	@Test
	void particle_Registry_LoadsCorrectly() {
		Registry<?> registry = new RegistryMock<>(RegistryKey.PARTICLE_TYPE);
		assertNotNull(registry.get(NamespacedKey.minecraft("flame")));
		assertEquals(org.bukkit.Particle.FLAME, registry.get(NamespacedKey.minecraft("flame")));
	}

	@Test
	void potion_Registry_LoadsCorrectly() {
		Registry<?> registry = new RegistryMock<>(RegistryKey.POTION);
		assertNotNull(registry.get(NamespacedKey.minecraft("strength")));
		assertEquals(org.bukkit.potion.PotionType.STRENGTH, registry.get(NamespacedKey.minecraft("strength")));
	}

	@Test
	void enum_Registry_HandlesInvalidKey() {
		Registry<?> registry = new RegistryMock<>(RegistryKey.ENTITY_TYPE);
		assertNull(registry.get(NamespacedKey.minecraft("not_an_entity")));
	}

	@Test
	void enum_Registry_StreamContainsAllValues() {
		Registry<?> registry = new RegistryMock<>(RegistryKey.ENTITY_TYPE);
		long registryCount = registry.stream().count();
		long enumCount = Arrays.stream(org.bukkit.entity.EntityType.values())
				.filter(entityType -> entityType != org.bukkit.entity.EntityType.UNKNOWN)
				.count();
		assertEquals(enumCount, registryCount);
	}

	static Stream<RegistryKey<? extends Keyed>> getValues()
	{
		return Arrays.stream(RegistryKey.class.getFields()).filter(field -> field.getType() == RegistryKey.class)
				.map(RegistryMockTest::getRegistryKey);
	}

	static RegistryKey<? extends Keyed> getRegistryKey(Field field)
	{
		try
		{
			return (RegistryKey<? extends Keyed>) field.get(null);
		}
		catch (IllegalAccessException e)
		{
			throw new InternalDataLoadException(e);
		}
	}

	static Stream<Structure> getStructures()
	{
		return Registry.STRUCTURE.stream();
	}

	static Stream<ItemType> getItems()
	{
		return Registry.ITEM.stream();
	}
}
