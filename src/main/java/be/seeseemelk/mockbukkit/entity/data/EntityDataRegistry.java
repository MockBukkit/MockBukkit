package be.seeseemelk.mockbukkit.entity.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.entity.EntityType;

import be.seeseemelk.mockbukkit.MockBukkit;

public class EntityDataRegistry
{
	
	static final Map<EntityType,String> entityJsonDataMap = new HashMap<>();
	
	
	public static void loadData(String repository) {
		for(EntityType type : EntityType.values()) {
			try
			{
				entityJsonDataMap.put(type,parse(repository,type));
			}
			catch (IOException e)
			{
				entityJsonDataMap.put(type, "");
			}
		}
	}
	
	static String parse(String repository, EntityType type) throws IOException
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
	
	public static EntityData loadEntityData(EntityType type) {
		if(entityJsonDataMap.isEmpty()) {
			loadData("/entities/");
		}
		String data = entityJsonDataMap.get(type);
		
		return new EntityData(type,data);
	}
}
