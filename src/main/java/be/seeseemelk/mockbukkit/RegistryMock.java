package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.block.BlockTypeMock;
import be.seeseemelk.mockbukkit.damage.DamageTypeMock;
import be.seeseemelk.mockbukkit.enchantments.EnchantmentMock;
import be.seeseemelk.mockbukkit.entity.variant.CatVariantMock;
import be.seeseemelk.mockbukkit.entity.variant.VillagerProfessionMock;
import be.seeseemelk.mockbukkit.entity.variant.WolfVariantMock;
import be.seeseemelk.mockbukkit.generator.structure.StructureMock;
import be.seeseemelk.mockbukkit.generator.structure.StructureTypeMock;
import be.seeseemelk.mockbukkit.inventory.ItemTypeMock;
import be.seeseemelk.mockbukkit.inventory.meta.trim.TrimMaterialMock;
import be.seeseemelk.mockbukkit.inventory.meta.trim.TrimPatternMock;
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
		if (key == RegistryKey.STRUCTURE)
		{
			return StructureMock::from;
		}
		else if (key == RegistryKey.STRUCTURE_TYPE)
		{
			return StructureTypeMock::from;
		}
		else if (key == RegistryKey.TRIM_MATERIAL)
		{
			return TrimMaterialMock::from;
		}
		else if (key == RegistryKey.TRIM_PATTERN)
		{
			return TrimPatternMock::from;
		}
		else if (key == RegistryKey.INSTRUMENT)
		{
			return MusicInstrumentMock::from;
		}
		else if (key == RegistryKey.GAME_EVENT)
		{
			return GameEventMock::from;
		}
		else if (key == RegistryKey.ENCHANTMENT)
		{
			return EnchantmentMock::from;
		}
		else if (key == RegistryKey.MOB_EFFECT)
		{
			return MockPotionEffectType::from;
		}
		else if (key == RegistryKey.DAMAGE_TYPE)
		{
			return DamageTypeMock::from;
		}
		else if (key == RegistryKey.ITEM)
		{
			return ItemTypeMock::from;
		}
		else if (key == RegistryKey.BLOCK)
		{
			return BlockTypeMock::from;
		}
		else if (key == RegistryKey.WOLF_VARIANT)
		{
			return WolfVariantMock::from;
		}
		else if (key == RegistryKey.JUKEBOX_SONG)
		{
			return JukeboxSongMock::from;
		}
		else if (key == RegistryKey.CAT_VARIANT)
		{
			return CatVariantMock::from;
		}
		else if (key == RegistryKey.VILLAGER_PROFESSION)
		{
			return VillagerProfessionMock::from;
		}
		else
		{
			throw new UnimplementedOperationException();
		}
	}

	@Override
	public @Nullable T get(@NotNull NamespacedKey key)
	{
		Preconditions.checkNotNull(key);
		loadIfEmpty();
		return keyedMap.get(key);
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
