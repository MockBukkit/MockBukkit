package be.seeseemelk.mockbukkit;

import be.seeseemelk.mockbukkit.damage.DamageTypeMock;
import be.seeseemelk.mockbukkit.enchantments.EnchantmentMock;
import be.seeseemelk.mockbukkit.generator.structure.StructureMock;
import be.seeseemelk.mockbukkit.generator.structure.StructureTypeMock;
import be.seeseemelk.mockbukkit.inventory.meta.trim.TrimMaterialMock;
import be.seeseemelk.mockbukkit.inventory.meta.trim.TrimPatternMock;
import be.seeseemelk.mockbukkit.potion.MockPotionEffectType;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.paper.world.structure.ConfiguredStructure;
import org.bukkit.GameEvent;
import org.bukkit.Keyed;
import org.bukkit.MusicInstrument;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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

	RegistryMock(Class<T> tClass)
	{
		try
		{
			loadKeyedToRegistry(tClass);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private void loadKeyedToRegistry(Class<T> tClass) throws IOException
	{
		String classNameLowerCase = tClass.getSimpleName().toLowerCase(Locale.ROOT);
		String fileName = "/keyed/" + classNameLowerCase + ".json";
		this.constructor = (Function<JsonObject, T>) getConstructor(tClass);
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

	private Function<JsonObject, ? extends Keyed> getConstructor(Class<T> tClass)
	{
		if (tClass == Structure.class)
		{
			return StructureMock::from;
		}
		else if (tClass == StructureType.class)
		{
			return StructureTypeMock::from;
		}
		else if (tClass == TrimMaterial.class)
		{
			return TrimMaterialMock::from;
		}
		else if (tClass == TrimPattern.class)
		{
			return TrimPatternMock::from;
		}
		else if (tClass == MusicInstrument.class)
		{
			return MusicInstrumentMock::from;
		}
		else if (tClass == GameEvent.class)
		{
			return GameEventMock::from;
		}
		else if (tClass == Enchantment.class)
		{
			return EnchantmentMock::from;
		}
		else if (tClass == PotionEffectType.class)
		{
			return MockPotionEffectType::from;
		}
		else if (tClass == DamageType.class)
		{
			return DamageTypeMock::from;
		}
		else
		{
			throw new UnimplementedOperationException();
		}
	}

	public static <T extends Keyed> Registry<?> createRegistry(Class<T> tClass)
	{
		if (tClass == ConfiguredStructure.class)
		{
			return new Registry<T>()
			{
				@Override
				public @Nullable T get(@NotNull NamespacedKey key)
				{
					throw new UnimplementedOperationException("Registry for type " + tClass + " not implemented");
				}

				@Override
				public @NotNull Stream<T> stream()
				{
					throw new UnimplementedOperationException("Registry for type " + tClass + " not implemented");
				}

				@NotNull
				@Override
				public Iterator<T> iterator()
				{
					throw new UnimplementedOperationException("Registry for type " + tClass + " not implemented");
				}
			};
		}
		if (getOutlierKeyedClasses().contains(tClass))
		{
			return new RegistryMock<>(tClass);
		}

		return Stream.of(Registry.class.getDeclaredFields()).filter(a -> Registry.class.isAssignableFrom(a.getType()))
				.filter(a -> Modifier.isPublic(a.getModifiers())).filter(a -> Modifier.isStatic(a.getModifiers()))
				.filter(a -> genericTypeMatches(a, tClass)).map(RegistryMock::getValue).filter(Objects::nonNull)
				.findAny().orElseThrow(() -> new UnimplementedOperationException(
						"Could not find registry for " + tClass.getSimpleName()));
	}

	private static boolean genericTypeMatches(Field a, Class<?> clazz)
	{
		if (a.getGenericType() instanceof ParameterizedType type)
		{
			return type.getActualTypeArguments()[0] == clazz;
		}
		return false;
	}

	private static Registry<?> getValue(Field a)
	{
		try
		{
			return (Registry<?>) a.get(null);
		}
		catch (IllegalAccessException e)
		{
			throw new ReflectionAccessException(
					"Could not access field " + a.getDeclaringClass().getSimpleName() + "." + a.getName());
		}
	}

	private static List<Class<? extends Keyed>> getOutlierKeyedClasses()
	{
		return List.of(Structure.class, PotionEffectType.class, StructureType.class, TrimMaterial.class,
				TrimPattern.class, MusicInstrument.class, GameEvent.class, Enchantment.class, DamageType.class);
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
				keyedMap.put(tObject.getKey(), tObject);
			}
		}
	}

}
