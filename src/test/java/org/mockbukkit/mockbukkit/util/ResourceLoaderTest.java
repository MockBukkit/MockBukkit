package org.mockbukkit.mockbukkit.util;

import com.google.gson.JsonElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.exception.InternalDataLoadException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceLoaderTest
{

	private static final String VALID_RESOURCE_PATH = "/test-resource.json";
	private static final String INVALID_RESOURCE_PATH = "/non-existent-resource.json";
	private static final String CORRUPTED_RESOURCE_PATH = "/corrupted-resource.json";

	@Test
	@DisplayName("Test loading a valid resource")
	void loadResource_ValidPath_ReturnsJsonElement()
	{
		JsonElement result = ResourceLoader.loadResource(VALID_RESOURCE_PATH);

		assertNotNull(result);
		assertTrue(result.isJsonObject());
		assertEquals("value", result.getAsJsonObject().get("test").getAsString());
	}

	@Test
	@DisplayName("Test loading a non-existent resource")
	void loadResource_InvalidPath_ThrowsIllegalArgumentException()
	{
		IllegalArgumentException exception = assertThrows(
				IllegalArgumentException.class,
				() -> ResourceLoader.loadResource(INVALID_RESOURCE_PATH)
		);

		assertTrue(exception.getMessage().contains("Resource not found"));
	}

	@Test
	@DisplayName("Test loading a corrupted resource")
	void loadResource_CorruptedJson_ThrowsInternalDataLoadException()
	{
		assertThrows(
				InternalDataLoadException.class,
				() -> ResourceLoader.loadResource(CORRUPTED_RESOURCE_PATH)
		);
	}

	@Test
	@DisplayName("Test loading a null value")
	void loadResource_NullPath_ThrowsNullPointerException()
	{
		assertThrows(
				NullPointerException.class,
				() -> ResourceLoader.loadResource(null)
		);
	}

	@Test
	@DisplayName("Test that the private constructor cannot be instantiated")
	void constructor_CreateInstance_ThrowsException()
	{
		assertThrows(
				IllegalAccessException.class,
				() -> ResourceLoader.class.getDeclaredConstructor().newInstance()
		);
	}

}
