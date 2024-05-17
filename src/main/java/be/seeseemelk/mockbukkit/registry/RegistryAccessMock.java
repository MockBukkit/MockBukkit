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
	private static BiMap<RegistryKey<?>, Class<?>> CLASS_TO_KEY_MAP;


	@Override
	public @Nullable <T extends Keyed> Registry<T> getRegistry(@NotNull Class<T> type)
	{
		RegistryKey<T> registryKey = determineRegistryKeyFromClass(type);
		if (registries.containsKey(type))
		{
			return (Registry<T>) registries.get(type);
		}
		return (Registry<T>) createRegistry(type);
	}

	private <T extends Keyed> RegistryKey<T> determineRegistryKeyFromClass(@NotNull Class<T> type)
	{
		if (CLASS_TO_KEY_MAP == null)
		{
			CLASS_TO_KEY_MAP = createClassToKeyConversions();
		}

		if (type == ConfiguredStructure.class)
		{
			// Too much of a hassle to fix this, would require changing the access on a class as RegistryKey is a sealed class
			// Not possible to Mock it
			throw new UnimplementedOperationException();
		}
		return (RegistryKey<T>) CLASS_TO_KEY_MAP.inverse().get(type);
	}

	@Override
	public @NotNull <T extends Keyed> Registry<T> getRegistry(@NotNull RegistryKey<T> registryKey)
	{
		if (CLASS_TO_KEY_MAP == null)
		{
			CLASS_TO_KEY_MAP = createClassToKeyConversions();
		}

		if (registries.containsKey(registryKey))
		{
			return (Registry<T>) registries.get(registryKey);
		}
		return (Registry<T>) createRegistry((Class<T>) CLASS_TO_KEY_MAP.get(registryKey));
	}

	private static <T extends Keyed> Registry<?> createRegistry(Class<T> tClass)
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

		return Stream.of(Registry.class.getDeclaredFields())
				.filter(a -> Registry.class.isAssignableFrom(a.getType()))
				.filter(a -> Modifier.isPublic(a.getModifiers()))
				.filter(a -> Modifier.isStatic(a.getModifiers()))
				.filter(a -> genericTypeMatches(a, tClass))
				.map(RegistryAccessMock::getValue)
				.filter(Objects::nonNull)
				.findAny()
				.orElseThrow(() -> new UnimplementedOperationException("Could not find registry for " + tClass.getSimpleName()));
	}


	private static boolean genericTypeMatches(Field a, Class<?> clazz)
	{
		if (a.getGenericType() instanceof ParameterizedType type)
		{
			return type.getActualTypeArguments()[0] == clazz;
		}
		return false;
	}


	private static List<Class<? extends Keyed>> getOutlierKeyedClasses()
	{
		return List.of(Structure.class, PotionEffectType.class,
				StructureType.class, TrimMaterial.class, TrimPattern.class,
				MusicInstrument.class, GameEvent.class, Enchantment.class, DamageType.class);
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


	private static BiMap<RegistryKey<?>, Class<?>> createClassToKeyConversions()
	{
		String fileName = "/registries/registry_key_class_relation.json";
		BiMap<RegistryKey<?>, Class<?>> output = HashBiMap.create();
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
				Class<?> type = RegistryAccessMock.class.getClassLoader().loadClass(className);
				output.put(registryKey, type);
			}
		}
		catch (IOException | ClassNotFoundException e)
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

}
