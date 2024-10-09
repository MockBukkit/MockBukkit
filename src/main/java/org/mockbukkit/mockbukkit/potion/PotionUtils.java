package org.mockbukkit.mockbukkit.potion;

import org.mockbukkit.mockbukkit.MockBukkit;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.InternalDataLoadException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Utility class copied from CraftPotionUtil.
 * https://hub.spigotmc.org/stash/projects/SPIGOT/repos/craftbukkit/browse/src/main/java/org/bukkit/craftbukkit/potion/CraftPotionUtil.java#14
 */
public class PotionUtils
{

	private static final BiMap<PotionType, PotionType> upgradeable;
	private static final BiMap<PotionType, PotionType> extendable;

	private PotionUtils()
	{
	}

	static
	{
		try
		{
			upgradeable = loadData("upgradeable", "STRONG_");
			extendable = loadData("extendable", "LONG_");
		}
		catch (IOException e)
		{
			throw new InternalDataLoadException(e);
		}
	}

	private static @NotNull BiMap<PotionType, PotionType> loadData(String filename, String prefix) throws IOException
	{
		String path = "/potion/type_mapping/" + filename + ".json";
		if (MockBukkit.class.getResource(path) == null)
		{
			throw new FileNotFoundException(path);
		}

		var builder = ImmutableBiMap.<PotionType, PotionType>builder();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(MockBukkit.class.getResourceAsStream(path), StandardCharsets.UTF_8)))
		{
			JsonArray values = JsonParser.parseReader(reader).getAsJsonObject().getAsJsonArray("values");

			for (var element : values)
			{
				String key = element.getAsString();
				PotionType original = PotionType.valueOf(key);
				PotionType mapped = PotionType.valueOf(prefix + key);
				builder.put(original, mapped);
			}
		}
		return builder.build();
	}

	public static @Nullable PotionType fromBukkit(@Nullable PotionData data)
	{
		if (data == null)
		{
			return null;
		}

		PotionType type;
		if (data.isUpgraded())
		{
			type = upgradeable.get(data.getType());
		}
		else if (data.isExtended())
		{
			type = extendable.get(data.getType());
		}
		else
		{
			type = data.getType();
		}
		Preconditions.checkNotNull(type, "Unknown potion type from data " + data);

		return type;
	}

	public static @Nullable PotionData toBukkit(@Nullable PotionType type)
	{
		if (type == null)
		{
			return null;
		}

		PotionType potionType;
		potionType = extendable.inverse().get(type);
		if (potionType != null)
		{
			return new PotionData(potionType, true, false);
		}
		potionType = upgradeable.inverse().get(type);
		if (potionType != null)
		{
			return new PotionData(potionType, false, true);
		}

		return new PotionData(type, false, false);
	}

}
