package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class EnumElementFactoryTest
{

	@Test
	void givenNull()
	{
		JsonElement actual = EnumElementFactory.toJson(null);
		assertNull(actual);
	}

	@ParameterizedTest
	@EnumSource
	void givenValidEnum(Material material)
	{
		JsonElement actual = EnumElementFactory.toJson(material);
		assertNotNull(actual);
		assertEquals(material.name(), actual.getAsString());
	}

}
