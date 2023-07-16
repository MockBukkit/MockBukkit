package be.seeseemelk.mockbukkit.entity.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.entity.EntityType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
		String path = repository + type.toString().toLowerCase() + ".json";

		if (MockBukkit.class.getResource(path) == null)
		{
			throw new FileNotFoundException(path);
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(MockBukkit.class.getResourceAsStream(path), StandardCharsets.UTF_8)))
		{
			return reader.lines().collect(Collectors.joining(""));
		}
	}

	/**
	 * Construcy entity data based on entity type
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
