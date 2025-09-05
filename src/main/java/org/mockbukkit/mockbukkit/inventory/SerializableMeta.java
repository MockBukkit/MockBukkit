package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.inventory.meta.ItemMeta;
import org.mockbukkit.mockbukkit.exception.ItemSerializationException;
import org.mockbukkit.mockbukkit.inventory.meta.ArmorMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ArmorStandMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.AxolotlBucketMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BannerMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BlockStateMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BookMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.BundleMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ColorableArmorMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.CompassMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.CrossbowMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.EnchantmentStorageMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.FireworkEffectMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.FireworkMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.KnowledgeBookMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.LeatherArmorMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.MapMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.OminousBottleMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.PotionMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.ShieldMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.SkullMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.SpawnEggMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.SuspiciousStewMetaMock;
import org.mockbukkit.mockbukkit.inventory.meta.TropicalFishBucketMetaMock;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;

@SerializableAs("ItemMeta")
public class SerializableMeta implements ConfigurationSerializable
{

	private static final String TYPE_FIELD = "meta-type";

	private static final ImmutableMap<Class<? extends ItemMetaMock>, String> classMap =  ImmutableMap.<Class<? extends ItemMetaMock>, String>builder()
			.put(ArmorMetaMock.class, "ARMOR")
			.put(ArmorStandMetaMock.class, "ARMOR_STAND")
			.put(BannerMetaMock.class, "BANNER")
			.put(BlockStateMetaMock.class, "TILE_ENTITY")
			.put(BookMetaMock.class, "BOOK")
			// .put(BookSignedMetaMock.class, "BOOK_SIGNED")
			.put(SkullMetaMock.class, "SKULL")
			.put(LeatherArmorMetaMock.class, "LEATHER_ARMOR")
			.put(ColorableArmorMetaMock.class, "COLORABLE_ARMOR")
			.put(MapMetaMock.class, "MAP")
			.put(PotionMetaMock.class, "POTION")
			.put(ShieldMetaMock.class, "SHIELD")
			.put(SpawnEggMetaMock.class, "SPAWN_EGG")
			.put(EnchantmentStorageMetaMock.class, "ENCHANTED")
			.put(FireworkMetaMock.class, "FIREWORK")
			.put(FireworkEffectMetaMock.class, "FIREWORK_EFFECT")
			.put(KnowledgeBookMetaMock.class, "KNOWLEDGE_BOOK")
			.put(TropicalFishBucketMetaMock.class, "TROPICAL_FISH_BUCKET")
			.put(AxolotlBucketMetaMock.class, "AXOLOTL_BUCKET")
			.put(CrossbowMetaMock.class, "CROSSBOW")
			.put(SuspiciousStewMetaMock.class, "SUSPICIOUS_STEW")
			// .put(EntityTagMetaMock.class, "ENTITY_TAG")
			.put(CompassMetaMock.class, "COMPASS")
			.put(BundleMetaMock.class, "BUNDLE")
			// .put(MusicInstrumentMetaMock.class, "MUSIC_INSTRUMENT")
			.put(OminousBottleMetaMock.class, "OMINOUS_BOTTLE")
			.put(ItemMetaMock.class, "UNSPECIFIC")
			.build();
	private static final ImmutableMap<String, Function<Map<String, Object>, ? extends ItemMetaMock>> factoryMap = compileFactoryMap();
	
	private static ImmutableMap<String, Function<Map<String, Object>,? extends ItemMetaMock>> compileFactoryMap()
	{
		ImmutableMap.Builder<String, Function<Map<String, Object>, ? extends ItemMetaMock>> builder = ImmutableMap.builder();

		for (Map.Entry<Class<? extends ItemMetaMock>, String> mapping : SerializableMeta.classMap.entrySet())
		{
			Class<? extends ItemMetaMock> clazz = mapping.getKey();

			// Deserialize method
			try
			{
				Method deserializeMethod = clazz.getDeclaredMethod("deserialize", Map.class);
				builder.put(mapping.getValue(), a ->
				{
					try
					{
						return (ItemMetaMock) deserializeMethod.invoke(null, a);
					}
					catch (IllegalAccessException | InvocationTargetException e)
					{
						throw new ItemSerializationException("Error while calling deserialize method.", e);
					}
				});
				continue;
			}
			catch (NoSuchMethodException e)
			{
				// If we don't found the method, we can then try the next method
			}

			// Value of
			try
			{
				Method deserializeMethod = clazz.getDeclaredMethod("valueOf", Map.class);
				builder.put(mapping.getValue(), a ->
				{
					try
					{
						return (ItemMetaMock) deserializeMethod.invoke(null, a);
					}
					catch (IllegalAccessException | InvocationTargetException e)
					{
						throw new ItemSerializationException("Error while calling valueOf method.", e);
					}
				});
				continue;
			}
			catch (NoSuchMethodException e)
			{
				// If we don't found the method, we can then try the next method
			}

			// Constructor
			try
			{
				Constructor<? extends ItemMetaMock> constructor = clazz.getDeclaredConstructor(Map.class);
				builder.put(mapping.getValue(), a ->
				{
					try
					{
						return constructor.newInstance(a);
					}
					catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
					{
						throw new ItemSerializationException("Error while calling deserialize constructor.", e);
					}
				});
			}
			catch (NoSuchMethodException e)
			{
				String message = String.format("""
						There is no deserialization method available for class %s. Make sure that the class follows the
						rules of class org.bukkit.configuration.serialization.ConfigurationSerializable.

						To be compliant it should have one of the following:
						- A static method "deserialize" that accepts a single Map<String, Object> and returns the class instance.
						- A static method "valueOf" that accepts a single Map<String, Object> and returns the class instance.
						- A constructor that accepts a single Map<String, Object>.
						""", clazz.getName());
				throw new UnsupportedOperationException(message, e);
			}
		}
		return builder.build();
	}

	private SerializableMeta()
	{
	}

	public static ItemMeta deserialize(Map<String, Object> map)
	{
		Preconditions.checkArgument(map != null, "Cannot deserialize null map");

		String type = SerializableMeta.getString(map, SerializableMeta.TYPE_FIELD, false);
		Function<Map<String, Object>, ? extends ItemMetaMock> constructor = SerializableMeta.factoryMap.get(type);

		if (constructor == null)
		{
			throw new IllegalArgumentException(type + " is not a valid " + SerializableMeta.TYPE_FIELD);
		}

		return constructor.apply(map);
	}

	@Override
	public Map<String, Object> serialize()
	{
		throw new AssertionError();
	}

	public static String getString(Map<?, ?> map, Object field, boolean nullable)
	{
		return SerializableMeta.getObject(String.class, map, field, nullable);
	}

	public static <T> T getObject(Class<T> clazz, Map<?, ?> map, Object field, boolean nullable)
	{
		final Object object = map.get(field);

		if (clazz.isInstance(object))
		{
			return clazz.cast(object);
		}

		// SPIGOT-7675 - More lenient conversion of floating point numbers from other number types:
		if (clazz == Float.class || clazz == Double.class)
		{
			if (object instanceof Number number)
			{
				if (clazz == Float.class)
				{
					return clazz.cast(number.floatValue());
				}
				else
				{
					return clazz.cast(number.doubleValue());
				}
			}
		}

		if (object == null)
		{
			if (!nullable)
			{
				throw new NoSuchElementException(map + " does not contain " + field);
			}
			return null;
		}
		throw new IllegalArgumentException(field + "(" + object + ") is not a valid " + clazz);
	}

}
