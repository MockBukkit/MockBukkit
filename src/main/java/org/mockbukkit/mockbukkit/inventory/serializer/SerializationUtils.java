package org.mockbukkit.mockbukkit.inventory.serializer;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@UtilityClass
public class SerializationUtils
{
	public static @NotNull Map<String, Object> serialize(@NotNull ConfigurationSerializable configurationSerializable)
	{
		return configurationSerializable.serialize();
	}

	public static @NotNull List<Map<String, Object>> serialize(@NotNull Collection<? extends ConfigurationSerializable> projectiles)
	{
		return projectiles.stream().map(SerializationUtils::serialize).toList();
	}

	public static ItemStack deserialize(@NotNull Object args)
	{
		Preconditions.checkArgument(args instanceof Map<?, ?>, "Args is not a Map");

		return ItemStack.deserialize((Map<String, Object>) args);
	}

	public static List<ItemStack> deserialize(@NotNull Collection<Object> args)
	{
		return args.stream().map(SerializationUtils::deserialize).toList();
	}

	public static GsonBuilder getDefaultBuilder()
	{
		return new GsonBuilder()
				.registerTypeHierarchyAdapter(ConfigurationSerializable.class, new ConfigurationTypeAdapter());
	}

	public static Gson createDefaultBuilder()
	{
		return getDefaultBuilder().create();
	}

}
