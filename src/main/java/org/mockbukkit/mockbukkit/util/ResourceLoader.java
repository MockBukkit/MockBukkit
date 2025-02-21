package org.mockbukkit.mockbukkit.util;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.exception.InternalDataLoadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ResourceLoader
{
	private ResourceLoader()
	{

	}

	public static JsonElement loadResource(@NotNull String path)
	{
		InputStream inputStream = MockBukkit.class.getResourceAsStream(path);
		if (inputStream == null)
		{
			inputStream = MockBukkit.class.getClassLoader().getResourceAsStream(path);
		}

		Preconditions.checkArgument(inputStream != null, "Resource not found: {}", path);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)))
		{
			return JsonParser.parseReader(reader);
		}
		catch (JsonSyntaxException | IOException e)
		{
			throw new InternalDataLoadException(e);
		}
	}
}
