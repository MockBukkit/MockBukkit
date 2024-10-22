package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.BlockTypeMock;
import be.seeseemelk.mockbukkit.block.banner.PatternTypeMock;
import be.seeseemelk.mockbukkit.damage.DamageTypeMock;
import be.seeseemelk.mockbukkit.enchantments.EnchantmentMock;
import be.seeseemelk.mockbukkit.entity.variant.CatVariantMock;
import be.seeseemelk.mockbukkit.entity.variant.FrogVariantMock;
import be.seeseemelk.mockbukkit.entity.variant.VillagerProfessionMock;
import be.seeseemelk.mockbukkit.entity.variant.VillagerTypeMock;
import be.seeseemelk.mockbukkit.entity.variant.WolfVariantMock;
import be.seeseemelk.mockbukkit.generator.structure.StructureMock;
import be.seeseemelk.mockbukkit.generator.structure.StructureTypeMock;
import be.seeseemelk.mockbukkit.inventory.ItemTypeMock;
import be.seeseemelk.mockbukkit.inventory.MenuTypeMock;
import be.seeseemelk.mockbukkit.inventory.meta.trim.TrimMaterialMock;
import be.seeseemelk.mockbukkit.inventory.meta.trim.TrimPatternMock;
import be.seeseemelk.mockbukkit.map.MapCursorTypeMock;
import be.seeseemelk.mockbukkit.potion.MockPotionEffectType;
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
			throw new RuntimeException(e);
		}
	}

	private void loadKeyedToRegistry(RegistryKey<T> key) throws IOException
	{
		String fileName = "/keyed/" + key.key().value() + ".json";
		this.constructor = (Function<JsonObject, T>) getConstructor(key);
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

	private Function<JsonObject, ? extends Keyed> getConstructor(RegistryKey<T> key)
	{
		Map<RegistryKey<?>, Function<JsonObject, ? extends Keyed>> factoryMap = new HashMap<>();
		factoryMap.put(RegistryKey.STRUCTURE, StructureMock::from);
		factoryMap.put(RegistryKey.STRUCTURE_TYPE, StructureTypeMock::from);
		factoryMap.put(RegistryKey.TRIM_MATERIAL, TrimMaterialMock::from);
		factoryMap.put(RegistryKey.TRIM_PATTERN, TrimPatternMock::from);
		factoryMap.put(RegistryKey.INSTRUMENT, MusicInstrumentMock::from);
		factoryMap.put(RegistryKey.GAME_EVENT, GameEventMock::from);
		factoryMap.put(RegistryKey.ENCHANTMENT, EnchantmentMock::from);
		factoryMap.put(RegistryKey.MOB_EFFECT, MockPotionEffectType::from);
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

		Function<JsonObject, ? extends Keyed> factory = factoryMap.get(key);
		if (factory == null)
		{
			throw new UnimplementedOperationException();
		}

		return factory;
	}

	@Override
	public @Nullable T get(@NotNull NamespacedKey key)
	{
		Preconditions.checkNotNull(key);
		loadIfEmpty();
		return keyedMap.get(key);
	}

	@Override
	public @NotNull T getOrThrow(@NotNull NamespacedKey key) throws IllegalArgumentException
	{
		T t = get(key);
		Preconditions.checkArgument(t != null, "No registry entry found for key %s.", key);
		return t;
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
				 * putIfAbsent fixes the edge case scenario when the constructor initializes class loading of the keyed object,
				 * which during initialization will trigger this exact method, therefore creating duplicate instances of
				 * each keyed object.
				 */
				keyedMap.putIfAbsent(tObject.getKey(), tObject);
			}
		}
	}

}
