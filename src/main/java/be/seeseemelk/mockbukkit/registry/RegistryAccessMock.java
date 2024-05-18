package be.seeseemelk.mockbukkit.registry;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ReflectionAccessException;
import be.seeseemelk.mockbukkit.RegistryMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class RegistryAccessMock implements RegistryAccess
{

	private final Map<RegistryKey<?>, Registry<?>> registries = new HashMap();
	private static BiMap<RegistryKey<?>, String> CLASS_NAME_TO_KEY_MAP;


	@Override
	public @Nullable <T extends Keyed> Registry<T> getRegistry(@NotNull Class<T> type)
	{
		if (type.getName().equals("io.papermc.paper.world.structure.ConfiguredStructure"))
		{
			return createConfiguredStructureRegistry();
		}
		RegistryKey<T> registryKey = determineRegistryKeyFromClass(type);
		return getRegistry(registryKey);
	}

	private <T extends Keyed> RegistryKey<T> determineRegistryKeyFromClass(@NotNull Class<T> type)
	{
		if (CLASS_NAME_TO_KEY_MAP == null)
		{
			CLASS_NAME_TO_KEY_MAP = createClassToKeyConversions();
		}
		return (RegistryKey<T>) CLASS_NAME_TO_KEY_MAP.inverse().get(type.getName());
	}

	@Override
	public @NotNull <T extends Keyed> Registry<T> getRegistry(@NotNull RegistryKey<T> registryKey)
	{
		if (registries.containsKey(registryKey))
		{
			return (Registry<T>) registries.get(registryKey);
		}
		Registry<T> registry = (Registry<T>) createRegistry(registryKey);
		registries.put(registryKey, registry);
		return registry;
	}

	private static <T extends Keyed> Registry<?> createRegistry(RegistryKey<T> key)
	{
		if (key.key().asString().equals("minecraft:worldgen/structure"))
		{
			return createConfiguredStructureRegistry();
		}
		if (getOutlierKeyedRegistryKeys().contains(key))
		{
			return new RegistryMock<>(key);
		}

		return Stream.of(Registry.class.getDeclaredFields())
				.filter(a -> Registry.class.isAssignableFrom(a.getType()))
				.filter(a -> Modifier.isPublic(a.getModifiers()))
				.filter(a -> Modifier.isStatic(a.getModifiers()))
				.filter(a -> genericTypeMatches(a, CLASS_NAME_TO_KEY_MAP.get(key)))
				.map(RegistryAccessMock::getValue)
				.filter(Objects::nonNull)
				.findAny()
				.orElseThrow(() -> new UnimplementedOperationException("Could not find registry for " + key));
	}


	private static boolean genericTypeMatches(Field a, String className)
	{
		if (a.getGenericType() instanceof ParameterizedType type)
		{
			return type.getActualTypeArguments()[0].getTypeName().equals(className);
		}
		return false;
	}


	private static List<RegistryKey<? extends Keyed>> getOutlierKeyedRegistryKeys()
	{
		return List.of(RegistryKey.STRUCTURE, RegistryKey.STRUCTURE_TYPE, RegistryKey.TRIM_MATERIAL,
				RegistryKey.TRIM_PATTERN, RegistryKey.INSTRUMENT, RegistryKey.GAME_EVENT, RegistryKey.ENCHANTMENT,
				RegistryKey.MOB_EFFECT, RegistryKey.DAMAGE_TYPE, RegistryKey.ITEM, RegistryKey.BLOCK, RegistryKey.WOLF_VARIANT);
	}


	private static Registry<?> getValue(Field a)
	{
		try
		{
			return (Registry<?>) a.get(null);
		}
		catch (IllegalAccessException e)
		{
			throw new ReflectionAccessException("Could not access field " + a.getDeclaringClass().getSimpleName() + "." + a.getName());
		}
	}


	private static BiMap<RegistryKey<?>, String> createClassToKeyConversions()
	{
		String fileName = "/registries/registry_key_class_relation.json";
		BiMap<RegistryKey<?>,String> output = HashBiMap.create();
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream(fileName))
		{
			if (inputStream == null)
			{
				throw new IOException("File not found: " + fileName);
			}
			JsonObject object = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonObject();
			for (RegistryKey<?> registryKey : getAllKeys())
			{
				String className = object.get(registryKey.key().asString()).getAsString();
				output.put(registryKey, className);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		return output;
	}

	private static List<RegistryKey<?>> getAllKeys()
	{
		List<RegistryKey<?>> output = new ArrayList<>();
		for (final Field field : RegistryKey.class.getFields())
		{
			if (field.getType() == RegistryKey.class)
			{
				try
				{
					output.add((RegistryKey<?>) field.get(null));
				}
				catch (IllegalAccessException e)
				{
					throw new RuntimeException(e);
				}
			}
		}
		return output;
	}

	private static <T extends Keyed> Registry<T> createConfiguredStructureRegistry()
	{
		return new Registry<>()
		{
			@Override
			public @Nullable T get(@NotNull NamespacedKey key)
			{
				throw new UnimplementedOperationException("Registry for type ConfiguredStructure not implemented");
			}

			@Override
			public @NotNull Stream<T> stream()
			{
				throw new UnimplementedOperationException("Registry for type ConfiguredStructure not implemented");
			}

			@NotNull
			@Override
			public Iterator<T> iterator()
			{
				throw new UnimplementedOperationException("Registry for type ConfiguredStructure not implemented");
			}
		};
	}

}
