package org.mockbukkit.mockbukkit.registry;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.art.ArtMock;
import org.mockbukkit.mockbukkit.attribute.AttributeMock;
import org.mockbukkit.mockbukkit.block.BiomeMock;
import org.mockbukkit.mockbukkit.block.BlockTypeMock;
import org.mockbukkit.mockbukkit.block.banner.PatternTypeMock;
import org.mockbukkit.mockbukkit.damage.DamageTypeMock;
import org.mockbukkit.mockbukkit.datacomponent.DataComponentTypeMock;
import org.mockbukkit.mockbukkit.enchantments.EnchantmentMock;
import org.mockbukkit.mockbukkit.entity.variant.CatVariantMock;
import org.mockbukkit.mockbukkit.entity.variant.FrogVariantMock;
import org.mockbukkit.mockbukkit.entity.variant.VillagerProfessionMock;
import org.mockbukkit.mockbukkit.entity.variant.VillagerTypeMock;
import org.mockbukkit.mockbukkit.entity.variant.WolfVariantMock;
import org.mockbukkit.mockbukkit.event.GameEventMock;
import org.mockbukkit.mockbukkit.exception.InternalDataLoadException;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.generator.structure.StructureMock;
import org.mockbukkit.mockbukkit.generator.structure.StructureTypeMock;
import org.mockbukkit.mockbukkit.inventory.ItemTypeMock;
import org.mockbukkit.mockbukkit.inventory.MenuTypeMock;
import org.mockbukkit.mockbukkit.inventory.meta.trim.TrimMaterialMock;
import org.mockbukkit.mockbukkit.inventory.meta.trim.TrimPatternMock;
import org.mockbukkit.mockbukkit.map.MapCursorTypeMock;
import org.mockbukkit.mockbukkit.potion.PotionEffectTypeMock;
import org.mockbukkit.mockbukkit.sound.JukeboxSongMock;
import org.mockbukkit.mockbukkit.sound.MusicInstrumentMock;
import org.mockbukkit.mockbukkit.sound.SoundMock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class RegistryMock<T extends Keyed> implements Registry<T>
{

	/**
	 * These classes have registries that are an exception to the others, as they are wrappers to minecraft internals
	 */
	private final Map<NamespacedKey, T> keyedMap = new HashMap<>();
	private JsonArray keyedData;
	private Function<JsonObject, T> constructor;

	public RegistryMock(RegistryKey<T> key)
	{
		try
		{
			loadKeyedToRegistry(key);
		}
		catch (IOException e)
		{
			throw new InternalDataLoadException(e);
		}
	}

	private void loadKeyedToRegistry(RegistryKey<T> key) throws IOException
	{
		String fileName = "/keyed/" + key.key().value() + ".json";
		this.constructor = (Function<JsonObject, T>) getConstructorFunction(key);
		try (InputStream stream = MockBukkit.class.getResourceAsStream(fileName))
		{
			if (stream == null)
			{
				throw new FileNotFoundException(fileName);
			}
			JsonElement element = JsonParser.parseReader(new InputStreamReader(stream));
			keyedData = element.getAsJsonObject().get("values").getAsJsonArray();
		}
	}

	private Function<JsonObject, ? extends Keyed> getConstructorFunction(RegistryKey<T> key)
	{
		Map<RegistryKey<?>, Function<JsonObject, ? extends Keyed>> factoryMap = new HashMap<>();
		factoryMap.put(RegistryKey.STRUCTURE, StructureMock::from);
		factoryMap.put(RegistryKey.STRUCTURE_TYPE, StructureTypeMock::from);
		factoryMap.put(RegistryKey.TRIM_MATERIAL, TrimMaterialMock::from);
		factoryMap.put(RegistryKey.TRIM_PATTERN, TrimPatternMock::from);
		factoryMap.put(RegistryKey.INSTRUMENT, MusicInstrumentMock::from);
		factoryMap.put(RegistryKey.GAME_EVENT, GameEventMock::from);
		factoryMap.put(RegistryKey.ENCHANTMENT, EnchantmentMock::from);
		factoryMap.put(RegistryKey.MOB_EFFECT, PotionEffectTypeMock::from);
		factoryMap.put(RegistryKey.DAMAGE_TYPE, DamageTypeMock::from);
		factoryMap.put(RegistryKey.ITEM, ItemTypeMock::from);
		factoryMap.put(RegistryKey.BLOCK, BlockTypeMock::from);
		factoryMap.put(RegistryKey.WOLF_VARIANT, WolfVariantMock::from);
		factoryMap.put(RegistryKey.JUKEBOX_SONG, JukeboxSongMock::from);
		factoryMap.put(RegistryKey.CAT_VARIANT, CatVariantMock::from);
		factoryMap.put(RegistryKey.VILLAGER_PROFESSION, VillagerProfessionMock::from);
		factoryMap.put(RegistryKey.VILLAGER_TYPE, VillagerTypeMock::from);
		factoryMap.put(RegistryKey.FROG_VARIANT, FrogVariantMock::from);
		factoryMap.put(RegistryKey.MAP_DECORATION_TYPE, MapCursorTypeMock::from);
		factoryMap.put(RegistryKey.MENU, MenuTypeMock::from);
		factoryMap.put(RegistryKey.BANNER_PATTERN, PatternTypeMock::from);
		factoryMap.put(RegistryKey.PAINTING_VARIANT, ArtMock::from);
		factoryMap.put(RegistryKey.ATTRIBUTE, AttributeMock::from);
		factoryMap.put(RegistryKey.BIOME, BiomeMock::from);
		factoryMap.put(RegistryKey.SOUND_EVENT, SoundMock::from);
		factoryMap.put(RegistryKey.FLUID, SoundMock::from);
		factoryMap.put(RegistryKey.DATA_COMPONENT_TYPE, DataComponentTypeMock::from);
		// Remove the EntityTypeMock mapping as it's an enum
		factoryMap.remove(RegistryKey.ENTITY_TYPE);

		// Add special handling for enum-based registry types
		if (isEnumBasedRegistry(key)) {
			return jsonObject -> createEnumWrapper(jsonObject, key);
		}

		Function<JsonObject, ? extends Keyed> constructorFunction = factoryMap.get(key);
		if (constructorFunction == null)
		{
			throw new UnimplementedOperationException();
		}

		return constructorFunction;
	}

	private boolean isEnumBasedRegistry(RegistryKey<?> key) {
		return key == RegistryKey.ENTITY_TYPE
			|| key == RegistryKey.PARTICLE_TYPE
			|| key == RegistryKey.POTION;
	}

	private T createEnumWrapper(JsonObject jsonObject, RegistryKey<T> key) {
		// Extract the key from the JSON object
		String id = jsonObject.get("key").getAsString();
		NamespacedKey namespacedKey = NamespacedKey.fromString(id);

		// Get the enum class from the registry key's type parameter
		Class<?> enumClass = getEnumClassForRegistryKey(key);
		if (enumClass == null || !enumClass.isEnum()) {
			throw new IllegalStateException("Registry key " + key + " is marked as enum but has no enum class");
		}

		// Find the enum constant by name
		String enumName = namespacedKey.getKey().toUpperCase();
		try {
			// Convert the enum name to the corresponding enum constant
			Enum<?> enumValue = Enum.valueOf((Class<? extends Enum>) enumClass, enumName);
			return (T) enumValue;
		} catch (IllegalArgumentException | ClassCastException e) {
			throw new IllegalStateException("Could not find enum constant " + enumName + " in " + enumClass, e);
		}
	}

	private Class<?> getEnumClassForRegistryKey(RegistryKey<?> key) {
		Map<RegistryKey<?>, Class<?>> enumMap = new HashMap<>();
		enumMap.put(RegistryKey.ENTITY_TYPE, org.bukkit.entity.EntityType.class);
		enumMap.put(RegistryKey.PARTICLE_TYPE, org.bukkit.Particle.class);
		enumMap.put(RegistryKey.POTION, org.bukkit.potion.PotionType.class);

		return enumMap.get(key);
	}

	@Override
	public @Nullable T get(@NotNull NamespacedKey key)
	{
		Preconditions.checkNotNull(key);
		loadIfEmpty();
		return keyedMap.get(key);
	}

	@Override
	public @NotNull T getOrThrow(@NotNull NamespacedKey namespacedKey)
	{
		Preconditions.checkNotNull(namespacedKey);
		loadIfEmpty();
		T value = this.keyedMap.get(namespacedKey);
		if (value == null)
		{
			throw new java.util.NoSuchElementException("No value for " + namespacedKey + " in " + this);
		}
		return value;
	}

	@Override
	public @NotNull Stream<T> stream()
	{
		loadIfEmpty();
		return keyedMap.values().stream();
	}

	@NotNull
	@Override
	public Iterator<T> iterator()
	{
		loadIfEmpty();
		return keyedMap.values().iterator();
	}

	private void loadIfEmpty()
	{
		if (keyedMap.isEmpty())
		{
			for (JsonElement structureJSONElement : keyedData)
			{
				JsonObject structureJSONObject = structureJSONElement.getAsJsonObject();
				T tObject = constructor.apply(structureJSONObject);
				/*
				 * putIfAbsent fixes the edge case scenario where the constructor initializes class loading of the keyed object.
				 * During this initialization, the loadIfEmpty method might be triggered again, leading to potential duplicate
				 * instances of each keyed object. By using putIfAbsent, we ensure that only one instance of each keyed object
				 * is added to the map, preventing duplicates.
				 */
				keyedMap.putIfAbsent(tObject.getKey(), tObject);
			}
		}
	}

}
