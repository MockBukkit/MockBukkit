package org.mockbukkit.mockbukkit.registry;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ReflectionAccessException;
import org.mockbukkit.mockbukkit.RegistryMock;
import org.mockbukkit.mockbukkit.UnimplementedOperationException;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class RegistryAccessMock implements RegistryAccess
{

	private final Map<RegistryKey<?>, Registry<?>> registries = new HashMap<>();
	private static final BiMap<RegistryKey<?>, String> CLASS_NAME_KEY_MAP = createClassToKeyConversions();


	@Override
	@Deprecated(forRemoval = true, since = "1.20.6")
	public @Nullable <T extends Keyed> Registry<T> getRegistry(@NotNull Class<T> type)
	{
		RegistryKey<T> registryKey = determineRegistryKeyFromClass(type);
		if (registryKey == null)
		{
			return findSimpleRegistry(type);
		}
		return getRegistry(registryKey);
	}

	private <T extends Keyed> RegistryKey<T> determineRegistryKeyFromClass(@NotNull Class<T> type)
	{
		return (RegistryKey<T>) CLASS_NAME_KEY_MAP.inverse().get(type.getName());
	}

	@Override
	public @NotNull <T extends Keyed> Registry<T> getRegistry(@NotNull RegistryKey<T> registryKey)
	{
		if (registries.containsKey(registryKey))
		{
			return (Registry<T>) registries.get(registryKey);
		}
		Registry<T> registry = createRegistry(registryKey);
		registries.put(registryKey, registry);
		return registry;
	}

	private static <T extends Keyed> Registry<T> createRegistry(RegistryKey<T> key)
	{
		if (getOutlierKeyedRegistryKeys().contains(key))
		{
			return new RegistryMock<>(key);
		}
		return findSimpleRegistry((Class<T>) getClass(CLASS_NAME_KEY_MAP.get(key)));
	}

	private static Class<?> getClass(String className)
	{
		try
		{
			return Class.forName(className);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	private static boolean genericTypeMatches(Field a, Class<?> tClass)
	{
		if (a.getGenericType() instanceof ParameterizedType type)
		{
			return type.getActualTypeArguments()[0].equals(tClass);
		}
		return false;
	}


	private static List<RegistryKey<? extends Keyed>> getOutlierKeyedRegistryKeys()
	{
		return List.of(RegistryKey.STRUCTURE, RegistryKey.STRUCTURE_TYPE, RegistryKey.TRIM_MATERIAL,
				RegistryKey.TRIM_PATTERN, RegistryKey.INSTRUMENT, RegistryKey.GAME_EVENT, RegistryKey.ENCHANTMENT,
				RegistryKey.MOB_EFFECT, RegistryKey.DAMAGE_TYPE, RegistryKey.ITEM, RegistryKey.BLOCK,
				RegistryKey.WOLF_VARIANT, RegistryKey.JUKEBOX_SONG, RegistryKey.CAT_VARIANT, RegistryKey.VILLAGER_PROFESSION,
				RegistryKey.VILLAGER_TYPE, RegistryKey.FROG_VARIANT, RegistryKey.MAP_DECORATION_TYPE, RegistryKey.BANNER_PATTERN,
				RegistryKey.MENU);
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
		BiMap<RegistryKey<?>, String> output = HashBiMap.create();
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

	private static <T extends Keyed> Registry<T> findSimpleRegistry(Class<T> tClass)
	{
		return (Registry<T>) Stream.of(Registry.class.getDeclaredFields())
				.filter(a -> Registry.class.isAssignableFrom(a.getType()))
				.filter(a -> Modifier.isPublic(a.getModifiers()))
				.filter(a -> Modifier.isStatic(a.getModifiers()))
				.filter(a -> genericTypeMatches(a, tClass))
				.map(RegistryAccessMock::getValue)
				.filter(Objects::nonNull)
				.findAny()
				.orElseThrow(() -> new UnimplementedOperationException("Could not find registry for " + tClass));
	}

}
