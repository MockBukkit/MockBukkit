package org.mockbukkit.metaminer.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JsonUtil
{

	private JsonUtil()
	{
		throw new IllegalStateException("Utility class");
	}

	public static void dump(JsonElement json, File destinationFile) throws IOException
	{
		File parentFile = destinationFile.getParentFile();
		if (!parentFile.exists() && !parentFile.mkdirs())
		{
			throw new IOException("Could not create directory: " + parentFile);
		}
		if (!destinationFile.exists() && !destinationFile.createNewFile())
		{
			throw new IOException("Could not create file: " + destinationFile);
		}
		sortDFS(json);
		try (PrintWriter writer = new PrintWriter(destinationFile, StandardCharsets.UTF_8))
		{
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonWriter jsonWriter = new JsonWriter(writer);
			jsonWriter.setIndent("\t");
			gson.toJson(json, jsonWriter);
			writer.print("\n");
		}
	}

	private static void sortDFS(JsonElement json)
	{
		if (json instanceof JsonArray jsonArray)
		{
			List<JsonElement> jsonList = jsonArray.asList();
			jsonList.forEach(JsonUtil::sortDFS);
			jsonList.sort(JsonUtil::compareAlphabetically);
		}
		if (json instanceof JsonObject jsonObject)
		{
			jsonObject.entrySet().forEach(entry -> sortDFS(entry.getValue()));
			sortObject(jsonObject);
		}
	}

	private static void sortObject(JsonObject jsonObject)
	{
		Map<String, JsonElement> map = jsonObject.asMap();
		List<String> keys = new ArrayList<>(map.keySet());
		Map<String, JsonElement> mapCopy = new HashMap<>(map);
		map.clear();
		keys.sort(String::compareTo);
		for (String key : keys)
		{
			jsonObject.add(key, mapCopy.get(key));
		}
	}

	private static int compareAlphabetically(JsonElement json1, JsonElement json2)
	{
		Optional<String> jsonString1 = getSortingString(json1);
		Optional<String> jsonString2 = getSortingString(json2);
		if (jsonString2.isPresent() && jsonString1.isPresent())
		{
			return jsonString1.get().compareTo(jsonString2.get());
		}
		return 0;
	}

	private static Optional<String> getSortingString(JsonElement json)
	{
		if (json instanceof JsonPrimitive jsonPrimitive)
		{
			if (jsonPrimitive.isString())
			{
				return Optional.of(jsonPrimitive.getAsString());
			}
			return Optional.empty();
		}
		if (json instanceof JsonObject jsonObject)
		{
			if (jsonObject.has("key"))
			{
				return Optional.of(jsonObject.get("key").getAsString());
			}
			if (jsonObject.has("type"))
			{
				return Optional.of(jsonObject.get("type").getAsString());
			}
			return Optional.empty();
		}
		return Optional.empty();
	}

}
