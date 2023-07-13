package be.seeseemelk.mockbukkit;

import io.papermc.paper.world.structure.ConfiguredStructure;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.stream.Stream;

public class RegistryMock
{

	private RegistryMock()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	public static <T extends Keyed> Registry<?> createRegistry(Class<T> tClass)
	{

		if (tClass == Structure.class
				|| tClass == StructureType.class
				|| tClass == TrimMaterial.class
				|| tClass == TrimPattern.class
				|| tClass == ConfiguredStructure.class)
		{
			return new Registry<>()
			{
				@Override
				public @Nullable Keyed get(@NotNull NamespacedKey key)
				{
					throw new UnimplementedOperationException("Registry for type " + tClass + " not implemented");
				}

				@NotNull
				@Override
				public Iterator<Keyed> iterator()
				{
					throw new UnimplementedOperationException("Registry for type " + tClass + " not implemented");
				}
			};
		}

		return Stream.of(Registry.class.getDeclaredFields())
				.filter(a -> Registry.class.isAssignableFrom(a.getType()))
				.filter(a -> Modifier.isPublic(a.getModifiers()))
				.filter(a -> Modifier.isStatic(a.getModifiers()))
				.filter(a -> genericTypeMatches(a, tClass))
				.map(RegistryMock::getValue)
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

}
