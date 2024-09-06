package org.mockbukkit.mockbukkit.entity.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.entity.EntityType;

import org.mockbukkit.mockbukkit.MockBukkit;

public class EntityDataRegistry
{

	private EntityDataRegistry()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	static final Map<EntityType, String> entityJsonDataMap = new HashMap<>();

	/**
	 * Load all entity data
	 *
	 * @param repository The repository to load from
	 */
	public static void loadData(String repository)
	{
		for (EntityType type : EntityType.values())
		{
			try
			{
				entityJsonDataMap.put(type, load(repository, type));
			}
			catch (IOException e)
			{
				entityJsonDataMap.put(type, "");
			}
		}
	}

	/**
	 * Load entity data json string
	 *
	 * @param repository The repository to look in
	 * @param type       The type of entity to look for
	 * @return A json string containing the data
	 * @throws IOException
	 */
	private static String load(String repository, EntityType type) throws IOException
	{
		String path = repository + type.toString().toLowerCase(Locale.ROOT) + ".json";
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream(path))
		{
			if (inputStream == null)
			{
				throw new FileNotFoundException(path);
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
			{
				return reader.lines().collect(Collectors.joining(""));
			}
		}
	}

	/**
	 * Construct entity data based on entity type
	 *
	 * @param type The type of the entity
	 * @return A new instance of entitydata
	 */
	public static EntityData loadEntityData(EntityType type)
	{
		if (entityJsonDataMap.isEmpty())
		{
			loadData("/entities/");
		}
		String data = entityJsonDataMap.get(type);

		return new EntityData(type, data);
	}

}
